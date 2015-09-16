
package com.acme.amazon.amazonpage;

import com.acme.amazon.AAManager;
import com.acme.amazon.AAProduct;
import com.acme.amazon.AAProfile;
import com.acme.amazon.AAUtils;
import com.acme.amazon.listsupport.AAListViewHodler;
import com.acme.amazon.listsupport.AANormalListViewAdapter;
import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListPage extends Activity implements OnItemClickListener,
        OnItemLongClickListener {
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
        productListAdapter = new AANormalListViewAdapter(this, R.layout.aa_prod_detail_list_item,
                PRODUCT_LIST_PAGE);
        mListView = (ListView) findViewById(R.id.lv_prod_detail_list);
        mListView.setAdapter(productListAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        mAddBT = (Button) findViewById(R.id.bt_prod_add);
        mAddBT.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductListDetailPage.class);
                intent.putExtra(ProductListDetailPage.INTENT_EXTRA_DETAIL_PAGE,
                        ProductListDetailPage.AMAZON_PRODUCT_ADD);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setViewData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AAProduct product = mProductMap.get(productListAdapter.getkeyWordList()[position]);
        startDetailPage(product, ProductListDetailPage.AMAZON_PRODUCT_VIEW);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AAProduct product = mProductMap.get(productListAdapter.getkeyWordList()[position]);
        if (product != null) {
            showItemMenu(product);
            return true;
        }
        return false;
    }

    private void showItemMenu(final AAProduct product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setItems(R.array.list_of_main, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int itemPos) {
                switch (itemPos) {
                    case 0:
                        startDetailPage(product, ProductListDetailPage.AMAZON_PRODUCT_EDIT);
                        break;
                    case 1:
                        AAManager.getManager().getDB()
                                .deleteAAProduct(getContentResolver(), product);
                        setViewData();
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void setViewData() {
        mProductList = AAManager.getManager().getDB().getAllProduct(getContentResolver());
        AAUtils.cvtProListToMap(mProductList, mProductMap);
        productListAdapter.updateData(mProductMap);
        productListAdapter.notifiListUpdate();
    }

    private void startDetailPage(AAProduct product, String action) {
        if (product != null) {
            Intent intent = new Intent(mContext, ProductListDetailPage.class);
            intent.putExtra(ProductListDetailPage.INTENT_EXTRA_DETAIL_PAGE, action);
            intent.putExtra(AAUtils.INTENT_PRODUCT_NAME, product.getProductName());
            startActivity(intent);
        }
    }
}
