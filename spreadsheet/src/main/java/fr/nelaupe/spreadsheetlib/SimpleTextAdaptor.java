/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import fr.nelaupe.spreadsheetlib.view.ArrowButton;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public class SimpleTextAdaptor<TSelf> extends SpreadSheetAdaptor<TSelf> {
    
    private Context mContext;
    
    public SimpleTextAdaptor(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public View getCellView(AnnotationFields cell, Object object) {
        TextView recyclableTextView = new TextView(mContext);
        recyclableTextView.setText((object == null ? "" : object.toString()));
        recyclableTextView.setTextColor(getConfiguration().getTextColor(mContext.getResources()));
        recyclableTextView.setGravity(getConfiguration().getTextGravity());
        recyclableTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getConfiguration().getTextSize(mContext.getResources()));
        recyclableTextView.setWidth(getConfiguration().computeSize(cell.getColumnSize()));
        recyclableTextView.setHeight(getConfiguration().getRowHeight(mContext.getResources()));
        return recyclableTextView;
    }

    @Override
    public ArrowButton getHeaderCellView(AnnotationFields cell) {
        ArrowButton button = new ArrowButton(mContext);
        button.setWidth(getConfiguration().computeSize(cell.getColumnSize()));
        button.setHeight(getConfiguration().getHeaderRowHeight(mContext.getResources()));
        button.setTextColor(getConfiguration().getHeaderTextColor(mContext.getResources()));
        button.setBackgroundResource(0);
        button.setText(cell.getName());
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, getConfiguration().getHeaderTextSize(mContext.getResources()));
        button.setTextGravity(getConfiguration().getTextGravity());
        if (((getConfiguration().getTextGravity() & Gravity.LEFT) == Gravity.LEFT) || ((getConfiguration().getTextGravity() & Gravity.START) == Gravity.START)) {
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icr_arrow_selector_sort, 0);
        } else if (((getConfiguration().getTextGravity() & Gravity.RIGHT) == Gravity.RIGHT) || ((getConfiguration().getTextGravity() & Gravity.END) == Gravity.END)) {
            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icr_arrow_selector_sort, 0, 0, 0);
        } else {
            button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_empty, 0, R.drawable.icr_arrow_selector_sort, 0);
        }

        return button;
    }

    @Override
    public View getFixedHeaderView(String name) {
        Button button = new Button(mContext);
        button.setText(name);
        button.setTextColor(getConfiguration().getHeaderTextColor(mContext.getResources()));
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, getConfiguration().getHeaderTextSize(mContext.getResources()));
        button.setGravity(getConfiguration().getTextGravity());
        button.setWidth(getConfiguration().getMinFixedRowWidth(mContext.getResources()));
        button.setHeight(getConfiguration().getRowHeight(mContext.getResources()));
        button.setBackgroundResource(0);
        return button;
    }

    @Override
    public View getFixedCellView(String name, int position) {
        CheckBox checkBox = new CheckBox(mContext);
        checkBox.setWidth(getConfiguration().getMinFixedRowWidth(mContext.getResources()));
        checkBox.setHeight(getConfiguration().getRowHeight(mContext.getResources()));
        return checkBox;
    }

}