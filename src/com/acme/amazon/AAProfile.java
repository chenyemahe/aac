package com.acme.amazon;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class AAProfile{

    private String mProfileId;
    private String mDate;
    private String mIDList;
    private int mTempId;
    private String mOrderTitle;
    private String mOrderCost;
    private String mExtra1;
    private static final String TAG = "AAProfile";

    private List<AAItem> mAAItemList;

    /*public static final Creator<AAProfile> CREATOR = new Creator<AAProfile>() {

        @Override
        public AAProfile createFromParcel(Parcel source) {
            AAProfile profile = new AAProfile();
            profile.setProfileId(source.readInt());
            profile.setDate(source.readString());
            profile.setID(source.readString());
            profile.setItemID(source.readInt());
            profile.setTitle(source.readString());
            profile.setCost(source.readString());
            source.readList(mAAItemList, null);
            return null;
        }

        @Override
        public AAProfile[] newArray(int size) {
            return new AAProfile[size];
        }
    };*/
    
    public AAProfile() {
        mDate = null;
        mOrderTitle = null;
        mOrderCost = null;
        mAAItemList = null;
    }

    public void setProfileId(String id) {
        mProfileId = id;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setID(String id) {
        mIDList = id;
    }

    public void setTempId(int id) {
        mTempId = id;
    }

    public void setItemID(String aid) {
        try {
            int id = Integer.parseInt(aid);
            setTempId(id);
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

    public void setExtra1(String cost) {
        mExtra1 = cost;
    }

    public void setItemList(ArrayList<AAItem> list) {
        mAAItemList = list;
    }

    public String getProfileId() {
        return mProfileId;
    }

    public String getDate() {
        return mDate;
    }

    public String getID() {
        return mIDList;
    }

    public int mTempId() {
        return mTempId;
    }

    public String getTitle() {
        return mOrderTitle;
    }

    public String getCost() {
        return mOrderCost;
    }
    
    public String getExtra1() {
        return mExtra1;
    }

    public List<AAItem> getItemList() {
        return mAAItemList;
    }

    /*
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mProfileId);
        dest.writeString(mDate);
        dest.writeString(mID);
        dest.writeInt(mItemID);
        dest.writeString(mOrderTitle);
        dest.writeString(mOrderCost);
        dest.writeList(mAAItemList);
    }
    */
}
