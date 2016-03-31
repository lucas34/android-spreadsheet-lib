package fr.nelaupe.spreadsheetlib;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lucas34990 on 16/2/16.
 */
public abstract class FieldBinder<TSelf> {

    private List<AnnotationFields> fields;

    public FieldBinder() {
        this.fields = init();
    }

    protected abstract List<AnnotationFields> fill();

    public abstract Object getValueAt(String fieldName, TSelf data);

    public List<AnnotationFields> fields() {
        return fields;
    }

    private List<AnnotationFields> init() {
        List<AnnotationFields> fields = fill();
        Collections.sort(fields, new Comparator<AnnotationFields>() {
            @Override
            public int compare(AnnotationFields lhs, AnnotationFields rhs) {
                Integer positionL = lhs.getPosition();
                Integer positionR = rhs.getPosition();

                return positionL.compareTo(positionR);
            }
        });
        return Collections.unmodifiableList(fields);
    }


}