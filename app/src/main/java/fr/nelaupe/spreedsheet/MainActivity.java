package fr.nelaupe.spreedsheet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import org.fluttercode.datafactory.impl.DataFactory;

import fr.nelaupe.spreadsheetlib.AnnotationFields;
import fr.nelaupe.spreadsheetlib.SimpleTextAdaptor;
import fr.nelaupe.spreadsheetlib.SpreadSheetView;

public class MainActivity extends Activity {

    private long start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start = System.currentTimeMillis();

        setContentView(R.layout.activity_main);

        SpreadSheetView spreadSheetView = (SpreadSheetView) findViewById(R.id.spread_sheet);

        PersonAdaptor personAdaptor = new PersonAdaptor(this);
        spreadSheetView.setAdaptor(personAdaptor.with(Person.class));

        for (int i = 0; i < 30; i++) {
            spreadSheetView.getAdaptor().add(generateDummyData(i));
        }

        spreadSheetView.invalidate();
    }

    private Person generateDummyData(int i) {
        return new Person(i, new DataFactory());
    }

    @Override
    protected void onResume() {
        super.onResume();

        long diff = System.currentTimeMillis() - start;
        System.out.println("TIME TO LOAD : " + diff);
    }

    private View inflateTextView(String text) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cell_textview, null, false);

        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(text);

        return view;
    }

    private View inflateCheckbox(Boolean bool) {
        CheckBox checkBox = new CheckBox(MainActivity.this);
        checkBox.setChecked(bool);
        checkBox.setEnabled(false);
        return checkBox;
    }

    private class PersonAdaptor extends SimpleTextAdaptor<Person> {

        public PersonAdaptor(Context context) {
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
