package com.acme.amazon.amazonpage.fba;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.acme.amazon.AAConstant;
import com.acme.amazon.AAFbaItem;
import com.acme.amazon.AAFbaProfile;
import com.acme.amazon.AAItem;
import com.acme.amazon.AAManager;
import com.acme.amazon.AAProfile;
import com.acme.amazon.AAUtils;
import com.acme.amazon.listsupport.AAListDataHolder;
import com.acme.amazon.listsupport.AAListViewAdapter;
import com.acme.amazon.orderrecord.AADialogFragment;
import com.acme.amazon.orderrecord.R;

import java.util.ArrayList;
import java.util.Set;

public class FbaShippingAddPage extends Activity implements View.OnClickListener {

    private static final String TAG = "FbaAddPage";

    private AutoCompleteTextView mItemACT;
    private EditText mItemQuantity;
    private Button mItemAddBT;
    private Button mSubmit;
    private EditText mItemPrice;
    private EditText mBV;
    private ListView mListView;
    private AAListDataHolder<AAFbaItem> mListHolder = new AAListDataHolder<>(new ArrayList<AAFbaItem>());
    private AAListViewAdapter mAdapter;
    private String mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_order);
        setLayoutView();
    }

    private void setLayoutView() {
        mItemACT = (AutoCompleteTextView) findViewById(R.id.act_add_item);
        ArrayAdapter<String> sAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getStringArray());
        mItemACT.setAdapter(sAdapter);
        mItemQuantity = (EditText) findViewById(R.id.ed_quantity);
        mItemPrice = (EditText) findViewById(R.id.ed_cost);
        mItemPrice.setVisibility(View.GONE);
        mBV = (EditText) findViewById(R.id.ed_bv);
        mBV.setVisibility(View.GONE);
        mItemAddBT = (Button) findViewById(R.id.bt_add_item);
        mItemAddBT.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.lv_add_item);
        mAdapter = new AAListViewAdapter(this, R.layout.aaitemlistitem,
                AAConstant.ADAPTER_ITEM_LIST, AAUtils.EXPAND_ADAPTER_FBA);
        mListView.setAdapter(mAdapter);

        mSubmit = (Button) findViewById(R.id.bt_submit);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_item:
                saveItem();
                break;
            case R.id.bt_submit:
                submitItem();
                break;

            default:
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mAdapter.notifiListUpdate();
        super.onConfigurationChanged(newConfig);
    }

    private void saveItem() {
        String itemName = mItemACT.getText().toString();
        String itemQuantity = mItemQuantity.getText().toString();
        if (TextUtils.isEmpty(itemName) || TextUtils.isEmpty(itemQuantity)) {
            Toast.makeText(this,
                    getResources().getString(R.string.no_item_info),
                    Toast.LENGTH_LONG).show();
        } else if (!TextUtils.isDigitsOnly(itemQuantity)) {
            Toast.makeText(this,
                    getResources().getString(R.string.number_item_quanatity),
                    Toast.LENGTH_LONG).show();
        }else {
            AAFbaItem item = new AAFbaItem();
            item.setName(itemName);
            item.setQuality(Integer.parseInt(itemQuantity));
            mListHolder.addListData(item);
            mAdapter.setDataHolder(mListHolder);
            mAdapter.notifiListUpdate();
        }
    }

    private void submitItem() {
        final AADialogFragment mDialog = new AADialogFragment();
        mDialog.setListener(new AADialogFragment.AADialogListener() {

            @Override
            public void onSubmitChange() {
                AAFbaProfile profile = new AAFbaProfile();
                for (AAFbaItem item : mListHolder.getList()) {
                    item.setDate(mDate);
                }
                profile.setFbaItemList(mListHolder.getList());
                setDate(mDialog.getDate());
                profile.setDate(mDate);
                AAManager.getManager().getDB()
                        .saveAAFbaProfile(getContentResolver(), profile);
            }
        });
        mDialog.show(getFragmentManager(), TAG);
    }

    private void setDate(String date) {
        mDate = date;
    }

    private String[] getStringArray() {
        String[] array;
        Set<String> productList = null;
        String[] stringArrayFromRes = getResources().getStringArray(
                R.array.list_of_item);
        int a = stringArrayFromRes.length;
        if (productList != null) {
            String[] stringArrayFromSettings = new String[productList.size()];
            int b = stringArrayFromSettings.length;
            stringArrayFromSettings = productList
                    .toArray(stringArrayFromSettings);
            array = new String[a + stringArrayFromSettings.length];
            System.arraycopy(stringArrayFromRes, 0, array, 0, a);
            System.arraycopy(stringArrayFromSettings, 0, array, a, b);
        } else {
            array = stringArrayFromRes;
        }
        return array;
    }
}
