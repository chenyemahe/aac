
package com.acme.amazon.amazonpage;

import com.acme.amazon.AAFbaProfile;
import com.acme.amazon.AAManager;
import com.acme.amazon.AAUtils;
import com.acme.amazon.listsupport.AAExpandableListAdapter;
import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class FbaShippingReportPage extends Activity implements OnClickListener{

    private ExpandableListView mListView;
    
    private Button mAddBt;

    private AAExpandableListAdapter mExpandAdapter;

    private ArrayList<ArrayList<ArrayList<AAFbaProfile>>> mExpandDataList;

    private ArrayList<ArrayList<AAFbaProfile>> mChildList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fba_shipping_report);
        mListView = (ExpandableListView) findViewById(R.id.expand_lv);
        mExpandAdapter = new AAExpandableListAdapter(AAUtils.EXPAND_ADAPTER_FBA);
        mListView.setAdapter(mExpandAdapter);
        mAddBt = (Button) findViewById(R.id.bt_add);
        mAddBt.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setExpViewData();
    }

    private void setSignleLevelChildData() {
        if (mExpandDataList != null && mExpandDataList.size() != 0) {
            for (int i = 0; i < mExpandDataList.size(); i++) {
                if (mChildList.size() <= i) {
                    mChildList.add(new ArrayList<AAFbaProfile>());
                }
                for (int j = mExpandDataList.get(i).size() - 1; j >= 0; j--) {
                    for (int k = 0; k < mExpandDataList.get(i).get(j).size(); k++) {
                        mChildList.get(i).add(mExpandDataList.get(i).get(j).get(k));
                    }
                }
            }
        }
    }

    private void setExpViewData() {
        mExpandDataList = AAUtils.sortFbaProfileByDate(AAManager.getManager().getDB()
                .getAllFbaProfile(getContentResolver()));
        mChildList = new ArrayList<ArrayList<AAFbaProfile>>();
        setSignleLevelChildData();
        mExpandAdapter.setFbaListData(mExpandDataList, mChildList, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                startActivity(new Intent(this,FbaShippingAddPage.class));
                break;
        }
    }
}
