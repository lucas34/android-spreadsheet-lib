/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib.view;

import android.view.View;

import fr.nelaupe.spreadsheetlib.SpreadSheetCell;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public abstract class SpreadSheetAdaptor {

    public abstract View getView(SpreadSheetCell cell);

}
