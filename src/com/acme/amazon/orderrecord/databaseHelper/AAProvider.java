package com.acme.amazon.orderrecord.databaseHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class AAProvider extends ContentProvider{

    public static final String AUTHORITY = "com.acme.amazon.orderrecord.provider";
    public static final String DB_NAME = "acme_amazon.db";
    private static final int DB_VERSION = 1;
    private static final String TAG = "AAProvider";
    
    // URL matcher path constants.
    private static final int AA_PROFILE = 1;
    private static final int AA_PROFILE_ID = 2;
    private static final int AA_ITEM = 3;
    private static final int AA_ITEM_ID = 4;
    
    private AADatabaseHelper mHelper;
    
    //Amazon Order profile
    public interface ProfileColumns extends BaseColumns {
    	public static final String TBL_AA_PROFILES = "aa_profiles";
    	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TBL_AA_PROFILES);
    	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.acme.profile";
    	public static final String ORDER_ID = "order_id";
    	public static final String ORDER_DATE = "order_date";
    	public static final String ORDER_DETIAL_ID = "order_detial_ID";
    	public static final String ORDER_TITLE = "order_tilte";
    	public static final String ORDER_TOTAL_COST = "order_total_cost";
    }
    
    //Amazon Order Item Info
    public interface ItemColumns extends BaseColumns {
    	public static final String TBL_AA_ITEM = "aa_item";
    	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TBL_AA_ITEM);
    	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.acme.item";
    	public static final String ITEM_NAME = "item_name";
    	public static final String ITEM_QUALITY = "item_quality";
    	public static final String ITEM_TOTAL_COST = "order_total_cost";
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String whichTable = getTable(uri);
        int count = db.delete(whichTable, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null, false);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
    	SQLiteDatabase db = mHelper.getWritableDatabase();

        String whichTable = getTable(uri);
        if (values == null) {
            values = new ContentValues();
        }

        long rowId = db.insert(whichTable, BaseColumns._ID, values);

        if (rowId >= 0) {
            Uri url = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(url, null, false);
            return url;
        }

        throw new SQLiteException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
    	mHelper = new AADatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sort) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String sortOrder = null;
        int match = urlMatcher.match(uri);
        switch (match) {
        case AA_PROFILE:
            qb.setTables(ProfileColumns.TBL_AA_PROFILES);
            sortOrder = (sort != null ? sort : ProfileColumns._ID);
            break;
        case AA_PROFILE_ID:
            qb.setTables(ProfileColumns.TBL_AA_PROFILES);
            qb.appendWhere("_id=" + uri.getPathSegments().get(1));
            break;
        case AA_ITEM:
            qb.setTables(ItemColumns.TBL_AA_ITEM);
            sortOrder = (sort != null ? sort : ItemColumns._ID);
            break;
        case AA_ITEM_ID:
            qb.setTables(ItemColumns.TBL_AA_ITEM);
            qb.appendWhere("_id=" + uri.getPathSegments().get(1));
            break;
        default:
            throw new IllegalArgumentException("Unknown URL " + uri);
        }

        Log.d(TAG, "Build query: "
            + qb.buildQuery(projection, selection, null, null, sortOrder, null));

        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String whichTable = getTable(uri);

        int count = 0;
        int match = urlMatcher.match(uri);
        switch (match) {
        case AA_PROFILE:
        case AA_ITEM:
            count = db.update(whichTable, values, selection, selectionArgs);
            break;
        case AA_PROFILE_ID:
        case AA_ITEM_ID:
            String segment = uri.getPathSegments().get(1);
            count = db.update(whichTable, values, "_id=" + segment
                    + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URL " + uri);
        }

        if (count > 0) getContext().getContentResolver().notifyChange(uri, null, false);

        return count;
    }

    public static class AADatabaseHelper extends SQLiteOpenHelper {

        private Context ctx;
        public AADatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.ctx = context;
        }
		@Override
		public void onCreate(SQLiteDatabase db) {
            // Profi.les
            db.execSQL("CREATE TABLE " + ProfileColumns.TBL_AA_PROFILES + " ("
                    + ProfileColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ProfileColumns.ORDER_ID + " INTEGER, "
                    + ProfileColumns.ORDER_DATE + " VARCHAR, "
                    + ProfileColumns.ORDER_DETIAL_ID + " INTEGER, "
                    + ProfileColumns.ORDER_TITLE + " VARCHAR, "
                    + ProfileColumns.ORDER_TOTAL_COST + " INTEGER);");
            
            db.execSQL("CREATE TABLE " + ItemColumns.TBL_AA_ITEM + " ("
                    + ItemColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ItemColumns.ITEM_NAME + " VARCHAR, "
                    + ItemColumns.ITEM_QUALITY + " INTEGER, "
                    + ItemColumns.ITEM_TOTAL_COST + " VARCHAR);");
		}
		@Override
		public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "Downgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
			
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
		}
		
        @Override
        public void onOpen(SQLiteDatabase db) {
            Log.i(TAG, "Database has been opened for operations.");
        }
    }
    
    private static final UriMatcher urlMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        urlMatcher.addURI(AUTHORITY, ProfileColumns.TBL_AA_PROFILES, AA_PROFILE);
        urlMatcher.addURI(AUTHORITY, ProfileColumns.TBL_AA_PROFILES + "/#", AA_PROFILE_ID);

        urlMatcher.addURI(AUTHORITY, ItemColumns.TBL_AA_ITEM, AA_ITEM);
        urlMatcher.addURI(AUTHORITY, ItemColumns.TBL_AA_ITEM + "/#", AA_ITEM_ID);
    }
    /**
     * Get the targeting table based on given url match.
     * @param url
     * @return
     */
    private String getTable(Uri uri) {
        String whichTable = null;
        int match = urlMatcher.match(uri);
        switch (match) {
        case AA_PROFILE:
        case AA_PROFILE_ID:
            whichTable = ProfileColumns.TBL_AA_PROFILES;
            break;
        case AA_ITEM:
        case AA_ITEM_ID:
            whichTable = ItemColumns.TBL_AA_ITEM;
            break;
        default:
                throw new IllegalArgumentException("Unknown URL: " + uri);
        }

        return whichTable;
    }
}
