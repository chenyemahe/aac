
package com.acme.amazon.databaseHelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.acem.amazon.logging.Logging;
import com.acme.amazon.AAFbaItem;
import com.acme.amazon.AAFbaProfile;
import com.acme.amazon.AAItem;
import com.acme.amazon.AAProduct;
import com.acme.amazon.AAProfile;
import com.acme.amazon.AAUtils;
import com.acme.amazon.amazonpage.order.TransactionNode;
import com.acme.amazon.databaseHelper.AAProvider.*;
import com.acme.amazon.databaseHelper.AAProvider.FbaShipReportItemColumns;
import com.acme.amazon.databaseHelper.AAProvider.ItemColumns;
import com.acme.amazon.databaseHelper.AAProvider.ProductColumns;
import com.acme.amazon.databaseHelper.AAProvider.ProfileColumns;

public class AADba {

    private static final String TAG = "AADba";

    private static AADba mDba;

    // Query string constants to work with database.
    private static String PROFILE_SELECTION_BY_DATE = ProfileColumns.ORDER_DATE + " LIKE ? ";

    private static String FBA_PROFILE_SELECTION_BY_DATE = FbaShipReportColumns.SHIP_DATE
            + " LIKE ? ";

    private static String PROFILE_SELECTION_BY_ID = ProfileColumns._ID + " LIKE ? ";

    private static String FBA_PROFILE_SELECTION_BY_ID = FbaShipReportColumns._ID + " LIKE ? ";

    private static String PRODUCT_SELECTION_BY_NAME = ProductColumns.PRODUCT_NAME + " LIKE ? ";

    private static String ITEM_SELECTION = ItemColumns._ID + " LIKE ? ";

    private static String FBA_ITEM_SELECTION = FbaShipReportItemColumns._ID + " LIKE ? ";

    private static String TRANS_SELECTION = TransColumns.aa_tran_date + " LIKE ? AND" + TransColumns.order_id + " LIKE ? AND" + TransColumns.description + " LIKE ? AND"
            + TransColumns.total + "LIKE ?";

    public static String ID_SELECTION = BaseColumns._ID + "=?";

    public static AADba getDB() {
        if (mDba == null)
            mDba = new AADba();
        return mDba;
    }

    /**
     * Save order
     * 
     * @param cr
     * @param profile
     * @return
     */
    public Uri saveAAProfile(ContentResolver cr, AAProfile profile) {
        if (profile == null) {
            return null;
        }

        List<AAItem> itemList = profile.getItemList();
        if (itemList != null) {
            saveAAItemList(cr, itemList, profile);
        }

        ContentValues values = new ContentValues();
        AAUtils.toContentValues(profile, values);

        Log.d(TAG, "insert order " + profile.getDate());
        return cr.insert(ProfileColumns.CONTENT_URI, values);
    }

    /**
     * Save List of Item
     * 
     * @param cr
     * @param itemList
     * @param profile
     */
    private void saveAAItemList(ContentResolver cr, List<AAItem> itemList, AAProfile profile) {
        String id = "";
        String title = "";

        if (itemList == null) {
            return;
        }
        for (int i = 0; i < itemList.size(); i++) {
            AAItem item = itemList.get(i);
            Uri uri = saveAAItem(cr, item);
            if (uri != null) {
                id = id + uri.getPathSegments().get(1);
                title = title + item.getName();
                if ((i + 1) < itemList.size()) {
                    id += ",";
                    title += "\n";
                }
            }
        }
        if (id == "") {
            return;
        }
        profile.setID(id);
        profile.setTitle(title);
    }

