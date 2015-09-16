package com.acme.amazon;

import java.util.List;

public class AAFbaProfile extends AAProfile{

    private List<AAFbaItem> mAAFbaItemList;

    public void setFbaItemList(List<AAFbaItem> list) {
        mAAFbaItemList = list;
    }
    public List<AAFbaItem> getFbaItemList() {
        return mAAFbaItemList;
    }
}
