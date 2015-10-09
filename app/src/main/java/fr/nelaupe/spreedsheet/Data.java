/**
 * Copyright
 */
package fr.nelaupe.spreedsheet;

import fr.nelaupe.spreadsheetlib.SpreadSheetCell;
import fr.nelaupe.spreadsheetlib.SpreadSheetComparators;
import fr.nelaupe.spreadsheetlib.SpreadSheetData;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
@SuppressWarnings("unused")
public class Data extends SpreadSheetData {

    // Put ONLY The DATA that you need to display !!

    @SpreadSheetCell(name = "ID", size = 100, filterName = "COMPARE_COLUMN1")
    public Integer column1;
    @SpreadSheetCell(name = "First Name", size = 300, filterName = "COMPARE_COLUMN2")
    public String column2;
    @SpreadSheetCell(name = "Last Name", size = 300, filterName = "COMPARE_COLUMN3")
    public String column3;
    @SpreadSheetCell(name = "Number", size = 300, filterName = "COMPARE_COLUMN4")
    public String column4;
    @SpreadSheetCell(name = "Company", size = 300, filterName = "COMPARE_COLUMN5")
    public String column5;
    @SpreadSheetCell(name = "Birth date", size = 300, filterName = "COMPARE_COLUMN6")
    public String column6;
    @SpreadSheetCell(name = "Email", size = 300, filterName = "COMPARE_COLUMN7")
    public String column7;
    @SpreadSheetCell(name = "Male?", size = 80, filterName = "")
    public boolean column8;

    public Data(Class<? extends SpreadSheetComparators> comparators) {
        super(comparators);
    }


}
