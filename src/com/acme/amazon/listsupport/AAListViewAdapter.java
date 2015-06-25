package com.acme.amazon.listsupport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.acme.amazon.AAConstant;
import com.acme.amazon.AAItem;
import com.acme.amazon.AAProfile;

public class AAListViewAdapter extends BaseAdapter {

	private Context mContext;
	private int mLayoutID;
	private AAListDataHolder<?> mListDataHodler;
	private int mAdapterStyle;

	public AAListViewAdapter(Context context, int layoutID, int style) {
		mContext = context;
		mLayoutID = layoutID;
		mAdapterStyle = style;
	}

	@Override
	public int getCount() {
		if (mListDataHodler == null)
			return 0;
		return mListDataHodler.getSize();
	}

	@Override
	public Object getItem(int index) {
		if (mListDataHodler == null)
			return null;
		return mListDataHodler.getListData(index);
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
		AAListViewHodler holder = new AAListViewHodler();
		if (mAdapterStyle == AAConstant.ADAPTER_ORIDER_LIST) {
			holder.setOrderListView(contentView);
			holder.setData((AAProfile) getItem(index));
		} else if (mAdapterStyle == AAConstant.ADAPTER_ITEM_LIST) {
			holder.setItemListView(contentView);
			holder.setData((AAItem) getItem(index));
		}
		contentView.setTag(holder);
		return contentView;
	}

	public void notifiListUpdate() {
		notifyDataSetChanged();
	}

	public void setDataHolder(AAListDataHolder<?> holder) {
		mListDataHodler = holder;
		notifiListUpdate();
	}
}
