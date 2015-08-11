package com.acme.amazon.amazonpage;

import com.acme.amazon.AAManager;
import com.acme.amazon.AAProduct;
import com.acme.amazon.AAUtils;
import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProductListDetailPage extends Activity{

    public static final String AMAZON_PRODUCT_ADD = "amazon_product_add";
    public static final String AMAZON_PRODUCT_VIEW = "amazon_product_view";
    
    private String mProductName;
    private AAProduct mProduct;
    
    private TextView mShop_Com;
    private TextView mBase_Price;
    private TextView mBV;
    private TextView mBV_Dollar;
    private TextView mFBA_Pre_Fee;
    private TextView mFBA_Pre_Ship_Fee;
    private TextView mBV;
    private TextView mBV;
    private TextView mBV;
    private TextView mBV;
    private TextView mBV;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_prod_list_detal_page);
        mProductName = getIntent().getStringExtra(AAUtils.INTENT_PRODUCT_NAME);
        mProduct = AAManager.getManager().getDB().getAAProductByName(getContentResolver(), mProductName);
    }
}
