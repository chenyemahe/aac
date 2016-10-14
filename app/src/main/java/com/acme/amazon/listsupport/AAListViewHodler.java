package com.acme.amazon.listsupport;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.acme.amazon.AAItem;
import com.acme.amazon.AAProfile;
import com.acme.amazon.AAUtils;
import com.acme.amazon.amazonpage.order.TransactionNode;
import com.acme.amazon.orderrecord.R;

public class AAListViewHodler {

    //Order List
    private TextView mOLDate;
    private TextView mOLTitle;
    private TextView mOLTotal;
    private TextView mOLBv;
    //Item List
    private TextView mItemName;
    private TextView mItemQuantity;
    private TextView mItemCost;

    private String mStyle;

    private int groupId;
    private int childId;

    public AAListViewHodler(String style) {
        mStyle = style;
    }

    public void setOrderListView(View v) {
        mOLDate = (TextView) v.findViewById(R.id.tv_date);
        mOLTitle = (TextView) v.findViewById(R.id.tv_title);
        mOLTotal = (TextView) v.findViewById(R.id.tv_cost);
        mOLBv = (TextView) v.findViewById(R.id.tv_bv);
    }

    public void setItemListView(View v) {
        mItemName = (TextView) v.findViewById(R.id.tv_name);
        mItemQuantity = (TextView) v.findViewById(R.id.tv_quantity);
        mItemCost = (TextView) v.findViewById(R.id.tv_cost);
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_FBA)) {
            mItemCost.setVisibility(View.GONE);
        }
    }

    public void setData(AAProfile profile) {
        mOLDate.setText(profile.getDate());
        mOLTitle.setText(profile.getTitle());
        mOLTotal.setText("$ " + profile.getCost());
    }

    public void setData(AAItem item) {
        mItemName.setText(" - " + item.getName());
        mItemQuantity.setText(" : " + String.valueOf(item.getQuality()));
        mItemCost.setText("Total Cost : " + item.getCost());
    }


    public void setData(TransactionNode node) {
        mOLDate.setText(node.getAa_tran_date());
        mOLBv.setText(node.getOrder_id());
        mOLTitle.setText(node.getDescription());
        String t = "$ " + node.getTotal();
        mOLTotal.setText(t);
    }

    public void setExpandId(int group, int child) {
        groupId = group;
        childId = child;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getChildId() {
        return childId;
    }
}
