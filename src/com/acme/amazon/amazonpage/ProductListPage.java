
package com.acme.amazon.amazonpage;

import com.acme.amazon.AAProfile;
import com.acme.amazon.listsupport.AANormalListViewAdapter;
import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.Map;

public class ProductListPage extends Activity {
    public static final String PRODUCT_LIST_PAGE = "product_list_page";

    private ListView mListView;
    private Button mAddBT;

    private Map<String, AAProfile> mProductMap;

    private AANormalListViewAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_prod_price_info_list);
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
                
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        productListAdapter.updateData(mProductMap);
    }
}
