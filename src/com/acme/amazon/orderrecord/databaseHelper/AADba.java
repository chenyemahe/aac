package com.acme.amazon.orderrecord.databaseHelper;

import java.util.List;

import com.acme.amazon.AAItem;
import com.acme.amazon.AAMatch;
import com.acme.amazon.AAProfile;
import com.acme.amazon.AAUtils;
import com.acme.amazon.orderrecord.databaseHelper.AAProvider.ItemColumns;
import com.acme.amazon.orderrecord.databaseHelper.AAProvider.MatchColumns;
import com.acme.amazon.orderrecord.databaseHelper.AAProvider.ProfileColumns;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

public class AADba {

	private static final String TAG = "AADba";

	/*
     * 
     */
	public Uri saveAAProfile(ContentResolver cr, AAProfile profile) {
		if (profile == null) {
			return null;
		}

		List<AAItem> itemList = profile.getItemList();
		if (itemList != null) {
			saveAAItemList(cr, itemList, profile);
		}

		ContentValues values = new ContentValues();
		AAUtils.toContentValues(profile, values);

		Log.d(TAG, "insert order " + profile.getDate());
		return cr.insert(ProfileColumns.CONTENT_URI, values);
	}

	private void saveAAItemList(ContentResolver cr, List<AAItem> itemList,
			AAProfile profile) {
		if (itemList == null) {
			return;
		}
		for (int i = 0; i < itemList.size(); i++) {
			AAItem item = itemList.get(i);
			AAMatch match = new AAMatch();
			match.setProfileID(profile.getDate());
			Uri uri = saveAAItem(cr, item);
			if (uri != null) {
				match.setItemID(uri.getPathSegments().get(1));
			}
			ContentValues values = new ContentValues();
			AAUtils.toContentValues(match, values);
			Log.d(TAG, "insert match " + profile.getDate());
			cr.insert(MatchColumns.CONTENT_URI, values);
		}
	}

	public Uri saveAAItem(ContentResolver cr, AAItem item) {
		if (item == null) {
			return null;
		}
		ContentValues values = new ContentValues();
		AAUtils.toContentValues(item, values);
		Log.d(TAG, "insert item " + item.getName());
		return cr.insert(ItemColumns.CONTENT_URI, values);
	}
}
