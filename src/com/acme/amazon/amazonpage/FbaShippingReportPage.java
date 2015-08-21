
package com.acme.amazon.amazonpage;

import com.acme.amazon.AAManager;
import com.acme.amazon.AAProfile;
import com.acme.amazon.AAUtils;
import com.acme.amazon.listsupport.AAExpandableListAdapter;
import com.acme.amazon.listsupport.AAListDataHolder;
import com.acme.amazon.orderrecord.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class FbaShippingReportPage extends Activity {

    private ExpandableListView mListView;

    private AAExpandableListAdapter mExpandAdapter;

    private AAListDataHolder<AAProfile> mListHolder;

    private ArrayList<ArrayList<ArrayList<AAProfile>>> mExpandDataList;

    private ArrayList<ArrayList<AAProfile>> mChildList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fba_shipping_report);
        mListView = (ExpandableListView) findViewById(R.id.listView1);
        mExpandAdapter = new AAExpandableListAdapter();
        mListView.setAdapter(mExpandAdapter);
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
                    mChildList.add(new ArrayList<AAProfile>());
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
        mExpandDataList = AAUtils.sortProfileByDate(AAManager.getManager().getDB()
                .getAllProfile(getContentResolver()));
        mChildList = new ArrayList<ArrayList<AAProfile>>();
        setSignleLevelChildData();
        mExpandAdapter.setListData(mExpandDataList, mChildList, this);
    }
}
