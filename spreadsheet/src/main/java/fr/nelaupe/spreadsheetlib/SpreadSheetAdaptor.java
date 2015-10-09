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
public abstract class SpreadSheetAdaptor<TSelf extends SpreadSheetData> {

    private List<TSelf> mData;

    private OnItemClickListener<TSelf> mItemClickListener;
    private OnSortingListener mSortingListener;

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

    public void onRowClick(int position) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(get(position));
        }
    }

    public void setOnItemClickListener(OnItemClickListener<TSelf> listener) {
        mItemClickListener = listener;
    }

    public void setOnSortingListener(OnSortingListener mSortingListener) {
        this.mSortingListener = mSortingListener;
    }

    public void onSort() {
        if (mSortingListener != null) {
            mSortingListener.onSort();
        }
    }

}
