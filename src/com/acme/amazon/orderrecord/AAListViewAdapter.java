package com.acme.amazon.orderrecord;

import com.acme.amazon.AAConstant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AAListViewAdapter extends BaseAdapter{
    
    private Context mContext;
    private int mLayoutID;
    private AAListDataHolder<?> mListDataHodler;
    private int mAdapterStyle;
    
    public AAListViewAdapter(Context context, int layoutID, int style){
        mContext = context;
        mLayoutID = layoutID;
        mAdapterStyle = style;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 5;
    }

    @Override
    public Object getItem(int index) {
        // TODO Auto-generated method stub
        return index;
    }

    @Override
    public long getItemId(int index) {
        // TODO Auto-generated method stub
        return index;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup parent) {
        View contentView = LayoutInflater.from(mContext).inflate(mLayoutID, parent, false);
        AAListViewHodler holder = new AAListViewHodler();
        if(mAdapterStyle == AAConstant.ADAPTER_ORIDER_LIST) {
        	holder.setOrderListView();
        	holder.setData(profile);
        } else if(mAdapterStyle == AAConstant.ADAPTER_ITEM_LIST) {
        	holder.setItemListView();
        	holder.setData(item);
        }
        contentView.setTag(holder);
        return contentView;
    }
    
    public void notifiListUpdate() {
    	notifyDataSetChanged();
    }
    
    public void setDataHolder(AAListDataHolder<?> holder){
    	mListDataHodler = holder;
    }
    
}
