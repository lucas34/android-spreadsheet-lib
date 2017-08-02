package fr.nelaupe.spreedsheet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.test.spreadsheet.Person;

import org.fluttercode.datafactory.impl.DataFactory;

import java.text.SimpleDateFormat;
import java.util.Locale;

import fr.nelaupe.spreadsheetlib.AnnotationFields;
import fr.nelaupe.spreadsheetlib.SimpleTextAdaptor;
import fr.nelaupe.spreadsheetlib.SpreadSheetView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        PersonAdaptor personAdaptor = new PersonAdaptor(this);

        for (int i = 0; i < 30; i++) {
            personAdaptor.add(new Person(i, new DataFactory(), sdf));
        }

        SpreadSheetView spreadSheetView = (SpreadSheetView) findViewById(R.id.spread_sheet);
        spreadSheetView.setAdaptor(personAdaptor);
        spreadSheetView.invalidate();
    }

    private static class PersonAdaptor extends SimpleTextAdaptor<Person> {

        public PersonAdaptor(Context context) {
            super(context);
            with(Person.class);
        }

        @Override
        public View getCellView(AnnotationFields cell, Object object) {
            if (object.getClass().equals(Boolean.class)) {
                return inflateCheckbox(getContext(), (Boolean) object);
            } else {
                return inflateTextView(object.toString());
            }
        }

        private View inflateCheckbox(Context context, Boolean bool) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setChecked(bool);
            checkBox.setEnabled(false);
            return checkBox;
        }


        private View inflateTextView(String text) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.cell_textview, null, false);

            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(text);

            return view;
        }

    }

}
