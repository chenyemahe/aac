
package com.acme.amazon.orderrecord.databaseHelper;

import com.acem.amazon.logging.Logging;

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

public class AAProvider extends ContentProvider {

    public static final String AUTHORITY = "com.acme.amazon.orderrecord.provider";

    public static final String DB_NAME = "acme_amazon.db";

    private static final int DB_VERSION = 1;

    private static final String TAG = "AAProvider";

    // URL matcher path constants
    // Product order from merchant
    private static final int AA_PROFILE = 1;

    private static final int AA_PROFILE_ID = 2;

    private static final int AA_ITEM = 3;

    private static final int AA_ITEM_ID = 4;

    private static final int AA_MATCH = 5;

    private static final int AA_MATCH_ID = 6;

    // Amazon product page detail
    private static final int AA_PRODUCT = 7;

    private static final int AA_PRODUCT_ID = 8;

    private static final int FBA_SHIP = 9;

    private static final int FBA_SHIP_ID = 10;

    private static final int FBA_SHIP_ITEM = 11;

    private static final int FBA_SHIP_ITEM_ID = 12;

    private static final int FBA_SHIP_REPORT = 13;

    private static final int FBA_SHIP_REPORT_ID = 14;

    private static final int FBA_SHIP_REPORT_ITEM = 15;

    private static final int FBA_SHIP_REPORT_ITEM_ID = 16;

    private AADatabaseHelper mHelper;

    // Amazon Order profile
    public interface ProfileColumns extends BaseColumns {
        String TBL_AA_PROFILES = "aa_profiles";

        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
                + TBL_AA_PROFILES);

        String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.acme.profile";

        String ORDER_ID = "order_id";

        String ORDER_DATE = "order_date";

        String ORDER_ITEM_ID = "order_detial_ID";

        String ORDER_TITLE = "order_tilte";

        String ORDER_TOTAL_COST = "order_total_cost";

