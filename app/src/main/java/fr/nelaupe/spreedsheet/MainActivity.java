package fr.nelaupe.spreedsheet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import org.fluttercode.datafactory.impl.DataFactory;

import fr.nelaupe.spreadsheetlib.SimpleTextAdaptor;
import fr.nelaupe.spreadsheetlib.SpreadSheetCell;
import fr.nelaupe.spreadsheetlib.SpreadSheetView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpreadSheetView spreadSheetView = (SpreadSheetView) findViewById(R.id.spread_sheet);

        initSpreadSheet(spreadSheetView);
//        initSpreadSheetWithAdaptor(spreadSheetView);

        spreadSheetView.invalidate();
    }

    // Simple but not flexible. Create only text row.
    private void initSpreadSheet(SpreadSheetView spreadSheetView) {
        for (int i = 0; i < 30; i++) {
            spreadSheetView.add(generateDummyData(i));
        }
    }

    private void initSpreadSheetWithAdaptor(SpreadSheetView spreadSheetView) {
        CustomCellAdaptor cellAdaptor = new CustomCellAdaptor(this);
        for (int i = 0; i < 30; i++) {
            cellAdaptor.add(generateDummyData(i));
        }
        spreadSheetView.setAdaptor(cellAdaptor);
    }


    private Data generateDummyData(int i) {
        DataFactory df = new DataFactory();

        Data data = new Data(Comparators.class);
        data.column1 = i;
        data.column2 = df.getFirstName();
        data.column3 = df.getLastName();
        data.column4 = df.getCity();
        data.column5 = df.getBusinessName();
        data.column6 = df.getBirthDate().toString();
        data.column7 = df.getEmailAddress();
        data.column8 = i % 3 == 0;

        return data;
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

    private class CustomCellAdaptor extends SimpleTextAdaptor {

        public CustomCellAdaptor(Context context) {
            super(context);
        }

        @Override
        public View getCellView(SpreadSheetCell cell, Object object) {
            if (object.getClass().equals(Boolean.class)) {
                return inflateCheckbox((Boolean) object);
            } else {
                return inflateTextView(object.toString());
            }
        }
    }

}
