
package com.acme.amazon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.acme.amazon.amazonpage.order.TransactionNode;
import com.acme.amazon.databaseHelper.AAProvider;
import com.acme.amazon.databaseHelper.AAProvider.FbaShipReportColumns;
import com.acme.amazon.databaseHelper.AAProvider.FbaShipReportItemColumns;
import com.acme.amazon.databaseHelper.AAProvider.ItemColumns;
import com.acme.amazon.databaseHelper.AAProvider.MatchColumns;
import com.acme.amazon.databaseHelper.AAProvider.ProductColumns;
import com.acme.amazon.databaseHelper.AAProvider.ProfileColumns;
import com.acme.amazon.databaseHelper.AAProvider.TransColumns;

public class AAUtils {

    public static final String INTENT_PROFILE_ID = "Item_ID";

    public static final String INTENT_PRODUCT_NAME = "product_name";

    public static final String UNSORT = "unsort";

    public static final String SHARED_PRE_NAME = "aa_shared_pre_name";

    public static final int PRODUCT_LIST_INI_STATE = -1;

    public static final int PRODUCT_LIST_EDIT_STATE = 1;

    public static final int PRODUCT_LIST_SHOW_STATE = 0;

    public static final double RATE_BV = 0.15;

    public static final double RATE_AMAZON_REF_HEALTHY = 0.15;

    public static final String EXPAND_ADAPTER_ORDER = "expand_adapter_order";

    public static final String EXPAND_ADAPTER_FBA = "expand_adapter_fba";

    public static final String EXPAND_ADAPTER_TRANS = "expand_adapter_trans";

    public static final String INTENT_EXTRA_ITEM_STYLE = "intent_extra_item_style";

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

    public static void toContentValues(AAProduct product, ContentValues values) {
        values.put(ProductColumns.PRODUCT_NAME, product.getProductName());
        values.put(ProductColumns.PRODUCT_PRIME_PRICE, product.getMaFullPrice());
        values.put(ProductColumns.PRODUCT_BV_POINT, product.getBVpoint());
        values.put(ProductColumns.PRODUCT_BV_TO_MONEY, product.getBVtoDollar());
        values.put(ProductColumns.PRODUCT_FBA_PRE_FEE, product.getFbaPreFee());
        values.put(ProductColumns.PRODUCT_FBA_SHIPPING_FEE, product.getFBAShipping());
        values.put(ProductColumns.PRODUCT_AMAZON_REF_FEE, product.getAmazonRefFee());
        values.put(ProductColumns.PRODUCT_AMAZON_SALE_PRICE, product.getSalePriceOnAm());
        values.put(ProductColumns.PRODUCT_CATEGORY, product.getCategory());
    }

    public static void toContentValues(AAFbaProfile profile, ContentValues values) {
        values.put(FbaShipReportColumns.SHIP_DATE, profile.getDate());
        values.put(FbaShipReportColumns.SHIP_ITEM_ID, profile.getID());
        values.put(FbaShipReportColumns.SHIP_TITLE, profile.getTitle());
        values.put(FbaShipReportColumns.SHIP_TOTAL_NUM, profile.getCost());
    }

    public static void toContentValues(AAFbaItem item, ContentValues values) {
        values.put(FbaShipReportItemColumns.SHIP_DATE, item.getDate());
        values.put(FbaShipReportItemColumns.ITEM_QUALITY, item.getQuality());
        values.put(FbaShipReportItemColumns.ITEM_NAME, item.getName());
    }

