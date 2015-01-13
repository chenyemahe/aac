package com.acme.amazon.orderrecord;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.acme.amazon.AAConstant;
import com.acme.amazon.AAItem;

public class AddNewOrder extends Activity implements OnClickListener{

    private AutoCompleteTextView mItemACT;
    private EditText mItemQuantity;
    private Button mItemAddBT;
    private EditText mItemPrice;
    private ListView mListView;
    private AAListDataHolder<AAItem> mListHolder;
    private AAListViewAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_order);
        setLayoutView();
        mListHolder = new AAListDataHolder<>(new ArrayList<AAItem>());
    }
    
    private void setLayoutView(){
        mItemACT = (AutoCompleteTextView) findViewById(R.id.act_add_item);
        ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.list_of_item));
        mItemACT.setAdapter(sAdapter);
        mItemQuantity = (EditText) findViewById(R.id.ed_quantity);
        mItemPrice = (EditText) findViewById(R.id.ed_cost);
        mItemAddBT = (Button) findViewById(R.id.bt_add_item);
        mItemAddBT.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.lv_add_item);
        mAdapter = new AAListViewAdapter(this,R.layout.aaitemlistitem,AAConstant.ADAPTER_ITEM_LIST);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.bt_add_item:
            saveItem();
            break;

        default:
            break;
        }
    }
    
    private void saveItem() {
    	String itemName = mItemACT.getText().toString();
    	String itemQuanatity = mItemQuantity.getText().toString();
    	String itemCost = mItemPrice.getText().toString();
    	if(TextUtils.isEmpty(itemName) || TextUtils.isEmpty(itemQuanatity) || TextUtils.isEmpty(itemCost)) {
    		Toast.makeText(this, getResources().getString(R.string.no_item_info) , Toast.LENGTH_LONG).show();
    	} else {
    		AAItem item = new AAItem();
    		item.setName(itemName);
    		item.setQuality(Integer.parseInt(itemQuanatity));
    		item.setCost(itemCost);
    		mListHolder.addListData(item);
    		mAdapter.setDataHolder(mListHolder);
    		mAdapter.notifiListUpdate();
    	}
    }
}
