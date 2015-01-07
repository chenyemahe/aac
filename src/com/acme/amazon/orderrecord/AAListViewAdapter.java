package com.acme.amazon.orderrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AAListViewAdapter extends BaseAdapter{
    
    private Context mContext;
    
    public AAListViewAdapter(Context context){
        mContext = context;
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
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.aalistitem, parent, false);
        return contentView;
    }
}