    public static void toContentValues(TransactionNode node, ContentValues values) {
        values.put(TransColumns.aa_tran_date, node.getAa_tran_date());
        values.put(TransColumns.settlement_id, node.getSettlement_id());
        values.put(TransColumns.aa_type, node.getType());
        values.put(TransColumns.order_id, node.getOrder_id());
        values.put(TransColumns.sku, node.getSku());
        values.put(TransColumns.description, node.getDescription());
        values.put(TransColumns.quantiy, node.getQuantiy());
        values.put(TransColumns.marketPlace, node.getMarketPlace());
        values.put(TransColumns.fulfillment, node.getFulfillment());
        values.put(TransColumns.order_city, node.getOrder_city());
        values.put(TransColumns.order_state, node.getOrder_state());
        values.put(TransColumns.order_postal, node.getOrder_postal());
        values.put(TransColumns.product_sales, node.getProduct_sales());
        values.put(TransColumns.shipping_credits, node.getShipping_credits());
        values.put(TransColumns.gift_wrap_credits, node.getGift_wrap_credits());
        values.put(TransColumns.promotional_rebates, node.getPromotional_rebates());
        values.put(TransColumns.sales_tax_collected, node.getSales_tax_collected());
        values.put(TransColumns.selling_fees, node.getSelling_fees());
        values.put(TransColumns.fba_fees, node.getFba_fees());
        values.put(TransColumns.other_transaction_fees, node.getOther_transaction_fees());
        values.put(TransColumns.other, node.getOther());
        values.put(TransColumns.total, node.getTotal());
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

    public static void fromCursor(Cursor cursor, AAProduct product) {
        int idxId = cursor.getColumnIndexOrThrow(ProductColumns._ID);
        int idxName = cursor.getColumnIndexOrThrow(ProductColumns.PRODUCT_NAME);
        int idxPrice = cursor.getColumnIndexOrThrow(ProductColumns.PRODUCT_PRIME_PRICE);
        int idxBv = cursor.getColumnIndexOrThrow(ProductColumns.PRODUCT_BV_POINT);
        int idxBvTo = cursor.getColumnIndexOrThrow(ProductColumns.PRODUCT_BV_TO_MONEY);
        int idxPreFee = cursor.getColumnIndexOrThrow(ProductColumns.PRODUCT_FBA_PRE_FEE);
        int idxFbaShipFee = cursor.getColumnIndexOrThrow(ProductColumns.PRODUCT_FBA_SHIPPING_FEE);
        int idxAmazonRef = cursor.getColumnIndexOrThrow(ProductColumns.PRODUCT_AMAZON_REF_FEE);
        int idxAmazonSalePrice = cursor
                .getColumnIndexOrThrow(ProductColumns.PRODUCT_AMAZON_SALE_PRICE);
        int idxShopComPrice = cursor.getColumnIndexOrThrow(ProductColumns.PRODUCT_CATEGORY);

        product.setID(cursor.getString(idxId));
        product.setProductName(cursor.getString(idxName));
        product.setMaFullPrice(cursor.getString(idxPrice));
        product.setBVpoint(cursor.getString(idxBv));
        product.setBVtoDollar(cursor.getString(idxBvTo));
        product.setFbaPreFee(cursor.getString(idxPreFee));
        product.setFBAShipping(cursor.getString(idxFbaShipFee));
        product.setAmazonRefFee(cursor.getString(idxAmazonRef));
        product.setSalePriceOnAm(cursor.getString(idxAmazonSalePrice));
        product.setCategory(cursor.getString(idxShopComPrice));

        product.setAmazonBasePrice(AAUtils.calAmazonBasePrice(product.getMaFullPrice(),
                product.getFBAShipping(), product.getFbaPreFee()));
        product.setAmazonPricewithBV(AAUtils.calAmazonPricewithBV(product.getMaFullPrice(),
                product.getFBAShipping(), product.getFbaPreFee(), product.getBVtoDollar()));
        product.setProfit(AAUtils.calProfit(product.getMaFullPrice(),
                product.getSalePriceOnAm(), product.getFbaPreFee(), product.getFBAShipping(), product.getAmazonRefFee()));
    }

    public static void fromCursor(Cursor cursor, AAFbaProfile profile) {
        int idxId = cursor.getColumnIndexOrThrow(FbaShipReportColumns._ID);
        int idxDate = cursor.getColumnIndexOrThrow(FbaShipReportColumns.SHIP_DATE);
        int idxOrderId = cursor.getColumnIndexOrThrow(FbaShipReportColumns.SHIP_ITEM_ID);
        int idxTitle = cursor.getColumnIndexOrThrow(FbaShipReportColumns.SHIP_TITLE);
        int idxCost = cursor.getColumnIndexOrThrow(FbaShipReportColumns.SHIP_TOTAL_NUM);

        profile.setProfileId(cursor.getString(idxId));
        profile.setDate(cursor.getString(idxDate));
        profile.setID(cursor.getString(idxOrderId));
        profile.setTitle(cursor.getString(idxTitle));
        profile.setCost(cursor.getString(idxCost));
    }

    public static void fromCursor(Cursor cursor, AAFbaItem item) {
        int idxId = cursor.getColumnIndexOrThrow(FbaShipReportItemColumns._ID);
        int idxDate = cursor.getColumnIndexOrThrow(FbaShipReportItemColumns.SHIP_DATE);
        int idxTitle = cursor.getColumnIndexOrThrow(FbaShipReportItemColumns.ITEM_NAME);
        int idxQuality = cursor.getColumnIndexOrThrow(FbaShipReportItemColumns.ITEM_QUALITY);

        item.setItemId(cursor.getString(idxId));
        item.setDate(cursor.getString(idxDate));
        item.setName(cursor.getString(idxTitle));
        item.setQuality(Integer.parseInt(cursor.getString(idxQuality)));
    }

    public static void fromCursor(Cursor cursor, TransactionNode note) {
        int idxId = cursor.getColumnIndexOrThrow(TransColumns._ID);
        int idxDate = cursor.getColumnIndexOrThrow(TransColumns.aa_tran_date);
        int idxSettlement_id = cursor.getColumnIndexOrThrow(TransColumns.settlement_id);
        int idxType = cursor.getColumnIndexOrThrow(TransColumns.aa_type);
        int idxOrderId = cursor.getColumnIndexOrThrow(TransColumns.order_id);
        int idxSku = cursor.getColumnIndexOrThrow(TransColumns.sku);
        int idxDescription = cursor.getColumnIndexOrThrow(TransColumns.description);
        int idxQuantiy = cursor.getColumnIndexOrThrow(TransColumns.quantiy);
        int idxMarketplace = cursor.getColumnIndexOrThrow(TransColumns.marketPlace);
        int idxFulfillment = cursor.getColumnIndexOrThrow(TransColumns.fulfillment);
        int idxOrder_city = cursor.getColumnIndexOrThrow(TransColumns.order_city);
        int idxOrder_state = cursor.getColumnIndexOrThrow(TransColumns.order_state);
        int idxOrder_postal = cursor.getColumnIndexOrThrow(TransColumns.order_postal);
        int idxProduct_sales = cursor.getColumnIndexOrThrow(TransColumns.product_sales);
        int idxShipping_credits = cursor.getColumnIndexOrThrow(TransColumns.shipping_credits);
        int idxGift_wrap_credits = cursor.getColumnIndexOrThrow(TransColumns.gift_wrap_credits);
        int idxPromotional_rebates = cursor.getColumnIndexOrThrow(TransColumns.promotional_rebates);
        int idxSales_tax_collected = cursor.getColumnIndexOrThrow(TransColumns.sales_tax_collected);
        int idxSelling_fees = cursor.getColumnIndexOrThrow(TransColumns.selling_fees);
        int idxFba_fees = cursor.getColumnIndexOrThrow(TransColumns.fba_fees);
        int idxOther_transaction_fees = cursor.getColumnIndexOrThrow(TransColumns.other_transaction_fees);
        int idxOther = cursor.getColumnIndexOrThrow(TransColumns.other);
        int idxTotal = cursor.getColumnIndexOrThrow(TransColumns.total);

        note.setId(cursor.getString(idxId));
        note.setAa_tran_date(cursor.getString(idxDate));
        note.setSettlement_id(cursor.getString(idxSettlement_id));
        note.setType(cursor.getString(idxType));
        note.setOrder_id(cursor.getString(idxOrderId));
        note.setSku(cursor.getString(idxSku));
        note.setDescription(cursor.getString(idxDescription));
        note.setQuantiy(cursor.getString(idxQuantiy));
        note.setMarketPlace(cursor.getString(idxMarketplace));
        note.setFulfillment(cursor.getString(idxFulfillment));
        note.setOrder_city(cursor.getString(idxOrder_city));
        note.setOrder_state(cursor.getString(idxOrder_state));
        note.setOrder_postal(cursor.getString(idxOrder_postal));
        note.setProduct_sales(cursor.getString(idxProduct_sales));
        note.setShipping_credits(cursor.getString(idxShipping_credits));
        note.setGift_wrap_credits(cursor.getString(idxGift_wrap_credits));
        note.setPromotional_rebates(cursor.getString(idxPromotional_rebates));
        note.setSales_tax_collected(cursor.getString(idxSales_tax_collected));
        note.setSelling_fees(cursor.getString(idxSelling_fees));
        note.setFba_fees(cursor.getString(idxFba_fees));
        note.setOther_transaction_fees(cursor.getString(idxOther_transaction_fees));
        note.setOther(cursor.getString(idxOther));
        note.setTotal(cursor.getString(idxTotal));
    }

    /**
     * Sort order profile list by date
     * 
     * @param profileList
     * @return ArrayList by key year and month for sorted list
     */
    public static synchronized ArrayList<ArrayList<ArrayList<AAProfile>>> sortProfileByDate(
            List<AAProfile> profileList) {
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

    public static synchronized ArrayList<ArrayList<ArrayList<AAFbaProfile>>> sortFbaProfileByDate(
            List<AAFbaProfile> profileList) {
        ArrayList<ArrayList<ArrayList<AAFbaProfile>>> sortListMap = new ArrayList<ArrayList<ArrayList<AAFbaProfile>>>();
        ArrayList<String> yearList = new ArrayList<String>();
        String year = UNSORT;
        String month = UNSORT;
        String day = UNSORT;
        // date structure Month/Date/Year
        for (AAFbaProfile profile : profileList) {
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
                        sortListMap.add(new ArrayList<ArrayList<AAFbaProfile>>());
                        yearList.add(year);
                        break;
                    }
                    if (Integer.parseInt(yearList.get(i)) < Integer.parseInt(year)) {
                        sortListMap.add(i, new ArrayList<ArrayList<AAFbaProfile>>());
                        yearList.add(i, year);
                        break;
                    }
                }
                indexYear = findStElemInArray(yearList, year);
                for (int i = 0; i < 12; i++) {
                    sortListMap.get(indexYear).add(new ArrayList<AAFbaProfile>());
                }
            } else {
                indexYear = findStElemInArray(yearList, year);
            }

            ArrayList<AAFbaProfile> list = sortListMap.get(indexYear).get(indexMonth);
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

    public static synchronized ArrayList<ArrayList<ArrayList<TransactionNode>>> sortTransOrderByDate(
            List<TransactionNode> profileList, ArrayList<String> yearList) {
        ArrayList<ArrayList<ArrayList<TransactionNode>>> sortListMap = new ArrayList<>();
        String year = UNSORT;
        String month = UNSORT;
        String day = UNSORT;
        // date structure Month/Date/Year
        for (TransactionNode profile : profileList) {
            String date = getFormatDateFromAmazon(profile.getAa_tran_date());
            if(date == null)
                continue;
            String[] mdate = date.split("/");
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
                        sortListMap.add(new ArrayList<ArrayList<TransactionNode>>());
                        yearList.add(year);
                        break;
                    }
                    if (Integer.parseInt(yearList.get(i)) < Integer.parseInt(year)) {
                        sortListMap.add(i, new ArrayList<ArrayList<TransactionNode>>());
                        yearList.add(i, year);
                        break;
                    }
                }
                indexYear = findStElemInArray(yearList, year);
                for (int i = 0; i < 12; i++) {
                    sortListMap.get(indexYear).add(new ArrayList<TransactionNode>());
                }
            } else {
                indexYear = findStElemInArray(yearList, year);
            }

