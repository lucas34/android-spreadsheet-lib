/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<String> mFixedViewData;
    private List<AnnotationFields> mFields;
    private List<Integer> mDisplayOnly;
    private BinderField<TSelf> mBindableClass;

    private OnItemClickListener<TSelf> mItemClickListener;
    private OnSortingListener mSortingListener;

    public SpreadSheetAdaptor(Context context) {
        mConfiguration = new Configuration(context);
        mData = new ArrayList<>();
        mFixedViewData = new HashSet<>();
        mFields = new ArrayList<>();
        mDisplayOnly = new ArrayList<>();
    }

    public void displayColumn(ArrayList<Integer> columnNumber) {
        mDisplayOnly.clear();
        mDisplayOnly.addAll(columnNumber);
    }

    public void displayColumn(Integer... columnNumber) {
        mDisplayOnly.clear();
        mDisplayOnly.addAll(Arrays.asList(columnNumber));
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

    public Set<String> getFixedViews() {
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

    public abstract View getCellView(AnnotationFields cell, Object object);

    public abstract ArrowButton getHeaderCellView(AnnotationFields cell);

    public abstract View getFixedHeaderView(String name);

    public abstract View getFixedCellView(String name, int position);

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void inspectFields() {

        if (getData().isEmpty()) {
            return;
        }

        mFields.clear();
        try {
            mBindableClass = (BinderField<TSelf>) Class.forName("fr.nelaupe.spreedsheet."+mData.get(0).getClass().getSimpleName()+"Binding").newInstance();
            mFields = mBindableClass.fields;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Missing binding class");
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Collections.sort(mFields, new Comparator<AnnotationFields>() {
            @Override
            public int compare(AnnotationFields lhs, AnnotationFields rhs) {
                Integer positionL = lhs.getPosition();
                Integer positionR = rhs.getPosition();

                return positionL.compareTo(positionR);
            }
        });
    }

    public List<AnnotationFields> getFields() {
        if (mFields.isEmpty()) {
            inspectFields();
        }

        if (mDisplayOnly.isEmpty()) {
            return mFields;
        } else {
            List<AnnotationFields> returned = new ArrayList<>();
            for (AnnotationFields field : mFields) {
                if (mDisplayOnly.contains(field.getPosition())) {
                    returned.add(field);
                }
            }

            return returned;
        }

    }

    public BinderField<TSelf> getBinder() {
        return mBindableClass;
    }

//
//    public Comparator<TSelf> sortBy(final AnnotationFields field) {
//
//        return new Comparator<TSelf>() {
//            @Override
//            public int compare(TSelf lhs, TSelf rhs) {
//
//                Comparable lComparable = (Comparable) lhs.getValueAt(field.getFieldName());
//                Comparable rComparable = (Comparable) rhs.getValueAt(field.getFieldName());
//
//                return lComparable.compareTo(rComparable);
//
//            }
//        };
//    }


}
