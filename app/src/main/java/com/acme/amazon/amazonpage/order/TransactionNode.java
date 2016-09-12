package com.acme.amazon.amazonpage.order;

/**
 * Created by zhengmeiling on 9/5/16.
 */
public class TransactionNode {

    private String id;
    private String aa_tran_date;
    private String settlement_id;
    private String aa_type;
    private String order_id;
    private String sku;
    private String description;
    private String quantiy;
    private String marketPlace;
    private String fulfillment;
    private String order_city;
    private String order_state;
    private String order_postal;
    private String product_sales;
    private String shipping_credits;
    private String gift_wrap_credits;
    private String promotional_rebates;
    private String sales_tax_collected;
    private String selling_fees;
    private String fba_fees;
    private String other_transaction_fees;
    private String other;
    private String total;

    public void setId(String s) {
        id = s;
    }
    public void setAa_tran_date(String s) {
        aa_tran_date = s;
    }
    public void setSettlement_id(String s) {
        settlement_id = s;
    }
    public void setType(String s) {
        aa_type = s;
    }
    public void setOrder_id(String s) {
        order_id = s;
    }
    public void setSku(String s) {
        sku = s;
    }
    public void setDescription(String s) {
        description = s;
    }
    public void setQuantiy(String s) {
        quantiy = s;
    }
    public void setMarketPlace(String s) {
        marketPlace = s;
    }
    public void setFulfillment(String s) {
        fulfillment = s;
    }
    public void setOrder_city(String s) {
        order_city = s;
    }
    public void setOrder_state(String s) {
        order_state = s;
    }
    public void setOrder_postal(String s) {
        order_postal = s;
    }
    public void setProduct_sales(String s) {
        product_sales = s;
    }
    public void setShipping_credits(String s) {
        shipping_credits = s;
    }
    public void setGift_wrap_credits(String s) {
        gift_wrap_credits = s;
    }
    public void setPromotional_rebates(String s) {
        promotional_rebates = s;
    }
    public void setSales_tax_collected(String s) {
        sales_tax_collected = s;
    }
    public void setSelling_fees(String s) {
        selling_fees = s;
    }
    public void setFba_fees(String s) {
        fba_fees = s;
    }
    public void setOther_transaction_fees(String s) {
        other_transaction_fees = s;
    }
    public void setOther(String s) {
        other = s;
    }
    public void setTotal(String s) {
        total = s;
    }

    public String getId() {
        return id;
    }
    public String getAa_tran_date() {
        return aa_tran_date;
    }
    public String getSettlement_id() {
        return settlement_id;
    }
    public String getType() {
        return aa_type;
    }
    public String getOrder_id() {
        return order_id;
    }
    public String getSku() {
        return sku;
    }
    public String getDescription() {
        return description;
    }
    public String getQuantiy() {
        return quantiy;
    }
    public String getMarketPlace() {
        return marketPlace;
    }
    public String getFulfillment() {
        return fulfillment;
    }
    public String getOrder_city() {
        return order_city;
    }
    public String getOrder_state() {
        return order_state;
    }
    public String getOrder_postal() {
        return order_postal;
    }
    public String getProduct_sales() {
        return product_sales;
    }
    public String getShipping_credits() {
        return shipping_credits;
    }
    public String getGift_wrap_credits() {
        return gift_wrap_credits;
    }
    public String getPromotional_rebates() {
        return promotional_rebates;
    }
    public String getSales_tax_collected() {
        return sales_tax_collected;
    }
    public String getSelling_fees() {
        return selling_fees;
    }
    public String getFba_fees() {
        return fba_fees;
    }
    public String getOther_transaction_fees() {
        return other_transaction_fees;
    }
    public String getOther() {
        return other;
    }
    public String getTotal() {
        return total;
    }
}