    /**
     * Save detail item
     * 
     * @param cr
     * @param item
     * @return
     */
    private Uri saveAAItem(ContentResolver cr, AAItem item) {
        if (item == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        AAUtils.toContentValues(item, values);
        Log.d(TAG, "insert item " + item.getName());
        return cr.insert(ItemColumns.CONTENT_URI, values);
    }

    public List<AAProfile> getAllProfile(ContentResolver cr) {
        List<AAProfile> profileList = new ArrayList<AAProfile>();

        AAProfile profile = null;
        Cursor cursor = null;

        try {
            cursor = cr.query(ProfileColumns.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    profile = new AAProfile();
                    AAUtils.fromCursor(cursor, profile);
                    profile.setItemList((ArrayList<AAItem>) getAAItem(cr, profile.getDate(),
                            profile.getID()));
                    profileList.add(profile);
                } while (cursor.moveToNext());

            }
        } catch (SQLException e) {
            Logging.logE(TAG, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return profileList;
    }

    public AAProfile getAAProfileById(ContentResolver cr, String id) {
        AAProfile profile = new AAProfile();
        Logging.logD(TAG, "{getAAProfile} the ID is : " + id);
        if (id == null)
            return null;

        Cursor cursor = null;

        try {
            cursor = cr.query(ProfileColumns.CONTENT_URI, null, PROFILE_SELECTION_BY_ID,
                    new String[] {
                        id
                    }, null);
            if (cursor != null && cursor.moveToFirst()) {
                AAUtils.fromCursor(cursor, profile);
                profile.setItemList((ArrayList<AAItem>) getAAItem(cr, profile.getDate(),
                        profile.getID()));

            }
        } catch (SQLException e) {
            Logging.logE(TAG, "Error in retrieve Date: " + id, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return profile;
    }

    public List<AAProfile> getAAProfile(ContentResolver cr, String Date) {
        List<AAProfile> profileList = new ArrayList<AAProfile>();
        Logging.logD(TAG, "{getAAProfile} the Date is : " + Date);
        if (Date == null)
            return null;

        AAProfile profile = null;
        Cursor cursor = null;

        try {
            cursor = cr.query(ProfileColumns.CONTENT_URI, null, PROFILE_SELECTION_BY_DATE,
                    new String[] {
                        Date
                    }, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    profile = new AAProfile();
                    AAUtils.fromCursor(cursor, profile);
                    profile.setItemList((ArrayList<AAItem>) getAAItem(cr, profile.getDate(),
                            profile.getID()));
                    profileList.add(profile);
                } while (cursor.moveToNext());

            }
        } catch (SQLException e) {
            Logging.logE(TAG, "Error in retrieve Date: " + Date, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return profileList;
    }

    public List<AAItem> getAAItem(ContentResolver cr, String date, String id) {
        List<AAItem> itemList = new ArrayList<AAItem>();
        String[] idArray = id.split(",");
        Logging.logD(TAG, "{getAAItem} the Date is : " + date);
        if (date == null)
            return null;
        AAItem item = null;
        Cursor cursor = null;

        try {
            for (int i = 0; i < idArray.length; i++) {
                String itemId = idArray[i];
                cursor = cr.query(ItemColumns.CONTENT_URI, null, ITEM_SELECTION, new String[] {
                    itemId
                }, null);
                if (cursor != null && cursor.moveToFirst()) {
                    item = new AAItem();
                    AAUtils.fromCursor(cursor, item);
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            Logging.logE(TAG, "Error in retrieve id: " + id, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return itemList;
    }

    public int deleteAAProfile(ContentResolver cr, AAProfile profile) {
        int count = 0;
        String[] idArray = profile.getID().split(",");
        if (profile != null) {
            for (int i = 0; i < idArray.length; i++) {
                deleteItem(cr, idArray[i]);
            }
            count = cr.delete(ProfileColumns.CONTENT_URI, ID_SELECTION, new String[] {
                profile.getProfileId()
            });
        }
        return count;
    }

    public int deleteAAProfile(ContentResolver cr, String id) {
        int count = 0;
        AAProfile profile = getAAProfileById(cr, id);
        String[] idArray = profile.getID().split(",");
        if (profile != null) {
            for (int i = 0; i < idArray.length; i++) {
                deleteItem(cr, idArray[i]);
            }
            count = cr.delete(ProfileColumns.CONTENT_URI, ID_SELECTION, new String[] {
                profile.getProfileId()
            });
        }
        return count;
    }

    public void deleteItem(ContentResolver cr, String id) {
        cr.delete(ItemColumns.CONTENT_URI, ID_SELECTION, new String[] {
            id
        });
    }

    // Amazon product database

    /**
     * Save product Info
     * 
     * @param cr
     * @param product
     * @return
     */
    public Uri saveAAProduct(ContentResolver cr, AAProduct product) {
        if (product == null) {
            return null;
        }

        ContentValues values = new ContentValues();
        AAUtils.toContentValues(product, values);

        Log.d(TAG, "insert product " + product.getProductName());
        return cr.insert(ProductColumns.CONTENT_URI, values);
    }

    public int updateAAProduct(ContentResolver cr, AAProduct product) {
        if (product == null) {
            return 0;
        }
        ContentValues contentValues = new ContentValues();
        AAUtils.toContentValues(product, contentValues);
        return cr.update(ProductColumns.CONTENT_URI, contentValues, ID_SELECTION, new String[] {
            product.getID()
        });
    }

    public AAProduct getAAProductByName(ContentResolver cr, String name) {
        AAProduct product = new AAProduct();
        Logging.logD(TAG, "{getAAProduct} the name is : " + name);
        if (name == null)
            return null;

        Cursor cursor = null;

        try {
            cursor = cr.query(ProductColumns.CONTENT_URI, null, PRODUCT_SELECTION_BY_NAME,
                    new String[] {
                        name
                    }, null);
            if (cursor != null && cursor.moveToFirst()) {
                AAUtils.fromCursor(cursor, product);

            }
        } catch (SQLException e) {
            Logging.logE(TAG, "Error in retrieve Date: " + name, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return product;
    }

    public List<AAProduct> getAllProduct(ContentResolver cr) {
        List<AAProduct> productList = new ArrayList<AAProduct>();

        AAProduct product = null;
        Cursor cursor = null;

        try {
            cursor = cr.query(ProductColumns.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    product = new AAProduct();
                    AAUtils.fromCursor(cursor, product);
                    productList.add(product);
                } while (cursor.moveToNext());

            }
        } catch (SQLException e) {
            Logging.logE(TAG, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return productList;
    }

    public int deleteAAProduct(ContentResolver cr, AAProduct product) {
        int count = 0;
        if (product != null) {
            count = cr.delete(ProductColumns.CONTENT_URI, ID_SELECTION, new String[] {
                product.getID()
            });
        }
        return count;
    }

    // FBA Shipping

    public Uri saveAAFbaProfile(ContentResolver cr, AAFbaProfile profile) {
        if (profile == null) {
            return null;
        }

        List<AAFbaItem> itemList = profile.getFbaItemList();
        if (itemList != null) {
            saveAAFbaItemList(cr, itemList, profile);
        }

        ContentValues values = new ContentValues();
        AAUtils.toContentValues(profile, values);

        Log.d(TAG, "insert fba shipping order " + profile.getDate());
        return cr.insert(FbaShipReportColumns.CONTENT_URI, values);
    }

    private void saveAAFbaItemList(ContentResolver cr, List<AAFbaItem> itemList,
            AAFbaProfile profile) {
        String id = "";

        if (itemList == null) {
            return;
        }
        for (int i = 0; i < itemList.size(); i++) {
            AAFbaItem item = itemList.get(i);
            Uri uri = saveAAFbaItem(cr, item);
            if (uri != null) {
                id = id + uri.getPathSegments().get(1);
                if ((i + 1) < itemList.size()) {
                    id += ",";
                }
            }
        }
        if (id == "") {
            return;
        }
        profile.setID(id);
    }

    private Uri saveAAFbaItem(ContentResolver cr, AAFbaItem item) {
        if (item == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        AAUtils.toContentValues(item, values);
        Log.d(TAG, "insert fba item " + item.getName());
        return cr.insert(FbaShipReportItemColumns.CONTENT_URI, values);
    }

    public AAFbaProfile getAAFbaProfileById(ContentResolver cr, String id) {
        AAFbaProfile profile = new AAFbaProfile();
        Logging.logD(TAG, "{getAAFbaProfile} the ID is : " + id);
        if (id == null)
            return null;

        Cursor cursor = null;

        try {
            cursor = cr.query(FbaShipReportColumns.CONTENT_URI, null, FBA_PROFILE_SELECTION_BY_ID,
                    new String[] {
                        id
                    }, null);
            if (cursor != null && cursor.moveToFirst()) {
                AAUtils.fromCursor(cursor, profile);
                profile.setFbaItemList((ArrayList<AAFbaItem>) getAAFbaItem(cr, profile.getDate(),
                        profile.getID()));

            }
        } catch (SQLException e) {
            Logging.logE(TAG, "Error in retrieve Date: " + id, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return profile;
    }

    public List<AAFbaProfile> getAAFbaProfile(ContentResolver cr, String Date) {
        List<AAFbaProfile> profileList = new ArrayList<AAFbaProfile>();
        Logging.logD(TAG, "{getAAFbaProfile} the Date is : " + Date);
        if (Date == null)
            return null;

        AAFbaProfile profile = null;
        Cursor cursor = null;

        try {
            cursor = cr.query(FbaShipReportColumns.CONTENT_URI, null,
                    FBA_PROFILE_SELECTION_BY_DATE, new String[] {
                        Date
                    }, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    profile = new AAFbaProfile();
                    AAUtils.fromCursor(cursor, profile);
                    profile.setFbaItemList((ArrayList<AAFbaItem>) getAAFbaItem(cr,
                            profile.getDate(), profile.getID()));
                    profileList.add(profile);
                } while (cursor.moveToNext());

            }
        } catch (SQLException e) {
            Logging.logE(TAG, "Error in retrieve Date: " + Date, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return profileList;
    }

    public List<AAFbaProfile> getAllFbaProfile(ContentResolver cr) {
        List<AAFbaProfile> profileList = new ArrayList<AAFbaProfile>();

        AAFbaProfile profile = null;
        Cursor cursor = null;

        try {
            cursor = cr.query(FbaShipReportColumns.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    profile = new AAFbaProfile();
                    AAUtils.fromCursor(cursor, profile);
                    profile.setFbaItemList((ArrayList<AAFbaItem>) getAAFbaItem(cr, profile.getDate(),
                            profile.getID()));
                    profileList.add(profile);
                } while (cursor.moveToNext());

            }
        } catch (SQLException e) {
            Logging.logE(TAG, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return profileList;
    }

    public List<AAFbaItem> getAAFbaItem(ContentResolver cr, String date, String id) {
        List<AAFbaItem> itemList = new ArrayList<AAFbaItem>();
        String[] idArray = id.split(",");
        Logging.logD(TAG, "{getAAFbaItem} the Date is : " + date);
        if (date == null)
            return null;
        AAFbaItem item = null;
        Cursor cursor = null;

        try {
            for (int i = 0; i < idArray.length; i++) {
                String itemId = idArray[i];
                cursor = cr.query(FbaShipReportItemColumns.CONTENT_URI, null, FBA_ITEM_SELECTION,
                        new String[] {
                            itemId
                        }, null);
                if (cursor != null && cursor.moveToFirst()) {
                    item = new AAFbaItem();
                    AAUtils.fromCursor(cursor, item);
                    itemList.add(item);
                }
            }
        } catch (SQLException e) {
            Logging.logE(TAG, "Error in retrieve id: " + id, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return itemList;
    }

    public int deleteAAFbaProfile(ContentResolver cr, AAFbaProfile profile) {
        int count = 0;
        String[] idArray = profile.getID().split(",");
        if (profile != null) {
            for (int i = 0; i < idArray.length; i++) {
                deleteFbaItem(cr, idArray[i]);
            }
            count = cr.delete(FbaShipReportColumns.CONTENT_URI, ID_SELECTION, new String[] {
                profile.getProfileId()
            });
        }
        return count;
    }

    public void deleteFbaItem(ContentResolver cr, String id) {
        cr.delete(FbaShipReportItemColumns.CONTENT_URI, ID_SELECTION, new String[] {
            id
        });
    }

    public TransactionNode getTransOrder(ContentResolver cr, TransactionNode cachedNnode) {

        TransactionNode node = null;
        Cursor cursor = null;

        try {
            cursor = cr.query(AAProvider.TransColumns.CONTENT_URI, null, TRANS_SELECTION, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    node = new TransactionNode();
                    AAUtils.fromCursor(cursor, node);
                } while (cursor.moveToNext());

            }
        } catch (SQLException e) {
            Logging.logE(TAG, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return node;
    }

    public List<TransactionNode> getAllTransOrder(ContentResolver cr) {
        List<TransactionNode> nodesList = new ArrayList<>();

        TransactionNode node = null;
        Cursor cursor = null;

        try {
            cursor = cr.query(AAProvider.TransColumns.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    node = new TransactionNode();
                    AAUtils.fromCursor(cursor, node);
                    nodesList.add(node);
                } while (cursor.moveToNext());

            }
        } catch (SQLException e) {
            Logging.logE(TAG, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return nodesList;
    }

    public Uri saveTransOrder(ContentResolver cr, TransactionNode node) {
        if (node == null) {
            return null;
        }

        ContentValues values = new ContentValues();
        AAUtils.toContentValues(node, values);

        Log.d(TAG, "insert amazon transaction order " + node.getOrder_id());
        return cr.insert(AAProvider.TransColumns.CONTENT_URI, values);
    }

    private String[] setTrans_Selection_Data(TransactionNode node) {
        String[] s = {node.getAa_tran_date(), node.getOrder_id(), node.getDescription(), node.getTotal()};
        return s;
    }
}
