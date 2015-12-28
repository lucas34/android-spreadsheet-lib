/**
 * Copyright
 */
package fr.nelaupe.spreadsheetlib;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public class CellInformation {

    private final String mName;

    private final int mColumnSize;

    private final int mPosition;

    public CellInformation(String name, int columnSize, int position) {
        this.mName = name;
        this.mColumnSize = columnSize;
        this.mPosition = position;
    }

    public CellInformation(SpreadSheetCell annotation) {
        this.mName = annotation.name();
        this.mColumnSize = annotation.size();
        this.mPosition = annotation.position();
    }


    public String getName() {
        return mName;
    }

    public int getSize() {
        return mColumnSize;
    }

    public int getPosition() {
        return mPosition;
    }
}
