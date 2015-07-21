package com.acme.amazon.amazonpage;

import com.acme.amazon.AAProduct;
import com.acme.amazon.orderrecord.R;

import android.view.View;
import android.widget.TextView;

public class ProductListHodler {

    private TextView mRealPrice;
    private TextView mAmazonMinPrice;
    private TextView mSalePrice;
    private TextView mProfit;
    
    public void setUpView(View v) {
        mRealPrice = (TextView) v.findViewById(R.id.tv_real_price_number);
        mAmazonMinPrice = (TextView) v.findViewById(R.id.tv_amazon_min_number);
        mSalePrice = (TextView) v.findViewById(R.id.tv_sale_price_number);
        mProfit = (TextView) v.findViewById(R.id.tv_profit_number);
    }

    public void setData(AAProduct product) {
        mRealPrice.setText(product.getMaFullPrice());
        mAmazonMinPrice.setText(product.getAmazonBasePrice());
        mSalePrice.setText(product.getSalePriceOnAm());
        mProfit.setText(product.getProfit());
    }
}
