/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public class AnnotationFields {

    private final String mName;
    private final boolean mIsComparable;

    private final String mFieldName;
    private final int mColumnSize;
    private final int mPosition;

    public AnnotationFields(String annotationName, int annotationSize, int annotationPosition, String fieldName, boolean isComparable) {
        this.mFieldName = fieldName;
        this.mIsComparable = isComparable;

        this.mName = annotationName;
        this.mColumnSize = annotationSize;
        this.mPosition = annotationPosition;
    }

    public boolean isComparable() {
        return true;
    }

    public String getFieldName() {
        return mFieldName;
    }

    public String getName() {
        return mName;
    }

    public int getColumnSize() {
        return mColumnSize;
    }

    public int getPosition() {
        return mPosition;
    }

}
