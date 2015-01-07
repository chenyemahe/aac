package com.acme.amazon.orderrecord.databaseHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class AAProvider extends ContentProvider{

    public static final String AUTHORITY = "com.acme.amazon.orderrecord.provider";
    public static final String DB_NAME = "acme_amazon.db";
    private static final int DB_VERSION = 1;
    private static final String TAG = "AAProvider";
    
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
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean onCreate() {
    	mHelper = new AADatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
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
}
