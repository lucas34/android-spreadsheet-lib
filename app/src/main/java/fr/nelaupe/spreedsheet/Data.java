/**
 * Copyright
 */
package fr.nelaupe.spreedsheet;

import fr.nelaupe.spreadsheetlib.SpreadSheetCell;
import fr.nelaupe.spreadsheetlib.SpreadSheetData;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
@SuppressWarnings("unused")
public class Data extends SpreadSheetData {

    // Put ONLY The DATA that you need to display !!

    @SpreadSheetCell(name = "ID", size = 100, position = 1)
    public Integer id;

    @SpreadSheetCell(name = "First Name", size = 300, position = 2)
    public String first_name;

    @SpreadSheetCell(name = "Last Name", size = 300, position = 3)
    public String last_name;

    @SpreadSheetCell(name = "Number", size = 300, position = 4)
    public String phone_number;

    @SpreadSheetCell(name = "Company", size = 300, position = 5)
    public String company;

    @SpreadSheetCell(name = "Birth date", size = 300, position = 6)
    public String birthday;

    @SpreadSheetCell(name = "Email", size = 300, position = 7)
    public String email;

    @SpreadSheetCell(name = "M", size = 80, position = 8)
    public boolean gender;


}
