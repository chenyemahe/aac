package com.acme.amazon;

import com.acme.amazon.orderrecord.databaseHelper.AADba;

public class AAManager {

	private static AAManager mManager;
	private AADba mDba;
	
	public static AAManager getManager() {
		if(mManager == null)
			mManager = new AAManager();
		return mManager;
	}
	
	public AAManager(){
		mDba = new AADba();
	}
	
	public AADba getDB() {
		return mDba;
	}
}
