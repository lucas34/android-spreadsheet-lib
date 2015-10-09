/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public abstract class SpreadSheetAdaptor<TSelf> {

    private List<TSelf> mData;

    public SpreadSheetAdaptor() {
        mData = new ArrayList<>();
    }

    public void add(TSelf data) {
        mData.add(data);
    }

    public void addAll(List<TSelf> data) {
        mData.addAll(data);
    }

    public List<TSelf> getData() {
        return mData;
    }

    public TSelf get(int position) {
        return mData.get(position);
    }

    public void clearData() {
        mData = new ArrayList<>();
    }

    public abstract View getView(SpreadSheetCell cell, Object object);

}