        String ORDER_EXTRA_1 = "order_extra_1";
    }

    // Amazon Order Item Info
    public interface ItemColumns extends BaseColumns {
        String TBL_AA_ITEM = "aa_item";

        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
                + TBL_AA_ITEM);

        String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.acme.item";

        String ITEM_NAME = "item_name";

        String ITEM_QUALITY = "item_quality";

        String ITEM_TOTAL_COST = "order_total_cost";

        String ORDER_DATE = "order_date";

        String ITEM_CURRENCY_TYPE = "item_currency_type";

        String ITEM_ORDER_EXTRA_1 = "item_order_extra_1";
    }

    // Amazon Order Item and profile match info
    public interface MatchColumns extends BaseColumns {
        String TBL_AA_MATCH = "aa_match";

        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
                + TBL_AA_MATCH);

        String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.acme.match";

        String PROFILE_ID = "profile_id";

        String ITEM_ID = "item_id";
    }

    // Amazon Order Item and profile match info
    public interface ProductColumns extends BaseColumns {
        String TBL_AA_PRODUCT = "aa_product";

        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
                + TBL_AA_PRODUCT);

        String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.acme.product";

        String PRODUCT_NAME = "product_id";

        String PRODUCT_PRIME_PRICE = "product_prime_price";

        String PRODUCT_BV_POINT = "product_bv_point";

        String PRODUCT_BV_TO_MONEY = "product_bv_to_money";

        String PRODUCT_FBA_PRE_FEE = "product_fba_pre_fee";

        String PRODUCT_FBA_SHIPPING_FEE = "product_fba_shipping_fee";

        String PRODUCT_AMAZON_REF_FEE = "product_amazon_ref_fee";

        String PRODUCT_AMAZON_SALE_PRICE = "product_amazon_sale_price";

        String PRODUCT_SHOP_COM_Price = "product_shop_com_price";
    }

    /*
     * // FBA public interface FbaShipColumns extends BaseColumns { public
     * static final String TBL_FBA_SHIP = "fba_ship"; public static final Uri
     * CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TBL_FBA_SHIP);
     * public static final String CONTENT_TYPE =
     * "vnd.android.cursor.dir/vnd.acme.fba.ship"; public static final String
     * FBA_SHIP_DATE = "order_date"; public static final String FBA_SHIP_ITEM_ID
     * = "order_detial_ID"; } // FBA public interface FbaShipItemColumns extends
     * BaseColumns { public static final String TBL_FBA_SHIP_ITEM =
     * "fba_ship_item"; public static final Uri CONTENT_URI =
     * Uri.parse("content://" + AUTHORITY + "/" + TBL_FBA_SHIP_ITEM); public
     * static final String CONTENT_TYPE =
     * "vnd.android.cursor.dir/vnd.acme.fba.ship.item"; public static final
     * String ITEM_NAME = "item_name"; public static final String ITEM_QUALITY =
     * "item_quality"; public static final String SHIP_DATE = "fba_ship_date"; }
     */

    public interface FbaShipReportColumns extends BaseColumns {
        String TBL_AA_REPORT_PROFILES = "aa_report_profiles";

        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
                + TBL_AA_REPORT_PROFILES);

        String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.acme.report.profile";

        String SHIP_DATE = "ship_date";

        String SHIP_ITEM_ID = "ship_item_ID";

        String SHIP_TITLE = "ship_tilte";

        String SHIP_TOTAL_NUM = "ship_total_num";
    }

    public interface FbaShipReportItemColumns extends BaseColumns {
        String TBL_AA_REPORT_ITEM = "aa_report_item";

        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
                + TBL_AA_REPORT_ITEM);

        String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.acme.report.item";

        String ITEM_NAME = "item_name";

        String ITEM_QUALITY = "item_quality";

        String SHIP_DATE = "ship_date";

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
        String type = null;
        switch (urlMatcher.match(uri)) {
            case AA_PROFILE:
                type = ProfileColumns.CONTENT_TYPE;
                break;
        }
        return type;
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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sort) {
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
            case AA_PRODUCT:
                qb.setTables(ProductColumns.TBL_AA_PRODUCT);
                sortOrder = (sort != null ? sort : ProductColumns._ID);
                break;
            case AA_PRODUCT_ID:
                qb.setTables(ProductColumns.TBL_AA_PRODUCT);
                qb.appendWhere("_id=" + uri.getPathSegments().get(1));
                break;
            /*
             * case FBA_SHIP: qb.setTables(FbaShipColumns.TBL_FBA_SHIP);
             * sortOrder = (sort != null ? sort : FbaShipColumns._ID); break;
             * case FBA_SHIP_ID: qb.setTables(FbaShipColumns.TBL_FBA_SHIP);
             * qb.appendWhere("_id=" + uri.getPathSegments().get(1)); break;
             * case FBA_SHIP_ITEM:
             * qb.setTables(FbaShipItemColumns.TBL_FBA_SHIP_ITEM); sortOrder =
             * (sort != null ? sort : FbaShipItemColumns._ID); break; case
             * FBA_SHIP_ITEM_ID:
             * qb.setTables(FbaShipItemColumns.TBL_FBA_SHIP_ITEM);
             * qb.appendWhere("_id=" + uri.getPathSegments().get(1)); break;
             */
            case FBA_SHIP_REPORT:
                qb.setTables(FbaShipReportColumns.TBL_AA_REPORT_PROFILES);
                sortOrder = (sort != null ? sort : FbaShipReportColumns._ID);
                break;
            case FBA_SHIP_REPORT_ID:
                qb.setTables(FbaShipReportColumns.TBL_AA_REPORT_PROFILES);
                qb.appendWhere("_id=" + uri.getPathSegments().get(1));
                break;
            case FBA_SHIP_REPORT_ITEM:
                qb.setTables(FbaShipReportItemColumns.TBL_AA_REPORT_ITEM);
                sortOrder = (sort != null ? sort : FbaShipReportItemColumns._ID);
                break;
            case FBA_SHIP_REPORT_ITEM_ID:
                qb.setTables(FbaShipReportItemColumns.TBL_AA_REPORT_ITEM);
                qb.appendWhere("_id=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }

        Log.d(TAG,
                "Build query: " + qb.buildQuery(projection, selection, null, null, sortOrder, null));

        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String whichTable = getTable(uri);

        int count;
        int match = urlMatcher.match(uri);
        switch (match) {
            case AA_PROFILE:
            case AA_ITEM:
            case AA_PRODUCT:
            case FBA_SHIP:
            case FBA_SHIP_ITEM:
            case FBA_SHIP_REPORT:
            case FBA_SHIP_REPORT_ITEM:
                count = db.update(whichTable, values, selection, selectionArgs);
                break;
            case AA_PROFILE_ID:
            case AA_ITEM_ID:
            case AA_PRODUCT_ID:
            case FBA_SHIP_ID:
            case FBA_SHIP_ITEM_ID:
            case FBA_SHIP_REPORT_ID:
            case FBA_SHIP_REPORT_ITEM_ID:
                String segment = uri.getPathSegments().get(1);
                count = db
                        .update(whichTable, values,
                                "_id="
                                        + segment
                                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                                                + ')' : ""), selectionArgs);
                break;
            case AA_MATCH:
            case AA_MATCH_ID:

            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }

        if (count > 0)
            getContext().getContentResolver().notifyChange(uri, null, false);

        return count;
    }

    public static class AADatabaseHelper extends SQLiteOpenHelper {

        // private Context ctx;
        public AADatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            // this.ctx = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Profi.les
            db.execSQL("CREATE TABLE " + ProfileColumns.TBL_AA_PROFILES + " (" + ProfileColumns._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ProfileColumns.ORDER_ID
                    + " VARCHAR, " + ProfileColumns.ORDER_DATE + " VARCHAR, "
                    + ProfileColumns.ORDER_ITEM_ID + " INTEGER, " + ProfileColumns.ORDER_TITLE
                    + " VARCHAR, " + ProfileColumns.ORDER_EXTRA_1 + " VARCHAR, "
                    + ProfileColumns.ORDER_TOTAL_COST + " INTEGER);");

            db.execSQL("CREATE TABLE " + ItemColumns.TBL_AA_ITEM + " (" + ItemColumns._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ItemColumns.ITEM_NAME + " VARCHAR, "
                    + ItemColumns.ITEM_QUALITY + " INTEGER, " + ItemColumns.ORDER_DATE
                    + " VARCHAR, " + ItemColumns.ITEM_CURRENCY_TYPE + " VARCHAR, "
                    + ItemColumns.ITEM_ORDER_EXTRA_1 + " VARCHAR, " + ItemColumns.ITEM_TOTAL_COST
                    + " VARCHAR);");

            db.execSQL("CREATE TABLE " + ProductColumns.TBL_AA_PRODUCT + " (" + ProductColumns._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ProductColumns.PRODUCT_NAME
                    + " VARCHAR, " + ProductColumns.PRODUCT_PRIME_PRICE + " VARCHAR, "
                    + ProductColumns.PRODUCT_BV_POINT + " VARCHAR, "
                    + ProductColumns.PRODUCT_BV_TO_MONEY + " VARCHAR, "
                    + ProductColumns.PRODUCT_FBA_PRE_FEE + " VARCHAR, "
                    + ProductColumns.PRODUCT_FBA_SHIPPING_FEE + " VARCHAR, "
                    + ProductColumns.PRODUCT_AMAZON_REF_FEE + " VARCHAR, "
                    + ProductColumns.PRODUCT_AMAZON_SALE_PRICE + " VARCHAR, "
                    + ProductColumns.PRODUCT_SHOP_COM_Price + " VARCHAR);");

            /*
             * db.execSQL("CREATE TABLE " + FbaShipColumns.TBL_FBA_SHIP + " (" +
             * FbaShipColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
             * FbaShipColumns.FBA_SHIP_ITEM_ID + " VARCHAR, " +
             * FbaShipColumns.FBA_SHIP_ITEM_ID + " VARCHAR);");
             * db.execSQL("CREATE TABLE " + FbaShipItemColumns.TBL_FBA_SHIP_ITEM
             * + " (" + ItemColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             * + ItemColumns.ITEM_NAME + " VARCHAR, " + ItemColumns.ITEM_QUALITY
             * + " INTEGER, " + ItemColumns.ORDER_DATE + " VARCHAR, " +
             * ItemColumns.ITEM_CURRENCY_TYPE + " VARCHAR, " +
             * ItemColumns.ITEM_ORDER_EXTRA_1 + " VARCHAR, " +
             * ItemColumns.ITEM_TOTAL_COST + " VARCHAR);");
             */

            db.execSQL("CREATE TABLE " + FbaShipReportColumns.TBL_AA_REPORT_PROFILES + " ("
                    + FbaShipReportColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + FbaShipReportColumns.SHIP_DATE + " VARCHAR, "
                    + FbaShipReportColumns.SHIP_ITEM_ID + " VARCHAR, "
                    + FbaShipReportColumns.SHIP_TITLE + " VARCHAR, "
                    + FbaShipReportColumns.SHIP_TOTAL_NUM + " VARCHAR);");

            db.execSQL("CREATE TABLE " + FbaShipReportItemColumns.TBL_AA_REPORT_ITEM + " ("
                    + FbaShipReportItemColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + FbaShipReportItemColumns.ITEM_NAME + " VARCHAR, "
                    + FbaShipReportItemColumns.SHIP_DATE + " VARCHAR, "
                    + FbaShipReportItemColumns.ITEM_QUALITY + " VARCHAR);");
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "Downgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
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

        urlMatcher.addURI(AUTHORITY, MatchColumns.TBL_AA_MATCH, AA_MATCH);
        urlMatcher.addURI(AUTHORITY, MatchColumns.TBL_AA_MATCH + "/#", AA_MATCH_ID);

        urlMatcher.addURI(AUTHORITY, ItemColumns.TBL_AA_ITEM, AA_ITEM);
        urlMatcher.addURI(AUTHORITY, ItemColumns.TBL_AA_ITEM + "/#", AA_ITEM_ID);

        urlMatcher.addURI(AUTHORITY, ProductColumns.TBL_AA_PRODUCT, AA_PRODUCT);
        urlMatcher.addURI(AUTHORITY, ProductColumns.TBL_AA_PRODUCT + "/#", AA_PRODUCT_ID);

        urlMatcher.addURI(AUTHORITY, FbaShipReportColumns.TBL_AA_REPORT_PROFILES, FBA_SHIP_REPORT);
        urlMatcher.addURI(AUTHORITY, FbaShipReportColumns.TBL_AA_REPORT_PROFILES + "/#",
                FBA_SHIP_REPORT_ID);

        urlMatcher.addURI(AUTHORITY, FbaShipReportItemColumns.TBL_AA_REPORT_ITEM,
                FBA_SHIP_REPORT_ITEM);
        urlMatcher.addURI(AUTHORITY, FbaShipReportItemColumns.TBL_AA_REPORT_ITEM + "/#",
                FBA_SHIP_REPORT_ITEM_ID);
    }

    /**
     * Get the targeting table based on given url match.
     * 
     * @param uri uri for the table
     * @return table
     */
    private String getTable(Uri uri) {
        String whichTable;
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
            case AA_MATCH:
            case AA_MATCH_ID:
                whichTable = MatchColumns.TBL_AA_MATCH;
                break;
            case AA_PRODUCT:
            case AA_PRODUCT_ID:
                whichTable = ProductColumns.TBL_AA_PRODUCT;
                break;
            case FBA_SHIP_REPORT:
            case FBA_SHIP_REPORT_ID:
                whichTable = FbaShipReportColumns.TBL_AA_REPORT_PROFILES;
                break;
            case FBA_SHIP_REPORT_ITEM:
            case FBA_SHIP_REPORT_ITEM_ID:
                whichTable = FbaShipReportItemColumns.TBL_AA_REPORT_ITEM;
                break;
            default:
                throw new IllegalArgumentException("Unknown URL: " + uri);
        }

        Logging.logD("Selected table is " + whichTable);
        return whichTable;
    }
}
