package com.acme.amazon.amazonpage;

import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.os.Bundle;

public class ProductListDetailPage extends Activity{

    public static final String AMAZON_PRODUCT_ADD = "amazon_product_add";
    public static final String AMAZON_PRODUCT_VIEW = "amazon_product_view";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_prod_list_detal_page);
    }
}
