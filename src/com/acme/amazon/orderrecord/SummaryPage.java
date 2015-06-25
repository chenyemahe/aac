
package com.acme.amazon.orderrecord;

import com.acme.amazon.AAManager;
import com.acme.amazon.AAProfile;
import com.acme.amazon.AAUtils;
import com.acme.amazon.listsupport.AANormalListViewAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SummaryPage extends Activity implements OnClickListener {

    public static final int SUM_TOTAL = 0;

    private Map<String, String> summaryData;

    private String[] title = {
            "Total Cost", "Total BV"
    };

    private Button mTotalButton;

    private ArrayList<AAProfile> mProfileList;

    private ListView list1;

    private AANormalListViewAdapter adapter1;

    private boolean total_bt_status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_sumary_page);
        mTotalButton = (Button) findViewById(R.id.tv_total);
        mTotalButton.setOnClickListener(this);
        summaryData = new HashMap<String, String>();
        list1 = (ListView) findViewById(R.id.list_total);
        adapter1 = new AANormalListViewAdapter(this, R.layout.aa_sumary_list_item, SUM_TOTAL);
        list1.setAdapter(adapter1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
        adapter1.updateData(summaryData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_total:
                updateTotalList();
                break;
        }
    }

    private void updateTotalList() {
        if (total_bt_status) {
            list1.setVisibility(View.GONE);
        } else {
            list1.setVisibility(View.VISIBLE);
        }
    }

    private void updateData() {
        mProfileList = (ArrayList<AAProfile>) AAManager.getManager().getDB()
                .getAllProfile(getContentResolver());
        summaryData.put(title[0], AAUtils.getTotalProCost(mProfileList));
        summaryData.put(title[1], AAUtils.getTotalProExtra1(mProfileList));
    }

}
