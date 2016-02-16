/**
 * Copyright
 */
package fr.nelaupe.spreedsheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import fr.nelaupe.spreadsheetlib.AnnotationFields;
import fr.nelaupe.spreadsheetlib.SimpleTextAdaptor;
import fr.nelaupe.spreadsheetlib.SpreadSheetView;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public class ActivityWithCustomAdaptor extends MainActivity {

    @Override
    protected void initSpreadSheet(SpreadSheetView spreadSheetView) {
        CustomCellAdaptor cellAdaptor = new CustomCellAdaptor(this);
        for (int i = 0; i < 30; i++) {
            cellAdaptor.add(generateDummyData(i));
        }
        spreadSheetView.setAdaptor(cellAdaptor);
    }

    private View inflateTextView(String text) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cell_textview, null, false);

        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(text);

        return view;
    }

    private View inflateCheckbox(Boolean bool) {
        CheckBox checkBox = new CheckBox(ActivityWithCustomAdaptor.this);
        checkBox.setChecked(bool);
        checkBox.setEnabled(false);
        return checkBox;
    }

    private class CustomCellAdaptor extends SimpleTextAdaptor {

        public CustomCellAdaptor(Context context) {
            super(context);
        }

        @Override
        public View getCellView(AnnotationFields cell, Object object) {
            if (object.getClass().equals(Boolean.class)) {
                return inflateCheckbox((Boolean) object);
            } else {
                return inflateTextView(object.toString());
            }
        }
    }

}