            ArrayList<TransactionNode> list = sortListMap.get(indexYear).get(indexMonth);
            for (int i = 0; i <= list.size(); i++) {
                if (i == list.size()) {
                    list.add(profile);
                    break;
                }
                // sort by latest date
                if (Integer.parseInt(getFormatDateFromAmazon(list.get(i).getAa_tran_date()).split("/")[1]) < Integer.parseInt(day)) {
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
     * 
     * @param set
     * @return ArrayList
     */
    public static <T> ArrayList<T> setToArrayList(Set<T> set) {
        if (set == null)
            return null;
        ArrayList<T> tArray = new ArrayList<T>();
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            T t = it.next();
            tArray.add(t);
        }
        return tArray;
    }

    /**
     * Parse ArrayList to Set
     * 
     * @param ArrayList
     * @return Set
     */
    public static <T> Set<T> arrayListToSet(ArrayList<T> list) {
        if (list == null)
            return null;
        Set<T> set = new HashSet<T>();
        for (T t : list) {
            set.add(t);
        }
        return set;
    }

    public static String getTotalProCost(ArrayList<AAProfile> proList) {
        Double cost = 0.0;
        for (AAProfile profile : proList) {
            cost += Double.parseDouble(profile.getCost());
        }
        return String.valueOf(cost);
    }

    public static String getTotalProExtra1(ArrayList<AAProfile> proList) {
        Double cost = 0.0;
        for (AAProfile profile : proList) {
            cost += Double.parseDouble(profile.getExtra1());
        }
        return String.valueOf(cost);
    }

    public static void cvtProListToMap(List<AAProduct> l, Map<String, AAProduct> m) {
        m.clear();
        for (AAProduct p : l) {
            m.put(p.getProductName(), p);
        }
    }

    public static String[] cvtMapKeyToList(Map<String, AAProduct> m, String[] array) {
        array = new String[m.size()];
        Iterator<Entry<String, AAProduct>> it = m.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry<String, AAProduct> pair = it.next();
            array[i] = pair.getKey();
            i++;
        }
        return array;
    }

