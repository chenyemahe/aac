package com.acme.amazon.listsupport;

import com.acme.amazon.orderrecord.SummaryPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Map;

import com.acme.amazon.orderrecord.R;

public class AANormalListViewAdapter extends BaseAdapter{

    private Context mContext;
    private int mLayoutID;
    private Map<String, String> mapData;
    private String[] keyWordList;
    private int mAdapterStyle;

    public AANormalListViewAdapter(Context context, int layoutID, int style) {
        mContext = context;
        mLayoutID = layoutID;
        mAdapterStyle = style;
    }

    @Override
    public int getCount() {
        if (keyWordList == null)
            return 0;
        return keyWordList.hashCode();
    }

    @Override
    public Object getItem(int index) {
        if (keyWordList == null)
            return null;
        return keyWordList[index];
    }

    @Override
    public long getItemId(int index) {
        // TODO Auto-generated method stub
        return index;
    }
    
    @Override
    public View getView(int index, View contentView, ViewGroup parent) {
        if (contentView == null) {
            contentView = LayoutInflater.from(mContext).inflate(mLayoutID,
                    parent, false);
        }
        if (mAdapterStyle == SummaryPage.SUM_TOTAL) {
            ViewHolderTotal holder = new ViewHolderTotal();
            holder.setUpView(contentView);
            holder.setData(index);
            contentView.setTag(holder);
        }
        return contentView;
    }

    public void notifiListUpdate() {
        notifyDataSetChanged();
    }

    public void updateData(Map<String, String> m) {
        mapData = m;
        mapData.keySet().toArray(keyWordList);
        notifiListUpdate();
    }
    
    private class ViewHolderTotal {
        private View mView;
        private TextView tital;
        private TextView content;
        
        public void setUpView(View v) {
            mView = v;
            tital = (TextView) mView.findViewById(R.id.tital);
            content = (TextView) mView.findViewById(R.id.content1);
        }
        
        public void setData(int index) {
            tital.setText(keyWordList[index]);
            content.setText(mapData.get(keyWordList[index]));
        }
    }
}
