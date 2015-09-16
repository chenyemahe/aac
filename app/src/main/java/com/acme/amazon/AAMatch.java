package com.acme.amazon;

import android.util.Log;

public class AAMatch {

	//Using Profile date as Profile ID
	private String mProfileID;
	private int mItemID;
	private static final String TAG = "AAMatch";

	public void setProfileID(String id) {
		mProfileID = id;
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

	public String getProfileID() {
		return mProfileID;
	}

	public int getItemID() {
		return mItemID;
	}
}