    /**
     * Calculate total value (dollar) fro BV point
     * 
     * @param bv
     * @return
     */
    public static String calBVtoDollar(String bv) {
        float bv_f = convertStringToFloat(bv);
        return String.format("%.02f", (bv_f * RATE_BV));
    }

    /**
     * Calculate reference fee for amazon product (Healthy Product)
     * 
     * @param bv
     * @return
     */
    public static String calAmazonRefFee(String salePriceOnAm) {
        float ref_f = convertStringToFloat(salePriceOnAm);
        return String.format("%.02f", (ref_f * RATE_AMAZON_REF_HEALTHY));
    }

    /**
     * Calculate lowest price for amazon product (Healthy Product)
     * 
     * @param bv
     * @return
     */
    public static String calAmazonBasePrice(String maFullPrice, String fBAShipping, String fbaPreFee) {
        float full_price = convertStringToFloat(maFullPrice);
        float fba_ship = convertStringToFloat(fBAShipping);
        float fba_pre = convertStringToFloat(fbaPreFee);
        return String.format("%.02f", (full_price + fba_pre + fba_ship)
                / (1 - RATE_AMAZON_REF_HEALTHY));
    }

    /**
     * Calculate lowest price + BV for amazon product (Healthy Product)
     * 
     * @param bv
     * @return
     */
    public static String calAmazonPricewithBV(String maFullPrice, String fBAShipping,
            String fbaPreFee, String bvToDoallor) {
        float full_price = convertStringToFloat(maFullPrice);
        float fba_ship = convertStringToFloat(fBAShipping);
        float fba_pre = convertStringToFloat(fbaPreFee);
        float bv_to_dollar = convertStringToFloat(bvToDoallor);
        return String.format("%.02f", (full_price + fba_pre + fba_ship - bv_to_dollar)
                / (1 - RATE_AMAZON_REF_HEALTHY));
    }

