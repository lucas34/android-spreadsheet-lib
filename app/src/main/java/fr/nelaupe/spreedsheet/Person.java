/**
 * Copyright
 */
package fr.nelaupe.spreedsheet;

import org.fluttercode.datafactory.impl.DataFactory;

import java.text.SimpleDateFormat;

import fr.nelaupe.spreadsheetlib.SpreadSheetCell;
import fr.nelaupe.spreadsheetlib.SpreadSheetData;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
@SuppressWarnings("all")
public class Person extends SpreadSheetData {

    @SpreadSheetCell(name = "ID", size = 100, position = 1)
    public Integer id;

    @SpreadSheetCell(name = "First Name", size = 300, position = 2)
    public String firstName;

    @SpreadSheetCell(name = "Last Name", size = 300, position = 3)
    public String lastName;

    @SpreadSheetCell(name = "city", size = 300, position = 4)
    public String city;

    @SpreadSheetCell(name = "Company", size = 300, position = 5)
    public CustomCompany company;

    @SpreadSheetCell(name = "Birth date", size = 200, position = 6)
    public String birthday;

    @SpreadSheetCell(name = "Email", size = 300, position = 7)
    public String email;

    @SpreadSheetCell(name = "M", size = 80, position = 8)
    public boolean gender;

    public Person(int id, DataFactory dataFactory, SimpleDateFormat simpleDateFormat) {
        this.id = id;
        this.firstName = dataFactory.getFirstName();
        this.lastName = dataFactory.getLastName();
        this.city = dataFactory.getCity();
        this.company = new CustomCompany(dataFactory.getBusinessName());
        this.birthday = simpleDateFormat.format(dataFactory.getBirthDate());
        this.email = dataFactory.getEmailAddress();
        this.gender = id % 3 == 0;
    }

}
