package com.acme.amazon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;

import com.acme.amazon.orderrecord.databaseHelper.AAProvider.ItemColumns;
import com.acme.amazon.orderrecord.databaseHelper.AAProvider.MatchColumns;
import com.acme.amazon.orderrecord.databaseHelper.AAProvider.ProfileColumns;

public class AAUtils {

    public static final String INTENT_PROFILE_ID = "Item_ID";
    public static final String UNSORT = "unsort";
    public static final String SHARED_PRE_NAME = "aa_shared_pre_name";
    public static final int PRODUCT_LIST_INI_STATE = -1;
    public static final int PRODUCT_LIST_EDIT_STATE = 1;
    public static final int PRODUCT_LIST_SHOW_STATE = 0;

    public static void toContentValues(AAProfile profile, ContentValues values) {
        values.put(ProfileColumns.ORDER_DATE, profile.getDate());
        values.put(ProfileColumns.ORDER_ID, profile.getID());
        values.put(ProfileColumns.ORDER_TITLE, profile.getTitle());
        values.put(ProfileColumns.ORDER_TOTAL_COST, profile.getCost());
        values.put(ProfileColumns.ORDER_EXTRA_1, profile.getExtra1());
    }

    public static void toContentValues(AAMatch match, ContentValues values) {
        values.put(MatchColumns.PROFILE_ID, match.getProfileID());
        values.put(MatchColumns.ITEM_ID, match.getItemID());
    }

    public static void toContentValues(AAItem item, ContentValues values) {
        values.put(ItemColumns.ITEM_NAME, item.getName());
        values.put(ItemColumns.ITEM_QUALITY, item.getQuality());
        values.put(ItemColumns.ITEM_TOTAL_COST, item.getCost());
        values.put(ItemColumns.ORDER_DATE, item.getDate());
        values.put(ItemColumns.ITEM_CURRENCY_TYPE, item.getCurrencyType());
        values.put(ItemColumns.ITEM_ORDER_EXTRA_1, item.getExtra1());
    }

    public static void fromCursor(Cursor cursor, AAProfile profile) {
        int idxId = cursor.getColumnIndexOrThrow(ProfileColumns._ID);
        int idxDate = cursor.getColumnIndexOrThrow(ProfileColumns.ORDER_DATE);
        int idxOrderId = cursor.getColumnIndexOrThrow(ProfileColumns.ORDER_ID);
        int idxTitle = cursor.getColumnIndexOrThrow(ProfileColumns.ORDER_TITLE);
        int idxCost = cursor.getColumnIndexOrThrow(ProfileColumns.ORDER_TOTAL_COST);
        int idxExtra1 = cursor.getColumnIndexOrThrow(ProfileColumns.ORDER_EXTRA_1);

        profile.setProfileId(cursor.getString(idxId));
        profile.setDate(cursor.getString(idxDate));
        profile.setID(cursor.getString(idxOrderId));
        profile.setTitle(cursor.getString(idxTitle));
        profile.setCost(cursor.getString(idxCost));
        profile.setExtra1(cursor.getString(idxExtra1));
    }

    public static void fromCursor(Cursor cursor, AAItem item) {
        int idxId = cursor.getColumnIndexOrThrow(ItemColumns._ID);
        int idxDate = cursor.getColumnIndexOrThrow(ItemColumns.ORDER_DATE);
        int idxName = cursor.getColumnIndexOrThrow(ItemColumns.ITEM_NAME);
        int idxQuality = cursor.getColumnIndexOrThrow(ItemColumns.ITEM_QUALITY);
        int idxCost = cursor.getColumnIndexOrThrow(ItemColumns.ITEM_TOTAL_COST);
        int idxCurrencyType = cursor.getColumnIndexOrThrow(ItemColumns.ITEM_CURRENCY_TYPE);
        int idxExtra1 = cursor.getColumnIndexOrThrow(ItemColumns.ITEM_ORDER_EXTRA_1);

        item.setItemId(cursor.getString(idxId));
        item.setDate(cursor.getString(idxDate));
        item.setName(cursor.getString(idxName));
        item.setQuality(cursor.getInt(idxQuality));
        item.setCurrencyType(cursor.getString(idxCurrencyType));
        item.setCost(cursor.getString(idxCost));
        item.setExtra1(cursor.getString(idxExtra1));
    }

    /**
     * Sort order profile list by date
     * 
     * @param profileList
     * @return ArrayList by key year and month for sorted list
     */
    public static synchronized ArrayList<ArrayList<ArrayList<AAProfile>>> sortProfileByDate(List<AAProfile> profileList) {
        ArrayList<ArrayList<ArrayList<AAProfile>>> sortListMap = new ArrayList<ArrayList<ArrayList<AAProfile>>>();
        ArrayList<String> yearList = new ArrayList<String>();
        String year = UNSORT;
        String month = UNSORT;
        String day = UNSORT;
        // date structure Month/Date/Year
        for (AAProfile profile : profileList) {
            String[] mdate = profile.getDate().split("/");
            if (mdate.length == 3) {
                year = mdate[2];
                month = mdate[0];
                day = mdate[1];
            } else {
                return null;
            }
            int indexYear = -1;
            int indexMonth = Integer.parseInt(month);
            if (!yearList.contains(year)) {
                for (int i = 0; i <= yearList.size(); i++) {
                    if (i == yearList.size()) {
                        sortListMap.add(new ArrayList<ArrayList<AAProfile>>());
                        yearList.add(year);
                        break;
                    }
                    if (Integer.parseInt(yearList.get(i)) < Integer.parseInt(year)) {
                        sortListMap.add(i, new ArrayList<ArrayList<AAProfile>>());
                        yearList.add(i, year);
                        break;
                    }
                }
                indexYear = findStElemInArray(yearList, year);
                for (int i = 0; i < 12; i++) {
                    sortListMap.get(indexYear).add(new ArrayList<AAProfile>());
                }
            } else {
                indexYear = findStElemInArray(yearList, year);
            }

            ArrayList<AAProfile> list = sortListMap.get(indexYear).get(indexMonth);
            for (int i = 0; i <= list.size(); i++) {
                if (i == list.size()) {
                    list.add(profile);
                    break;
                }
                // sort by latest date
                if (Integer.parseInt(list.get(i).getDate().split("/")[1]) < Integer.parseInt(day)) {
                    list.add(i, profile);
                    break;
                }
            }
        }
        return sortListMap;
    }

    private static int findStElemInArray(ArrayList<String> list, String item) {
        int i = 0;
        for (String s : list) {
            if (s.equals(item)) {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    /**
     * Parse Set to ArrayList
     * @param set
     * @return ArrayList
     */
    public static <T> ArrayList<T> setToArrayList(Set<T> set) {
        if(set == null)
            return null;
        ArrayList<T> tArray = new ArrayList<T>();
        Iterator<T> it = set.iterator();
        while(it.hasNext()) {
            T t = it.next();
            tArray.add(t);
        }
        return tArray;
    }
    
    /**
     * Parse ArrayList to Set
     * @param ArrayList
     * @return Set
     */
    public static <T> Set<T> arrayListToSet(ArrayList<T> list) {
        if(list == null)
            return null;
        Set<T> set = new HashSet<T>();
        for(T t : list) {
            set.add(t);
        }
        return set;
    } 
}
