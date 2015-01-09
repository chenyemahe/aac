package com.acme.amazon;

import com.acme.amazon.orderrecord.databaseHelper.AAProvider.ItemColumns;
import com.acme.amazon.orderrecord.databaseHelper.AAProvider.MatchColumns;
import com.acme.amazon.orderrecord.databaseHelper.AAProvider.ProfileColumns;

import android.content.ContentValues;

public class AAUtils {

	public static void toContentValues(AAProfile profile, ContentValues values) {
		values.put(ProfileColumns.ORDER_DATE, profile.getDate());
		values.put(ProfileColumns.ORDER_ID, profile.getItemID());
		values.put(ProfileColumns.ORDER_TITLE, profile.getTitle());
		values.put(ProfileColumns.ORDER_TOTAL_COST, profile.getCost());
	}

	public static void toContentValues(AAMatch match, ContentValues values) {
		values.put(MatchColumns.PROFILE_ID, match.getProfileID());
		values.put(MatchColumns.ITEM_ID, match.getItemID());
	}

	public static void toContentValues(AAItem item, ContentValues values) {
		values.put(ItemColumns.ITEM_NAME, item.getName());
		values.put(ItemColumns.ITEM_QUALITY, item.getQuality());
		values.put(ItemColumns.ITEM_TOTAL_COST, item.getCost());
	}
}
