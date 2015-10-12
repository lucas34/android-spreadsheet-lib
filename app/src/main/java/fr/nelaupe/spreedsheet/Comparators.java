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
            return lhs.id.compareTo(rhs.id);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN2 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.first_name.compareTo(rhs.first_name);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN3 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.last_name.compareTo(rhs.last_name);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN4 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.phone_number.compareTo(rhs.phone_number);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN5 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.company.compareTo(rhs.company);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN6 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.birthday.compareTo(rhs.birthday);
        }
    };

    public static final Comparator<Data> COMPARE_COLUMN7 = new Comparator<Data>() {
        public int compare(Data lhs, Data rhs) {
            return lhs.email.compareTo(rhs.email);
        }
    };
}
