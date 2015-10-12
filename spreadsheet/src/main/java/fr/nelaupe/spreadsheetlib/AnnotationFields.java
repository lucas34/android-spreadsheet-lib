/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public class AnnotationFields {

    private Field mField;
    private SpreadSheetCell mAnnotation;

    public AnnotationFields(Field mField, SpreadSheetCell mAnnotation) {
        this.mField = mField;
        this.mAnnotation = mAnnotation;
    }

    public SpreadSheetCell getAnnotation() {
        return mAnnotation;
    }

    public Field getField() {
        return mField;
    }
}
