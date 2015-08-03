
package com.acme.amazon.listsupport;

import com.acem.amazon.logging.Logging;
import com.acme.amazon.AAProduct;
import com.acme.amazon.amazonpage.ProductListHodler;
import com.acme.amazon.amazonpage.ProductListPage;
import com.acme.amazon.orderrecord.AASummaryPage;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import com.acme.amazon.orderrecord.R;

public class AANormalListViewAdapter extends BaseAdapter {

    private Context mContext;

    private int mLayoutID;

    private Map<String, String> summaryMapData;

    private Map<String, AAProduct> productMapData;

    private String[] keyWordList;

    private String mAdapterStyle;

    public AANormalListViewAdapter(Context context, int layoutID, String style) {
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
            contentView = LayoutInflater.from(mContext).inflate(mLayoutID, parent, false);
        }
        if (mAdapterStyle.equals(AASummaryPage.SUMMARY_PAGE)) {
            ViewHolderTotal holder = new ViewHolderTotal();
            holder.setUpView(contentView);
            holder.setData(index);
            contentView.setTag(holder);
        }
        if (TextUtils.equals(mAdapterStyle, ProductListPage.PRODUCT_LIST_PAGE)) {
            ProductListHodler holder = new ProductListHodler();
            holder.setUpView(contentView);
            holder.setData(productMapData.get(keyWordList[index]));
            contentView.setTag(holder);
        }
        return contentView;
    }

    public void notifiListUpdate() {
        notifyDataSetChanged();
    }

    /**
     * Set Data for Adapter, must called for data setting
     * @param m
     */
    @SuppressWarnings("unchecked")
    public <T> void updateData(Map<String, T> m) {
        if (TextUtils.equals(mAdapterStyle, AASummaryPage.SUMMARY_PAGE)) {
            try {
                summaryMapData = (Map<String, String>) m;
                summaryMapData.keySet().toArray(keyWordList);
            } catch (ClassCastException e) {
                Logging.logE("Summary list page data parsing fail! Not a String Map.");
                summaryMapData = new HashMap<String, String>();
            }
        }
        if (TextUtils.equals(mAdapterStyle, ProductListPage.PRODUCT_LIST_PAGE)) {
            try {
                productMapData = (Map<String, AAProduct>) m;
                productMapData.keySet().toArray(keyWordList);
            } catch (ClassCastException e) {
                Logging.logE("Summary list page data parsing fail! Not a String Map.");
                productMapData = new HashMap<String, AAProduct>();
            }
        }
        notifiListUpdate();
    }

    // For Summary Page
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
            content.setText(summaryMapData.get(keyWordList[index]));
        }
    }
}
