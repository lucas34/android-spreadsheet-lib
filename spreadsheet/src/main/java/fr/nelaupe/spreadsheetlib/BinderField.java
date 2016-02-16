package fr.nelaupe.spreadsheetlib;

/**
 * Created by lucas34990 on 16/2/16.
 */
public class BinderField<TSelf extends SpreadSheetData> {

    public java.util.List<fr.nelaupe.spreadsheetlib.AnnotationFields> fields;

    public Object getValueAt(String fieldName, TSelf data) {
        return null;
    }

}
