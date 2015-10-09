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
public abstract class SpreadSheetAdaptor {

    private List<SpreadSheetData> mData;

    public SpreadSheetAdaptor() {
        mData = new ArrayList<>();
    }

    public void add(SpreadSheetData data) {
        mData.add(data);
    }

    public void addAll(List<? extends SpreadSheetData> data) {
        mData.addAll(data);
    }

    public List<? extends SpreadSheetData> getData() {
        return mData;
    }

    public void clearData() {
        mData = new ArrayList<>();
    }

    public abstract View getView(SpreadSheetCell cell, Object object);

}
