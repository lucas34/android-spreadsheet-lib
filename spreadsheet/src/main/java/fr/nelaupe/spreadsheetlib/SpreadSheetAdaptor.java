/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.nelaupe.spreadsheetlib.view.ArrowButton;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class SpreadSheetAdaptor<TSelf extends SpreadSheetData> {

    private List<TSelf> mData;
    private Configuration mConfiguration;
    private List<String> mFixedViewData;

    private OnItemClickListener<TSelf> mItemClickListener;
    private OnSortingListener mSortingListener;

    public SpreadSheetAdaptor(Context context) {
        mConfiguration = new Configuration(context);
        mData = new ArrayList<>();
        mFixedViewData = new ArrayList<>();
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

    public void setOnSortingListener(OnSortingListener mSortingListener) {
        this.mSortingListener = mSortingListener;
    }

    public void onSort() {
        if (mSortingListener != null) {
            mSortingListener.onSort();
        }
    }

    public void setOnItemClickListener(OnItemClickListener<TSelf> listener) {
        mItemClickListener = listener;
    }

    public OnItemClickListener<TSelf> getItemClickListener() {
        return mItemClickListener;
    }

    public List<String> getFixedViews() {
        return mFixedViewData;
    }

    public void addFixed(String name) {
        mFixedViewData.add(name);
    }

    public Configuration getConfiguration() {
        return mConfiguration;
    }

    public void setConfiguration(Configuration configuration) {
        mConfiguration = configuration;
    }

    public Context getContext() {
        return mConfiguration.getContext();
    }

    public abstract View getCellView(SpreadSheetCell cell, Object object);

    public abstract ArrowButton getHeaderCellView(SpreadSheetCell cell);

    public abstract View getFixedHeaderView(String name);

    public abstract View getFixedCellView(String name, int position);

}
