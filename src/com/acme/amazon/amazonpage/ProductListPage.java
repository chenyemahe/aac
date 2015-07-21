package com.acme.amazon.amazonpage;

import com.acme.amazon.listsupport.AANormalListViewAdapter;
import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ProductListPage extends Activity {
    public static final String PRODUCT_LIST_PAGE = "product_list_page";
    
    private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aa_prod_price_info_list);
		setLayout();
	}

	private void setLayout() {
	    AANormalListViewAdapter productListAdapter = new AANormalListViewAdapter(this, R.layout.aa_prod_list_detal_page, PRODUCT_LIST_PAGE);
	    mListView = (ListView) findViewById(R.id.lv_prod_detail_list);
	    mListView.setAdapter(productListAdapter);
	}
}
