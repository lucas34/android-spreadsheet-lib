/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib;

import android.content.Context;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private ArrayList<AnnotationFields> mFields;

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

    public void onSort(AnnotationFields annotationFields, boolean isDESC) {
        if (mSortingListener != null) {
            mSortingListener.onSort(annotationFields, isDESC);
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

    public void inspectFields() {
        mFields  = new ArrayList<>();

        if(getData().isEmpty()) { return; }

        for (Field field : getData().get(0).getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(SpreadSheetCell.class)) {
                mFields.add(new AnnotationFields(field, field.getAnnotation(SpreadSheetCell.class)));
            }
        }

        Collections.sort(mFields, new Comparator<AnnotationFields>() {
            @Override
            public int compare(AnnotationFields lhs, AnnotationFields rhs) {
                Integer positionL = lhs.getAnnotation().position();
                Integer positionR = rhs.getAnnotation().position();

                return positionL.compareTo(positionR);
            }
        });
    }

    public List<AnnotationFields> getFields() {
        return mFields;
    }

    public Comparator<TSelf> sortBy(final Field field) {

        return new Comparator<TSelf>() {
            @Override
            public int compare(TSelf lhs, TSelf rhs) {

                try {
                    Comparable lComparable = (Comparable) field.get(lhs);
                    Comparable rComparable = (Comparable) field.get(rhs);

                    return lComparable.compareTo(rComparable);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        };
    }


}
