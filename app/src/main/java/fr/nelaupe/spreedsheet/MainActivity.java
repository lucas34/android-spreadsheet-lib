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
        spreadSheetView.invalidate();
    }

    protected void initSpreadSheet(SpreadSheetView spreadSheetView) {
        for (int i = 0; i < 30; i++) {
            spreadSheetView.getAdaptor().add(generateDummyData(i));
        }
    }

    protected Person generateDummyData(int i) {
        return new Person(i, new DataFactory());
    }

    @Override
    protected void onResume() {
        super.onResume();

        long diff = System.currentTimeMillis() - start;
        System.out.println("TIME TO LOAD : " + diff);
    }

}
