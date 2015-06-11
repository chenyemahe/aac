package com.acme.amazon;

public class AAItem{

    private String mItemId;
    private String mItemName;
    private int mItemQuality;
    private String mCost;
    private String mDate;
    private String mCurrencyType;
    private String mExtra1;

    /*public static final Creator<AAItem> CREATOR = new Creator<AAItem>() {

        @Override
        public AAItem createFromParcel(Parcel source) {
            AAItem item = new AAItem();
            item.setItemId(source.readString());
            item.setName(source.readString());
            item.setQuality(source.readInt());
            item.setCost(source.readString());
            item.setDate(source.readString());
            return null;
        }

        @Override
        public AAItem[] newArray(int size) {
            return new AAItem[size];
        }
    };*/
    
    public void setName(String name) {
        mDate = null;
        mItemName = name;
    }

    public void setItemId(String id) {
        mItemId = id;
    }

    public void setQuality(int quality) {
        mItemQuality = quality;
    }

    public void setCost(String cost) {
        mCost = cost;
    }

    public void setDate(String date) {
        mDate = date;
    }
    
    public void setCurrencyType(String type) {
        mCurrencyType = type;
    }

    public void setExtra1(String cost) {
        mExtra1 = cost;
    }

    public String getItemId() {
        return mItemId;
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

    public String getDate() {
        return mDate;
    }
    
    public String getExtra1() {
        return mExtra1;
    }

    public String getCurrencyType() {
        return mCurrencyType;
    }
    /*
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mItemId);
        dest.writeString(mItemName);
        dest.writeInt(mItemQuality);
        dest.writeString(mCost);
        dest.writeString(mDate);
    }*/

}
