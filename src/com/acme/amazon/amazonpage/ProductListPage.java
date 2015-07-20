package com.acme.amazon.amazonpage;

import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProductListPage extends Activity {

	private TextView mBasePrice;
	private TextView mAmazonMinPrice;
	private TextView mSalePrice;
	private TextView mProfit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_prod_price_info_list);
		setLayout();
	}

	private void setLayout() {
		mBasePrice = (TextView) findViewById(R.id.tv_real_price_number);
		mAmazonMinPrice = (TextView) findViewById(R.id.tv_amazon_min_number);
		mSalePrice = (TextView) findViewById(R.id.tv_sale_price_number);
		mProfit = (TextView) findViewById(R.id.tv_profit_number);
	}
}
