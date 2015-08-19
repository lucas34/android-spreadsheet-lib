/**
 * Copyright
 */
package fr.nelaupe.spreedsheet;

import java.util.Comparator;

import fr.nelaupe.spreadsheetlib.SpreadSheetCell;
import fr.nelaupe.spreadsheetlib.SpreadSheetData;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
@SuppressWarnings("unused")
public class Data extends SpreadSheetData {

    @SpreadSheetCell(name = "id", size = 300, filterName = "COMPARE_COLUMN1")
    public Integer column1;
    public static final Comparator<Data> COMPARE_COLUMN1 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column1.compareTo(rhs.column1);
        }
    };

    @SpreadSheetCell(name = "First Name", size = 300, filterName = "COMPARE_COLUMN2")
    public String column2;
    public static final Comparator<Data> COMPARE_COLUMN2 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column2.compareTo(rhs.column2);
        }
    };

    @SpreadSheetCell(name = "Last Name", size = 300, filterName = "COMPARE_COLUMN3")
    public String column3;
    public static final Comparator<Data> COMPARE_COLUMN3 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column3.compareTo(rhs.column3);
        }
    };

    @SpreadSheetCell(name = "Number", size = 300, filterName = "COMPARE_COLUMN4")
    public String column4;
    public static final Comparator<Data> COMPARE_COLUMN4 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column4.compareTo(rhs.column4);
        }
    };

    @SpreadSheetCell(name = "Company", size = 300, filterName = "COMPARE_COLUMN5")
    public String column5;
    public static final Comparator<Data> COMPARE_COLUMN5 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column5.compareTo(rhs.column5);
        }
    };

    @SpreadSheetCell(name = "Birth date", size = 300, filterName = "COMPARE_COLUMN6")
    public String column6;
    public static final Comparator<Data> COMPARE_COLUMN6 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column6.compareTo(rhs.column6);
        }
    };

    @SpreadSheetCell(name = "Email", size = 300, filterName = "COMPARE_COLUMN7")
    public String column7;
    public static final Comparator<Data> COMPARE_COLUMN7 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column7.compareTo(rhs.column7);
        }
    };

}
