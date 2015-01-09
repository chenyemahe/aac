package com.acme.amazon;

public class AAItem {

	private String mItemName;
	private int mItemQuality;
	private String mCost;

	public void setName(String name) {
		mItemName = name;
	}

	public void setQuality(int quality) {
		mItemQuality = quality;
	}

	public void setCost(String cost) {
		mCost = cost;
	}

	public String getName() {
		return mItemName;
	}

	public int getQuality() {
		return mItemQuality;
	}

	public String getCost() {
		return mCost;
	}
}
