/*
 * Copyright 2015-present Lucas Nelaupe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fr.nelaupe.spreadsheetlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.nelaupe.spreadsheetlib.view.ArrowButton;
import fr.nelaupe.spreadsheetlib.view.DispatcherHorizontalScrollView;

/**
 * Created with IntelliJ
 * Created by Lucas Nelaupe
 * Date 26/03/15
 */
@SuppressWarnings({"unused", "unchecked"})
public class SpreadSheetView extends LinearLayout implements View.OnClickListener {

    private Map<String, Integer> mFixedViewData;
    private String mPreviousID;
    private boolean mInvert;

    private TableLayout mHeader;
    private TableLayout mTable;
    private TableLayout mFixed;
    private TableLayout mFixedHeader;

    private SpreadSheetAdaptor<SpreadSheetData> mAdaptor;

    public SpreadSheetView(Context context) {
        super(context);
        mAdaptor = new SimpleTextAdaptor(getContext());
        init();
    }

    public SpreadSheetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAdaptor = new SimpleTextAdaptor(getContext());
        parseAttribute(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SpreadSheetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAdaptor = new SimpleTextAdaptor(getContext());
        parseAttribute(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpreadSheetView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mAdaptor = new SimpleTextAdaptor(getContext());
        parseAttribute(context, attrs);
        init();
    }

    private void parseAttribute(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sheet);

        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.sheet_headerColor) {
                mAdaptor.getConfiguration().setHeaderBackgroundColor(a.getColor(attr, 0));
            } else if (attr == R.styleable.sheet_headerTextSize) {
                mAdaptor.getConfiguration().setHeaderTextSize(a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.sheet_textSize) {
                mAdaptor.getConfiguration().setTextSize(a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.sheet_textColor) {
                mAdaptor.getConfiguration().setTextColor(a.getColor(attr, 0));
            } else if (attr == R.styleable.sheet_headerTextColor) {
                mAdaptor.getConfiguration().setHeaderTextColor(a.getColor(attr, 0));
            } else if (attr == R.styleable.sheet_rowHeight) {
                mAdaptor.getConfiguration().setRowHeight(a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.sheet_headerRowHeight) {
                mAdaptor.getConfiguration().setHeaderRowHeight(a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.sheet_minFixedRowWidth) {
                mAdaptor.getConfiguration().setMinFixedRowWidth(a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.sheet_textPaddingRight) {
                mAdaptor.getConfiguration().setTextPaddingRight(a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.sheet_textPaddingLeft) {
                mAdaptor.getConfiguration().setTextPaddingLeft(a.getDimensionPixelSize(attr, 0));
            }
        }
        a.recycle();
    }

    private void init() {
        mFixedViewData = new HashMap<>();
        mPreviousID = "";
        mInvert = false;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = inflater.inflate(R.layout.spread_sheet_layout, this, true);

        mHeader = (TableLayout) inflatedView.findViewById(R.id.table_header);
        mTable = (TableLayout) inflatedView.findViewById(R.id.scrollable_part);
        mFixed = (TableLayout) inflatedView.findViewById(R.id.fixed_column);
        mFixedHeader = (TableLayout) inflatedView.findViewById(R.id.fixed_column_header);

        DispatcherHorizontalScrollView scrollViewTab = (DispatcherHorizontalScrollView) inflatedView.findViewById(R.id.scrollViewHorizontal);
        DispatcherHorizontalScrollView scrollViewHeader = (DispatcherHorizontalScrollView) inflatedView.findViewById(R.id.scrollViewHorizontalHeader);
        scrollViewHeader.setHorizontalScrollBarEnabled(false);

        scrollViewTab.setTarget(scrollViewHeader);
        scrollViewHeader.setTarget(scrollViewTab);
        scrollViewTab.setHorizontalScrollBarEnabled(true);

        invalidate();
    }

    /*
     * With auto adaptor
     */
    public List<? extends SpreadSheetData> getData() {
        return mAdaptor.getData();
    }

    /*
     * With auto adaptor
     */
    public void add(SpreadSheetData data) {
        mAdaptor.add(data);
    }

    /*
     * With auto adaptor
     */
    public void addAll(List<SpreadSheetData> data) {
        mAdaptor.addAll(data);
    }

    public void addFixedView(String name, int layout) {
        mFixedViewData.put(name, layout);
    }

    /*
     *  Click
     */
    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.filter) {
            SpreadSheetData cls = mAdaptor.getData().get(0);
            try {
                String filterName = String.valueOf(v.getTag(R.id.filter_name));
                if (!TextUtils.isEmpty(filterName)) {
                    if (cls.hasComparators()) {
                        Comparator<SpreadSheetData> comparator = (Comparator<SpreadSheetData>) cls.getComparatorsClass().getDeclaredField(filterName).get(cls);
                        doSorting(v, comparator);
                    } else {
                        Comparator<SpreadSheetData> comparator = (Comparator<SpreadSheetData>) cls.getClass().getDeclaredField(filterName).get(cls);
                        doSorting(v, comparator);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (i == R.id.item) {
            Integer position = (Integer) v.getTag(R.id.item_number);
            if( mAdaptor.getItemClickListener() != null) {
                mAdaptor.getItemClickListener().onItemClick(mAdaptor.get(position));
            }
        }
    }

    /*
     *  View
     */
    private void addFixedHeader() {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(mAdaptor.getConfiguration().getTableLayoutParams());
        row.setGravity(mAdaptor.getConfiguration().getTextGravity());
        row.setBackgroundColor(mAdaptor.getConfiguration().getHeaderColor());
        for (String name : mFixedViewData.keySet()) {
            Button textView = new Button(getContext());
            textView.setText(name);
            textView.setTextColor(mAdaptor.getConfiguration().getHeaderTextColor());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mAdaptor.getConfiguration().getHeaderTextSize());
            textView.setGravity(mAdaptor.getConfiguration().getTextGravity());
            textView.setWidth(mAdaptor.getConfiguration().getMinFixedRowWidth());
            textView.setHeight(mAdaptor.getConfiguration().getRowHeight());
            textView.setBackgroundResource(0);
            row.addView(textView);
        }

        mFixedHeader.addView(row);
    }

    private void addHeader() {
        SpreadSheetData cls = mAdaptor.getData().get(0);
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(mAdaptor.getConfiguration().getTableLayoutParams());
        row.setGravity(mAdaptor.getConfiguration().getTextGravity());
        row.setBackgroundColor(mAdaptor.getConfiguration().getHeaderColor());

        for (Field field : cls.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(SpreadSheetCell.class)) {
                SpreadSheetCell spreadSheetCell = field.getAnnotation(SpreadSheetCell.class);

                View button = mAdaptor.getHeaderCellView(spreadSheetCell);
                button.setPadding(mAdaptor.getConfiguration().getTextPaddingLeft(), 0, mAdaptor.getConfiguration().getTextPaddingRight(), 0);
                button.setOnClickListener(this);
                button.setId(R.id.filter);
                if (!TextUtils.isEmpty(spreadSheetCell.filterName())) {
                    button.setTag(R.id.filter_name, spreadSheetCell.filterName());
                }

                row.addView(button);
            }
        }
        mHeader.addView(row);
    }

    private void AddFixedRow(boolean colorBool) {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(mAdaptor.getConfiguration().getTableLayoutParams());
        row.setGravity(mAdaptor.getConfiguration().getTextGravity());
        row.setBackgroundColor(getResources().getColor(colorBool ? R.color.white : R.color.grey_cell));
        for (Map.Entry<String, Integer> entry : mFixedViewData.entrySet()) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(entry.getValue(), null);
            view.setMinimumHeight(mAdaptor.getConfiguration().getRowHeight());
            view.setMinimumWidth(mAdaptor.getConfiguration().getMinFixedRowWidth());
            row.addView(view);
        }

        mFixed.addView(row);
    }

    private void addRow() {
        Boolean colorBool = true;
        int nb = 0;
        for (SpreadSheetData resource : mAdaptor.getData()) {

            AddFixedRow(colorBool);

            TableRow row = new TableRow(getContext());
            row.setLayoutParams(mAdaptor.getConfiguration().getTableLayoutParams());
            row.setGravity(mAdaptor.getConfiguration().getTextGravity());
            row.setBackgroundColor(getResources().getColor(colorBool ? R.color.white : R.color.grey_cell));
            row.setId(R.id.item);
            row.setTag(R.id.item_number, nb);
            row.setOnClickListener(this);

            for (Field field : resource.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(SpreadSheetCell.class)) {
                    SpreadSheetCell spreadSheetCell = field.getAnnotation(SpreadSheetCell.class);
                    try {
                        Object object = field.get(resource);
                        View view = mAdaptor.getCellView(spreadSheetCell, object);
                        view.setMinimumWidth(mAdaptor.getConfiguration().computeSize(spreadSheetCell.size()));
                        view.setPadding(mAdaptor.getConfiguration().getTextPaddingLeft(), 0, mAdaptor.getConfiguration().getTextPaddingRight(), 0);
                        row.addView(view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            colorBool = !colorBool;
            mTable.addView(row);
            nb++;
        }
    }

    @Deprecated
    public void clearData() {
        mAdaptor.clearData();
    }

    public void invalidate() {
        if (mAdaptor.getData().isEmpty()) return;

        mFixedHeader.removeAllViews();
        mHeader.removeAllViews();
        mFixed.removeAllViews();
        mTable.removeAllViews();

        addFixedHeader();
        addHeader();
        addRow();
    }

    private void invalidateContent() {
        mTable.removeAllViews();
        mFixed.removeAllViews();

        addRow();
    }

    /*
     *  Sorting
     */
    private void invert(View v) {
        Collections.reverse(mAdaptor.getData());
        if (!mInvert) {
            setArrowDown((ArrowButton) v);
        } else {
            setArrowUP((ArrowButton) v);
        }
        mInvert = !mInvert;
    }

    private void sort(View v, Comparator<SpreadSheetData> comparator) {
        setArrowUP((ArrowButton) v);
        mPreviousID = String.valueOf(v.getTag(R.id.filter_name));
        mInvert = false;
        Collections.sort(mAdaptor.getData(), comparator);
    }

    private void doSorting(View v, Comparator<SpreadSheetData> comparator) {
        if (mPreviousID.equals(v.getTag(R.id.filter_name))) {
            invert(v);
        } else {
            sort(v, comparator);
        }
        mAdaptor.onSort();
        invalidateContent();
    }

    private void resetArrow() {
        TableRow row = (TableRow) (mHeader).getChildAt(0);
        for (int i = 0; i < row.getChildCount(); ++i) {
            ArrowButton childAt = (ArrowButton) row.getChildAt(i);
            childAt.setState(ArrowButton.states.NONE);
        }
    }

    private void setArrowDown(ArrowButton view) {
        resetArrow();
        view.setState(ArrowButton.states.DONW);
    }

    private void setArrowUP(ArrowButton view) {
        resetArrow();
        view.setState(ArrowButton.states.UP);
    }

    public void setAdaptor(SpreadSheetAdaptor adaptor) {
        if (mAdaptor != null && adaptor.getData().size() == 0) {
            adaptor.addAll(mAdaptor.getData());
            adaptor.setConfiguration(mAdaptor.getConfiguration());
        }

        mAdaptor = adaptor;
    }

}
