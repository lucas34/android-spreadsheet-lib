package fr.nelaupe.spreedsheet;

import android.app.Activity;
import android.os.Bundle;

import java.util.Random;

import fr.nelaupe.spreadsheetlib.SpreadSheetView;

public class MainActivity extends Activity {

    private final Random mRand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpreadSheetView spreadSheetView = (SpreadSheetView) findViewById(R.id.spread_sheet);

        for (int i = 0; i < 30; i++) {
            Data data = new Data();
            data.column1 = getRandom();
            data.column2 = getRandom();
            data.column3 = getRandom();
            data.column4 = getRandom();
            data.column5 = getRandom();
            data.column6 = getRandom();
            data.column7 = getRandom();
            data.column8 = getRandom();
            data.column9 = getRandom();
            spreadSheetView.add(data);
        }

        spreadSheetView.invalidate();

    }

    private String getRandom() {
        int myRandomNumber = mRand.nextInt(0x10) + 0x10;
        return Integer.toHexString(myRandomNumber);
    }

}
