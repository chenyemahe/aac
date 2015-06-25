package com.acme.amazon.listsupport;

import java.util.ArrayList;
import com.acme.amazon.AAProfile;
import com.acme.amazon.orderrecord.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class AAExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<ArrayList<AAProfile>>> mList;
    private ArrayList<String> mGroupNameList;
    private ArrayList<ArrayList<AAProfile>> mChildList;
    private AAListDataHolder<?> mListDataHodler;
    private Context mContext;
    private static final int GROUP_PADDING = 10;

    @Override
    public int getGroupCount() {
        if(mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getSignleLevelChildNum(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(mList == null)
            return null;
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(mChildList == null)
            return null;
        return mChildList.get(groupPosition).get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView text = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.aa_group_view, parent, false);
            convertView.setLongClickable(false);
        }
        text = (TextView) convertView.findViewById(R.id.tv_group_name);
        text.setText(mGroupNameList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.aalistitem, parent, false);
        }
        AAListViewHodler holder = new AAListViewHodler();
        holder.setOrderListView(convertView);
        holder.setData(mChildList.get(groupPosition).get(childPosition));
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
        if(mChildList.get(groupPosition).size() != 0) {
            childNum = mChildList.get(groupPosition).size();
        }
        return childNum;
    }

    public void setListData(ArrayList<ArrayList<ArrayList<AAProfile>>> list, ArrayList<ArrayList<AAProfile>> childList, Context context) {
        mList = list;
        mContext = context;
        mGroupNameList = new ArrayList<String>();
        mChildList = childList;
        for (int i = 0; i < mList.size(); i++) {
            for (int j = 0; j < mList.get(i).size(); j++) {
                if (mList.get(i).get(j).size() != 0) {
                    mGroupNameList.add(mList.get(i).get(j).get(0).getDate().split("/")[2]);
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
