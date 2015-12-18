package com.acme.amazon.orderrecord;

import java.util.List;

import com.acme.amazon.AAConstant;
import com.acme.amazon.AAItem;
import com.acme.amazon.AAManager;
import com.acme.amazon.AAProfile;
import com.acme.amazon.AAUtils;
import com.acme.amazon.listsupport.AAListDataHolder;
import com.acme.amazon.listsupport.AAListViewAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AAItemListPage extends Activity implements OnItemClickListener {

    private ListView mListView;
    private AAProfile mProfile;
    private String mProfileId;
    private AAListDataHolder<AAItem> mListHolder;
    private AAListViewAdapter mAdapter;
    private List<AAItem> mItemList;
    private String mStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_item_page);
        setLayoutView();
    }

    private void setLayoutView() {
        mListView = (ListView) findViewById(R.id.lv_item);
        mProfileId = getIntent().getStringExtra(AAUtils.INTENT_PROFILE_ID);
        mStyle = getIntent().getStringExtra(AAUtils.INTENT_EXTRA_ITEM_STYLE);
        mProfile = AAManager.getManager().getDB().getAAProfileById(getContentResolver(), mProfileId);
        mAdapter = new AAListViewAdapter(this, R.layout.aaitemlistitem,
                AAConstant.ADAPTER_ITEM_LIST, mStyle);
        mListView.setAdapter(mAdapter);
        mItemList = mProfile.getItemList();
        mListHolder = new AAListDataHolder<>(mItemList);
        mAdapter.setDataHolder(mListHolder);
        mListView.setOnItemClickListener(this);
    }

    private void showItemMenu(final AAItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setItems(R.array.list_of_main, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int itemPos) {
                switch (itemPos) {
                    case 0:
                        break;
                    case 1:
                        AAManager.getManager().getDB().deleteItem(getContentResolver(), item.getItemId());
                        mAdapter.notifiListUpdate();
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showItemMenu(mItemList.get(position));
    }
}