    /**
     * Calculate Profit for amazon product (Healthy Product)
     * 
     * @param bv
     * @return
     */
    public static String calProfit(String basePrice, String salePriceOnAm, String fbaPreFee, String fbaShippingFee, String fbaSaleFee) {
        float base_price = convertStringToFloat(basePrice);
        float sale_amazon_price = convertStringToFloat(salePriceOnAm);
        float fba_pre_fee = convertStringToFloat(fbaPreFee);
        float fba_shipping_fee = convertStringToFloat(fbaShippingFee);
        float fba_sale_fee = convertStringToFloat(fbaSaleFee);
        return String.format("%.02f", (sale_amazon_price - base_price - fba_pre_fee - fba_shipping_fee - fba_sale_fee));
    }

    /**
     * Calculate 2 string times result
     *
     * @param bv
     * @return
     */
    public static String calStringTimes(String a, String b) {
        float f_a = convertStringToFloat(a);
        float f_b = convertStringToFloat(b);
        return String.format("%.02f", (f_a * f_b));
    }

    public static float convertStringToFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String[] stripLeadingAndTrailingQuotes(String[] str) {
        if (str == null) {
            return null;
        }

        for(int i = 0; i < str.length ; i++) {
        if (str[i].startsWith("\"")) {
            str[i] = str[i].substring(1, str[i].length());
        }
        if (str[i].endsWith("\"")) {
            str[i] = str[i].substring(0, str[i].length() - 1);
        }
        }
        return str;
    }

