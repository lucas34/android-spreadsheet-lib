/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;

import java.lang.reflect.ParameterizedType;
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
public abstract class SpreadSheetAdaptor<TSelf> {

    private final Set<String> mFixedViewData;
    private final List<Integer> mDisplayOnly;
    private List<TSelf> mData;
    private Configuration mConfiguration;
    private FieldBinder<TSelf> mBindableClass;
    private Class<TSelf> mCurrentClass;
    private boolean mAutoSorting;

    private OnItemClickListener<TSelf> mItemClickListener;
    private OnSortingListener mSortingListener;

    public SpreadSheetAdaptor(Context context) {
        mConfiguration = new Configuration(context);
        mData = new ArrayList<>();
        mFixedViewData = new HashSet<>();
        mDisplayOnly = new ArrayList<>();
        mAutoSorting = true;
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

    boolean onSort(AnnotationFields annotationFields, int columnPosition) {
        if (mAutoSorting) {
            if (annotationFields.isComparable()) {
                doSorting(columnPosition, sortBy(annotationFields));
                return true;
            }
        } else if (mSortingListener != null) {
            getBinder().setIsDESC(getBinder().getSortingColumnSelected() != columnPosition || !getBinder().isSortingDESC());
            mSortingListener.onSort(annotationFields, getBinder().isSortingDESC());
            return true;
        }
        return false;
    }

    private void invert() {
        Collections.reverse(getData());
    }

    private <T> void doSorting(int columnId, Comparator<T> comparator) {
        if (getBinder().getSortingColumnSelected() == columnId) {
            invert();
            getBinder().invertSorting();
        } else {
            Collections.sort(getData(), (Comparator<? super TSelf>) comparator);
            getBinder().setIsDESC(false);
            getBinder().setColumnSortSelected(columnId);
        }
    }

    public void setArrow(int column, boolean isDESC) {
        getBinder().setIsDESC(isDESC);
        getBinder().setColumnSortSelected(column);
    }

    public void setOnItemClickListener(OnItemClickListener<TSelf> listener) {
        mItemClickListener = listener;
    }

    @Deprecated
    public OnItemClickListener<TSelf> getItemClickListener() {
        return mItemClickListener;
    }

    public OnItemClickListener<TSelf> getOnItemClickListener() {
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

        if (mCurrentClass == null) {
            if (mData.size() > 0) {
                mCurrentClass = (Class<TSelf>) mData.get(0).getClass();
            } else {
                return;
            }
        }

        try {
            Class<TSelf> persistentClass = (Class<TSelf>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            mBindableClass = (FieldBinder<TSelf>) Class.forName("fr.nelaupe.spreedsheet." + persistentClass.getSimpleName() + "Binding").newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Missing binding class");
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public List<AnnotationFields> getFields() {
        if (mBindableClass == null || mBindableClass.fields().size() == 0) {
            inspectFields();
        }

        if (mBindableClass == null) {
            return Collections.emptyList();
        }

        if (mDisplayOnly.isEmpty()) {
            return Collections.unmodifiableList(mBindableClass.fields());
        } else {
            List<AnnotationFields> returned = new ArrayList<>();
            for (AnnotationFields field : mBindableClass.fields()) {
                if (mDisplayOnly.contains(field.getPosition())) {
                    returned.add(field);
                }
            }

            return Collections.unmodifiableList(returned);
        }

    }

    FieldBinder<TSelf> getBinder() {
        return mBindableClass;
    }

    public final Comparator<TSelf> sortBy(final AnnotationFields field) {

        return new Comparator<TSelf>() {
            @Override
            public int compare(TSelf lhs, TSelf rhs) {

                Comparable lComparable = (Comparable) mBindableClass.getValueAt(field.getFieldName(), lhs);
                Comparable rComparable = (Comparable) mBindableClass.getValueAt(field.getFieldName(), rhs);

                return lComparable.compareTo(rComparable);

            }
        };
    }

    public SpreadSheetAdaptor<TSelf> with(Class<TSelf> cls) {
        mCurrentClass = cls;
        inspectFields();
        return this;
    }

    public void setAutoSorting(boolean isAutoSort) {
        mAutoSorting = isAutoSort;
    }

}
