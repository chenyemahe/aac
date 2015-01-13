package com.acme.amazon.orderrecord;

import java.util.List;

public class AAListDataHolder<T> {

	private List<T> mListData;
	
	public AAListDataHolder (List<T> list) {
		mListData = list;
	}
	
	public void addListData(T t) {
		mListData.add(t);
	}
	public T getListData(int index) {
		return mListData.get(index);
	}
	
	public int getSize() {
		return mListData.size();
	}
}