    public static String getFormatDateFromAmazon(String amazonDate) {
        String month = null;
        String date = null;
        String year = null;
        if (TextUtils.isEmpty(amazonDate))
            return "";
        String[] format_1 = amazonDate.split(",");
        String[] month_date = format_1[0].split(" ");
        month = month_date[0];
        date = month_date[1];
        if (format_1[1].startsWith(" ")) {
            format_1[1] = format_1[1].substring(1, format_1[1].length());
        }
        String[] year_plus = format_1[1].split(" ");
        year = year_plus[0];
        if(TextUtils.equals(month,"Jan"))
            month = "1";
        else if(TextUtils.equals(month,"Feb"))
            month = "2";
        else if(TextUtils.equals(month,"Mar"))
            month = "3";
        else if(TextUtils.equals(month,"Apr"))
            month = "4";
        else if(TextUtils.equals(month,"May"))
            month = "5";
        else if(TextUtils.equals(month,"Jun"))
            month = "6";
        else if(TextUtils.equals(month,"Jul"))
            month = "7";
        else if(TextUtils.equals(month,"Aug"))
            month = "8";
        else if(TextUtils.equals(month,"Sep"))
            month = "9";
        else if(TextUtils.equals(month,"Oct"))
            month = "10";
        else if(TextUtils.equals(month,"Nov"))
            month = "11";
        else if(TextUtils.equals(month,"Dev"))
            month = "12";
        return month+"/"+date+"/"+year;
    }

    /**
     * check permission is granted or not
     * @param context
     * @return
     */
    public static int checkPermissionGranted(Context context) {
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return AAConstant.NO_EXTERNAL_STORAGE_PERMISSION;
        }
        return AAConstant.PERMISSION_CHECKING_PASS;
    }

    public static boolean isPermissionGranted(Context context) {
        return AAConstant.PERMISSION_CHECKING_PASS == checkPermissionGranted(context);
    }

    public static void startPermissionDialog(Context context) {
        if(!isPermissionGranted(context))
            context.startActivity(new Intent(context, PermissionDialogPage.class));
    }
}
