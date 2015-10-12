package fr.nelaupe.spreedsheet;

import android.app.Activity;
import android.os.Bundle;

import org.fluttercode.datafactory.impl.DataFactory;

import fr.nelaupe.spreadsheetlib.SpreadSheetView;

public class MainActivity extends Activity {

    private long start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start = System.currentTimeMillis();

        setContentView(R.layout.activity_main);

        SpreadSheetView spreadSheetView = (SpreadSheetView) findViewById(R.id.spread_sheet);
        initSpreadSheet(spreadSheetView);
        spreadSheetView.setArrow(0, true);
        spreadSheetView.invalidate();
    }

    protected void initSpreadSheet(SpreadSheetView spreadSheetView) {
        for (int i = 0; i < 30; i++) {
            spreadSheetView.getAdaptor().add(generateDummyData(i));
        }
    }

    protected Data generateDummyData(int i) {
        DataFactory df = new DataFactory();

        Data data = new Data();
        data.id = i;
        data.first_name = df.getFirstName();
        data.last_name = df.getLastName();
        data.phone_number = df.getCity();
        data.company = new CustomCompany(df.getBusinessName());
        data.birthday = df.getBirthDate().toString();
        data.email = df.getEmailAddress();
        data.gender = i % 3 == 0;

        return data;
    }

    @Override
    protected void onResume() {
        super.onResume();

        long diff = System.currentTimeMillis() - start;
        System.out.println("TIME TO LOAD : "+diff);
    }

}
