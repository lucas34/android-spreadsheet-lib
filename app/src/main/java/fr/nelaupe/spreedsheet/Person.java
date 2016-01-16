/**
 * Copyright
 */
package fr.nelaupe.spreedsheet;

import org.fluttercode.datafactory.impl.DataFactory;

import fr.nelaupe.spreadsheetlib.SpreadSheetCell;
import fr.nelaupe.spreadsheetlib.SpreadSheetData;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
@SuppressWarnings("all")
public class Person extends SpreadSheetData {

    // Put ONLY The DATA that you need to display !!

    @SpreadSheetCell(name = "ID", size = 100, position = 1)
    private Integer id;

    @SpreadSheetCell(name = "First Name", size = 300, position = 2)
    private String firstName;

    @SpreadSheetCell(name = "Last Name", size = 300, position = 3)
    private String lastName;

    @SpreadSheetCell(name = "city", size = 300, position = 4)
    private String city;

    @SpreadSheetCell(name = "Company", size = 300, position = 5)
    private CustomCompany company;

    @SpreadSheetCell(name = "Birth date", size = 300, position = 6)
    private String birthday;

    @SpreadSheetCell(name = "Email", size = 300, position = 7)
    private String email;

    @SpreadSheetCell(name = "M", size = 80, position = 8)
    private boolean gender;

    public Person(int id, DataFactory dataFactory) {
        this.id = id;
        this.firstName = dataFactory.getFirstName();
        this.lastName = dataFactory.getLastName();
        this.city = dataFactory.getCity();
        this.company = new CustomCompany(dataFactory.getBusinessName());
        this.birthday = dataFactory.getBirthDate().toString();
        this.email = dataFactory.getEmailAddress();
        this.gender = id % 3 == 0;
    }

}
