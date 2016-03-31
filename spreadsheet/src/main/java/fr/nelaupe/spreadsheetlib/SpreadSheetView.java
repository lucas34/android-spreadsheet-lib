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
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import fr.nelaupe.spreadsheetlib.view.ArrowButton;
import fr.nelaupe.spreadsheetlib.view.DispatcherHorizontalScrollView;

/**
 * Created with IntelliJ
 * Created by Lucas Nelaupe
 * Date 26/03/15
 */
@SuppressWarnings("unused")
public class SpreadSheetView extends LinearLayout {

    private TableLayout mHeader;
    private TableLayout mTable;
    private TableLayout mFixed;
    private TableLayout mFixedHeader;

    private SpreadSheetAdaptor<?> mAdaptor;
    private View.OnClickListener clickListener = new OnClickIntervalHandler();

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
    }

    /*
     *  View
     */
    private void addFixedHeader() {
        if (mAdaptor.getFixedViews().size() == 0) return;
        
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(mAdaptor.getConfiguration().getTableLayoutParams());
        row.setGravity(mAdaptor.getConfiguration().getTextGravity());
        row.setBackgroundColor(mAdaptor.getConfiguration().getHeaderColor(getResources()));
        for (String name : mAdaptor.getFixedViews()) {
            View view = mAdaptor.getFixedHeaderView(name);
            view.setMinimumWidth(mAdaptor.getConfiguration().getMinFixedRowWidth(getResources()));
            view.setMinimumHeight(mAdaptor.getConfiguration().getHeaderRowHeight(getResources()));
            view.setPadding(mAdaptor.getConfiguration().getTextPaddingLeft(), 0, mAdaptor.getConfiguration().getTextPaddingRight(), 0);
            row.addView(view);
        }

        mFixedHeader.addView(row);
    }

    private void addHeader() {
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(mAdaptor.getConfiguration().getTableLayoutParams());
        row.setGravity(mAdaptor.getConfiguration().getTextGravity());
        row.setBackgroundColor(mAdaptor.getConfiguration().getHeaderColor(getResources()));

        int column = 0;

        for (AnnotationFields field : mAdaptor.getFields()) {
            ArrowButton button = mAdaptor.getHeaderCellView(field);
            button.setPadding(mAdaptor.getConfiguration().getTextPaddingLeft(), 0, mAdaptor.getConfiguration().getTextPaddingRight(), 0);
            button.setOnClickListener(clickListener);
            button.setId(R.id.filter);
            button.setMinimumWidth(mAdaptor.getConfiguration().computeSize(field.getColumnSize()));
            button.setMinimumHeight(mAdaptor.getConfiguration().getHeaderRowHeight(getResources()));
            button.setPadding(mAdaptor.getConfiguration().getTextPaddingLeft(), 0, mAdaptor.getConfiguration().getTextPaddingRight(), 0);
            button.setTag(R.id.filter_column_position, column);
            column++;

            row.addView(button);
        }

        mHeader.addView(row);
    }

    private void AddFixedRow(boolean colorBool, int position) {
        if (mAdaptor.getFixedViews().size() == 0) return;

        TableRow row = new TableRow(getContext());
        row.setLayoutParams(mAdaptor.getConfiguration().getTableLayoutParams());
        row.setGravity(mAdaptor.getConfiguration().getTextGravity());
        row.setBackgroundColor(getResources().getColor(colorBool ? R.color.white : R.color.grey_cell));

        for (String name : mAdaptor.getFixedViews()) {
            View view = mAdaptor.getFixedCellView(name, position);
            view.setMinimumWidth(mAdaptor.getConfiguration().getMinFixedRowWidth(getResources()));
            view.setMinimumHeight(mAdaptor.getConfiguration().getRowHeight(getResources()));
            view.setPadding(mAdaptor.getConfiguration().getTextPaddingLeft(), 0, mAdaptor.getConfiguration().getTextPaddingRight(), 0);
            row.addView(view);
        }

        mFixed.addView(row);
    }

    private void addRow() {
        Boolean colorBool = true;
        int position = 0;

        for (Object resource : mAdaptor.getData()) {

            AddFixedRow(colorBool, position);

            TableRow row = new TableRow(getContext());
            row.setLayoutParams(mAdaptor.getConfiguration().getTableLayoutParams());
            row.setGravity(mAdaptor.getConfiguration().getTextGravity());
            row.setBackgroundColor(getResources().getColor(colorBool ? R.color.white : R.color.grey_cell));
            row.setId(R.id.item);
            row.setTag(R.id.item_number, position);
            row.setOnClickListener(clickListener);

            for (AnnotationFields field : mAdaptor.getFields()) {
                Object object = binder().getValueAt(field.getFieldName(), resource);
                View view = mAdaptor.getCellView(field, object);
                view.setMinimumWidth(mAdaptor.getConfiguration().computeSize(field.getColumnSize()));
                view.setMinimumHeight(mAdaptor.getConfiguration().getRowHeight(getResources()));
                view.setPadding(mAdaptor.getConfiguration().getTextPaddingLeft(), 0, mAdaptor.getConfiguration().getTextPaddingRight(), 0);
                row.addView(view);
            }

            colorBool = !colorBool;
            mTable.addView(row);
            position++;
        }
    }

    private <T> FieldBinder<T> binder() {
        //noinspection unchecked
        return (FieldBinder<T>) mAdaptor.getBinder();
    }

    private <T> SpreadSheetAdaptor<T> adaptor() {
        //noinspection unchecked
        return (SpreadSheetAdaptor<T>) mAdaptor;
    }

    @Override
    public void invalidate() {
        super.invalidate();

        if (mAdaptor.getFields().isEmpty()) return;

        mFixedHeader.removeAllViews();
        mHeader.removeAllViews();
        mFixed.removeAllViews();
        mTable.removeAllViews();

        addFixedHeader();

        addHeader();
        addRow();

        showArrow();
    }

    private void invalidateContent() {
        mTable.removeAllViews();
        mFixed.removeAllViews();

        addRow();
    }

    private void showArrow() {
        boolean mIsDESC = binder().isSortingDESC();
        int mColumnSortSelected = binder().getSortingColumnSelected();

        TableRow row = (TableRow) (mHeader).getChildAt(0);
        for (int i = 0; i < row.getChildCount(); ++i) {
            ArrowButton childAt = (ArrowButton) row.getChildAt(i);
            if (mColumnSortSelected == (int) childAt.getTag(R.id.filter_column_position)) {
                if (mIsDESC) {
                    childAt.setState(ArrowButton.states.UP);
                } else {
                    childAt.setState(ArrowButton.states.DOWN);
                }
            } else {
                childAt.setState(ArrowButton.states.NONE);
            }
        }
    }

    public SpreadSheetAdaptor getAdaptor() {
        return mAdaptor;
    }

    public <T> void setAdaptor(SpreadSheetAdaptor<T> adaptor) {
        adaptor.getConfiguration().init(getResources());
        mAdaptor = adaptor;
        invalidate();
    }

    private final class OnClickIntervalHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int i = v.getId();

            if (i == R.id.filter) {
                int columnPosition = (Integer) v.getTag(R.id.filter_column_position);
                AnnotationFields annotationFields = mAdaptor.getFields().get(columnPosition);
                if (adaptor().onSort(annotationFields, columnPosition)) {
                    invalidate();
                }
            } else if (i == R.id.item) {
                Integer position = (Integer) v.getTag(R.id.item_number);
                if (mAdaptor.getOnItemClickListener() != null) {
                    adaptor().getOnItemClickListener().onItemClick(mAdaptor.get(position));
                }
            }
        }
    }

}
