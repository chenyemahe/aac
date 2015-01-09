package com.acme.amazon;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class AAProfile {

	private String mDate;
	private int mItemID;
	private String mOrderTitle;
	private String mOrderCost;
	private static final String TAG = "AAProfile";

	private List<AAItem> mAAItemList;

	public AAProfile() {
		mDate = null;
		mOrderTitle = null;
		mOrderCost = null;
		mAAItemList = null;
	}

	public void setDate(String date) {
		mDate = date;
	}

	public void setItemID(int id) {
		mItemID = id;
	}

	public void setItemID(String aid) {
		try {
			int id = Integer.parseInt(aid);
			setItemID(id);
		} catch (NumberFormatException nfe) {
			Log.e(TAG, "Exception: " + nfe.toString());
		}
	}

	public void setTitle(String title) {
		mOrderTitle = title;
	}

	public void setCost(String cost) {
		mOrderCost = cost;
	}

	public void setItemList(ArrayList<AAItem> list) {
		mAAItemList = list;
	}

	public String getDate() {
		return mDate;
	}

	public int getItemID() {
		return mItemID;
	}

	public String getTitle() {
		return mOrderTitle;
	}

	public String getCost() {
		return mOrderCost;
	}

	public List<AAItem> getItemList() {
		return mAAItemList;
	}
}
