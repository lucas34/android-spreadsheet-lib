# Android Spreadsheet

## This library is deprecated and will no longer receive update.

<img src='https://travis-ci.org/lucas34/android-spreadsheet-lib.svg?branch=master'>

Automatic creation of two-dimensional scrollable spreadsheet for Android.
The spreadsheet is automatically generated, any entity can be diplayed. No extends is needed. The library is using annotations android processor to generate the array. No reflection API is used at runtime. 
The library requires at least Android 1.6 Donut (API level 4).

## Add Dependencies

<a href='http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22fr.nelaupe%22%20AND%20a%3A%22spreadsheet%22'><img src='http://img.shields.io/maven-central/v/fr.nelaupe/spreadsheet.svg'></a>

**Gradle dependencies**

``` groovy
compile 'fr.nelaupe:spreadsheet:1.1.1@aar'
compile 'fr.nelaupe:spreadsheet-compiler:1.1.1'
```

## Sample

![Result](https://github.com/lucas34/android-spreadsheet-lib/blob/master/image/sample.gif)

Create your entity

``` java
public class Person {
	
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

}

```

Layout
``` xml

<fr.nelaupe.spreadsheetlib.SpreadSheetView
	android:id="@+id/spread_sheet"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1"
    sheet:headerColor="#009FFF"
    sheet:headerRowHeight="50dp"
    sheet:headerTextColor="#FFFFFF"
    sheet:headerTextSize="17sp"
    sheet:minFixedRowWidth="80dp"
    sheet:rowHeight="50dp"
    sheet:textColor="@color/text"
    sheet:textPaddingLeft="15dp"
    sheet:textSize="15sp"
    />
```

Activity 

``` java

SpreadSheetAdaptor adaptor = new SpreadSheetAdaptor(context);
// Fill data

SpreadSheetView spreadSheetView = (SpreadSheetView) findViewById(R.id.spread_sheet);
spreadSheetView.setAdaptor(adaptor);
spreadSheetView.invalidate();

```


## Contributor

* [Lucas Nelaupe](http://www.lucas-nelaupe.fr/) - <https://github.com/lucas34>

## License

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
