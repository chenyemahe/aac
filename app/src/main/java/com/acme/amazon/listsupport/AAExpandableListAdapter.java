
package com.acme.amazon.listsupport;

import java.util.ArrayList;

import com.acme.amazon.AAFbaProfile;
import com.acme.amazon.AAProfile;
import com.acme.amazon.AAUtils;
import com.acme.amazon.amazonpage.order.TransactionNode;
import com.acme.amazon.orderrecord.R;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class AAExpandableListAdapter extends BaseExpandableListAdapter {

    private String mStyle;

    private ArrayList<ArrayList<ArrayList<AAProfile>>> mOrderList;

    private ArrayList<ArrayList<ArrayList<AAFbaProfile>>> mFbaList;

    private ArrayList<ArrayList<ArrayList<TransactionNode>>> mTransList;

    private ArrayList<String> mGroupNameList;

    private ArrayList<ArrayList<AAProfile>> mChildList;

    private ArrayList<ArrayList<AAFbaProfile>> mFbaChildList;

    private ArrayList<ArrayList<TransactionNode>> mTransChildList;

    private AAListDataHolder<?> mListDataHodler;

    private Context mContext;

    private static final int GROUP_PADDING = 10;

    public AAExpandableListAdapter(String style) {
        mStyle = style;
    }

    @Override
    public int getGroupCount() {
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_ORDER)) {
            if (mOrderList == null)
                return 0;
            else
                return mOrderList.size();
        }
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_FBA)) {
            if (mFbaList == null)
                return 0;
            else
                return mFbaList.size();
        }
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_TRANS)) {
            if (mTransList == null)
                return 0;
            else
                return mTransList.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getSignleLevelChildNum(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_ORDER)) {
            if (mOrderList == null)
                return null;
            return mOrderList.get(groupPosition);
        }
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_FBA)) {
            if (mFbaList == null)
                return 0;
            return mFbaList.get(groupPosition);
        }
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_TRANS)) {
            if (mTransList == null)
                return 0;
            else
                return mTransList.get(groupPosition);
        }
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_ORDER)) {
            if (mChildList == null)
                return null;
            return mChildList.get(groupPosition).get(childPosition);
        }
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_FBA)) {
            if (mFbaChildList == null)
                return null;
            return mFbaChildList.get(groupPosition).get(childPosition);
        }
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_TRANS)) {
            if (mTransList == null)
                return null;
            else
                return mTransList.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        TextView text = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.aa_group_view, parent,
                    false);
            convertView.setLongClickable(false);
        }
        text = (TextView) convertView.findViewById(R.id.tv_group_name);
        text.setText(mGroupNameList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.aalistitem, parent, false);
        }

        AAListViewHodler holder = new AAListViewHodler(mStyle);
        holder.setOrderListView(convertView);
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_ORDER)) {
            holder.setData(mChildList.get(groupPosition).get(childPosition));
        }
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_FBA)) {
            holder.setData(mFbaChildList.get(groupPosition).get(childPosition));
        }
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_TRANS)) {
            holder.setData(mTransChildList.get(groupPosition).get(childPosition));
        }
        holder.setExpandId(groupPosition, childPosition);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    private int getSignleLevelChildNum(int groupPosition) {
        int childNum = 0;
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_ORDER)) {
            if (mChildList.get(groupPosition).size() != 0) {
                childNum = mChildList.get(groupPosition).size();
            }
        }
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_FBA)) {
            if (mFbaChildList.get(groupPosition).size() != 0) {
                childNum = mFbaChildList.get(groupPosition).size();
            }
        }
        if (TextUtils.equals(mStyle, AAUtils.EXPAND_ADAPTER_TRANS)) {
            if (mTransChildList.get(groupPosition).size() != 0) {
                childNum = mTransChildList.get(groupPosition).size();
            }
        }
        return childNum;
    }

    public void setListData(ArrayList<ArrayList<ArrayList<AAProfile>>> list,
            ArrayList<ArrayList<AAProfile>> childList, Context context) {
        mOrderList = list;
        mContext = context;
        mGroupNameList = new ArrayList<>();
        mChildList = childList;
        for (int i = 0; i < mOrderList.size(); i++) {
            for (int j = 0; j < mOrderList.get(i).size(); j++) {
                if (mOrderList.get(i).get(j).size() != 0) {
                    mGroupNameList.add(mOrderList.get(i).get(j).get(0).getDate().split("/")[2]);
                    break;
                }
            }
        }
        notifiListUpdate();
    }

    public void setFbaListData(ArrayList<ArrayList<ArrayList<AAFbaProfile>>> list,
            ArrayList<ArrayList<AAFbaProfile>> childList, Context context) {
        mFbaList = list;
        mContext = context;
        mGroupNameList = new ArrayList<>();
        mFbaChildList = childList;
        for (int i = 0; i < mFbaList.size(); i++) {
            for (int j = 0; j < mFbaList.get(i).size(); j++) {
                if (mFbaList.get(i).get(j).size() != 0) {
                    mGroupNameList.add(mFbaList.get(i).get(j).get(0).getDate().split("/")[2]);
                    break;
                }
            }
        }
        notifiListUpdate();
    }

    public void setTransListData(ArrayList<ArrayList<ArrayList<TransactionNode>>> list,
                               ArrayList<ArrayList<TransactionNode>> childList, Context context) {
        mTransList = list;
        mContext = context;
        mGroupNameList = new ArrayList<>();
        mTransChildList = childList;
        for (int i = 0; i < mTransList.size(); i++) {
            for (int j = 0; j < mTransList.get(i).size(); j++) {
                if (mTransList.get(i).get(j).size() != 0) {
                    mGroupNameList.add(AAUtils.getFormatDateFromAmazon(mTransList.get(i).get(j).get(0).getAa_tran_date()).split("/")[2]);
                    break;
                }
            }
        }
        notifiListUpdate();
    }

    public void notifiListUpdate() {
        notifyDataSetChanged();
    }
}
