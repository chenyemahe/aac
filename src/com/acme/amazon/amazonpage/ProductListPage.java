
package com.acme.amazon.amazonpage;

import com.acme.amazon.AAManager;
import com.acme.amazon.AAProduct;
import com.acme.amazon.AAUtils;
import com.acme.amazon.listsupport.AANormalListViewAdapter;
import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListPage extends Activity {
    public static final String PRODUCT_LIST_PAGE = "product_list_page";

    private ListView mListView;
    private Button mAddBT;

    private Map<String, AAProduct> mProductMap;
    private List<AAProduct> mProductList;
    private Context mContext;

    private AANormalListViewAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_prod_price_info_list);
        mContext = this;
        mProductMap = new HashMap<String, AAProduct>();
        setLayout();
    }

    private void setLayout() {
        productListAdapter = new AANormalListViewAdapter(this, R.layout.aa_prod_list_detal_page,
                PRODUCT_LIST_PAGE);
        mListView = (ListView) findViewById(R.id.lv_prod_detail_list);
        mListView.setAdapter(productListAdapter);
        mAddBT = (Button) findViewById(R.id.bt_prod_add);
        mAddBT.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductListDetailPage.class);
                intent.putExtra(ProductListDetailPage.INTENT_EXTRA_DETAIL_PAGE, ProductListDetailPage.AMAZON_PRODUCT_ADD);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProductList = AAManager.getManager().getDB().getAllProduct(getContentResolver());
        AAUtils.cvtProListToMap(mProductList, mProductMap);
        productListAdapter.updateData(mProductMap);
    }
}
