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
    private CellInformation mAnnotation;

    public AnnotationFields(Field mField, CellInformation mAnnotation) {
        this.mField = mField;
        this.mAnnotation = mAnnotation;
    }

//    public String getAnnotationName() {
//        return mAnnotation.get();
//    }

    public Field getField() {
        return mField;
    }

    public String getFieldName() {
        return mField.getName();
    }

    public CellInformation getAnnotation() {
        return mAnnotation;
    }
}
