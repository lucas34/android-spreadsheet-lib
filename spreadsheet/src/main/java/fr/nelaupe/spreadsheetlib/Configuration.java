/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableRow;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
@SuppressWarnings("unused")
public class Configuration {

    private static final int mDefaultTextGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
    private final float screenDensity;

    private final TableRow.LayoutParams wrapWrapTableRowParams;

    private float mRowHeight;
    private float mHeaderTextSize;
    private float mTextSize;
    private float mMinFixedRowWidth;
    private float mHeaderRowHeight;
    private int mHeaderBackgroundColor;
    private int mHeaderTextColor;
    private int mTextColor;
    private int mTextGravity;
    private int mTextPaddingLeft;
    private int mTextPaddingRight;

    private final Context mContext;

    public Configuration(Context context) {
        mContext = context;
        screenDensity = context.getResources().getDisplayMetrics().density;
        wrapWrapTableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, mDefaultTextGravity);
        mTextGravity = mDefaultTextGravity;
    }

    /*
     *  Colors parameters
     */
    public int getHeaderColor() {
        return (mHeaderBackgroundColor == 0) ? mContext.getResources().getColor(R.color.header_color) : mHeaderBackgroundColor;
    }

    public void setHeaderBackgroundColor(int color) {
        mHeaderBackgroundColor = color;
    }

    public float getTextSize() {
        return (mTextSize == 0) ? mContext.getResources().getDimension(R.dimen.text) : mTextSize;
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    public float getHeaderTextSize() {
        return (mHeaderTextSize == 0) ? mContext.getResources().getDimension(R.dimen.text) : mHeaderTextSize;
    }

    public void setHeaderTextSize(int headerTextSize) {
        this.mHeaderTextSize = headerTextSize;
    }

    public int getTextColor() {
        return (mTextColor == 0) ? mContext.getResources().getColor(R.color.text) : mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getHeaderTextColor() {
        return (mHeaderTextColor == 0) ? mContext.getResources().getColor(R.color.white) : mHeaderTextColor;
    }

    public void setHeaderTextColor(int headerTextColor) {
        this.mHeaderTextColor = headerTextColor;
    }

    public int getRowHeight() {
        return (int) ((mRowHeight == 0) ? mContext.getResources().getDimension(R.dimen.rowHeight) : mRowHeight);
    }

    public void setRowHeight(float rowHeight) {
        mRowHeight = computeSize(rowHeight);
    }

    public int getHeaderRowHeight() {
        return (int) ((mHeaderRowHeight == 0) ? mContext.getResources().getDimension(R.dimen.rowHeight) : mHeaderRowHeight);
    }

    public void setHeaderRowHeight(float rowHeight) {
        mHeaderRowHeight = computeSize(rowHeight);
    }

    public int getMinFixedRowWidth() {
        return (int) ((mMinFixedRowWidth == 0) ? mContext.getResources().getDimension(R.dimen.minFixedRowWidth) : mMinFixedRowWidth);
    }

    public void setMinFixedRowWidth(float minFixedRowWidth) {
        mMinFixedRowWidth = computeSize(minFixedRowWidth);
    }

    public int getTextGravity() {
        return mTextGravity;
    }

    public void setTextGravity(int gravity) {
        mTextGravity = gravity;
        wrapWrapTableRowParams.gravity = gravity;
    }

    public int computeSize(float size) {
        return (int) (size * screenDensity + 0.5f);
    }

    public int getTextPaddingLeft() {
        return mTextPaddingLeft;
    }

    public void setTextPaddingLeft(int textPaddingLeft) {
        this.mTextPaddingLeft = textPaddingLeft;
    }

    public int getTextPaddingRight() {
        return mTextPaddingRight;
    }

    public void setTextPaddingRight(int textPaddingRight) {
        this.mTextPaddingRight = textPaddingRight;
    }

    public TableRow.LayoutParams getTableLayoutParams() {
        return wrapWrapTableRowParams;
    }

    public Context getContext() {
        return mContext;
    }

}
