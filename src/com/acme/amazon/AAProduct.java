package com.acme.amazon;

public class AAProduct {

	private String mID;
	private String mProductName;
	private String mMaFullPrice;
	private String mBVpoint;
	private String mBVtoDollar;
	private String mFBAShipping;
	private String mFbaPreFee;
	private String mAmazonRefFee;

	private String mShop_comPrice;
	private String mAmazonBasePrice;
	private String mAmazonPricewithBV;
	private String mSalePriceOnAm;
	private String mProfit;

	public void setID(String s) {
		mID = s;
	}

	public void setProductName(String s) {
		mProductName = s;
	}

	public void setShop_comPrice(String s) {
		mShop_comPrice = s;
	}

	public void setMaFullPrice(String s) {
		mMaFullPrice = s;
	}

	public void setBVpoint(String s) {
		mBVpoint = s;
	}

	public void setBVtoDollar(String s) {
		mBVtoDollar = s;
	}

	public void setFBAShipping(String s) {
		mFBAShipping = s;
	}

	public void setAmazonBasePrice(String s) {
		mAmazonBasePrice = s;
	}

	public void setAmazonPricewithBV(String s) {
		mAmazonPricewithBV = s;
	}

	public void setSalePriceOnAm(String s) {
		mSalePriceOnAm = s;
	}

	public void setProfit(String s) {
		mProfit = s;
	}

	public void setFbaPreFee(String s) {
		mFbaPreFee = s;
	}

	public void setAmazonRefFee(String s) {
		mAmazonRefFee = s;
	}

	public String getID() {
		return mID;
	}

	public String getProductName() {
		return mProductName;
	}

	public String getShop_comPrice() {
		return mShop_comPrice;
	}

	public String getMaFullPrice() {
		return mMaFullPrice;
	}

	public String getBVpoint() {
		return mBVpoint;
	}

	public String getBVtoDollar() {
		return mBVtoDollar;
	}

	public String getFBAShipping() {
		return mFBAShipping;
	}

	public String getAmazonBasePrice() {
		return mAmazonBasePrice;
	}

	public String getAmazonPricewithBV() {
		return mAmazonPricewithBV;
	}

	public String getSalePriceOnAm() {
		return mSalePriceOnAm;
	}

	public String getProfit() {
		return mProfit;
	}

	public String getFbaPreFee() {
		return mFbaPreFee;
	}

	public String getAmazonRefFee() {
		return mAmazonRefFee;
	}
}
