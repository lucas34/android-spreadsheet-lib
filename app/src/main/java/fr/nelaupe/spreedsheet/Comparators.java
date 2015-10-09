/**
 * Copyright
 */
package fr.nelaupe.spreedsheet;

import java.util.Comparator;

import fr.nelaupe.spreadsheetlib.SpreadSheetComparators;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
@SuppressWarnings("unused")
public class Comparators extends SpreadSheetComparators {

    // Put only the comparators
    // The method name need to be EXACTLY the same as the one defined in the annotation

    public static final Comparator<Data> COMPARE_COLUMN1 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column1.compareTo(rhs.column1);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN2 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column2.compareTo(rhs.column2);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN3 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column3.compareTo(rhs.column3);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN4 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column4.compareTo(rhs.column4);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN5 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column5.compareTo(rhs.column5);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN6 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column6.compareTo(rhs.column6);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN7 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.column7.compareTo(rhs.column7);
        }
    };
}
