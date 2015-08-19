package fr.nelaupe.spreedsheet;

import android.app.Activity;
import android.os.Bundle;

import org.fluttercode.datafactory.impl.DataFactory;

import fr.nelaupe.spreadsheetlib.SpreadSheetView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpreadSheetView spreadSheetView = (SpreadSheetView) findViewById(R.id.spread_sheet);

        for (int i = 0; i < 30; i++) {
            DataFactory df = new DataFactory();

            Data data = new Data();
            data.column1 = i;
            data.column2 = df.getFirstName();
            data.column3 = df.getLastName();
            data.column4 = df.getCity();
            data.column5 = df.getBusinessName();
            data.column6 = df.getBirthDate().toString();
            data.column7 = df.getEmailAddress();
            spreadSheetView.add(data);
        }

        spreadSheetView.invalidate();

    }

}
