# Android Spreadsheet

Automatic creation of two-dimensional scrollable array for Android

## Add Dependency

<a href='http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22fr.nelaupe%22%20AND%20a%3A%22spreadsheet%22'><img src='http://img.shields.io/maven-central/v/fr.nelaupe/spreadsheet.svg'></a>

**Gradle dependency**

``` groovy
compile 'fr.nelaupe:spreadsheet:1.0.4@aar'
```

**Maven dependency**

``` xml
<dependency>
    <groupId>fr.nelaupe</groupId>
    <artifactId>spreadsheet</artifactId>
    <version>1.0.4</version>
</dependency>
```



## Sample

Define the datas

``` java
public class Data extends SpreadSheetData {
	
	@SpreadSheetCell(name = "ID", size = 100, position = 1)
    public Integer id;

    @SpreadSheetCell(name = "First Name", size = 300, position = 2)
    public String first_name;

    @SpreadSheetCell(name = "Last Name", size = 300, position = 3)
    public String last_name;

    @SpreadSheetCell(name = "Number", size = 300, position = 4)
    public String phone_number;

    @SpreadSheetCell(name = "Company", size = 300, position = 5)
    public CustomCompany company;

    @SpreadSheetCell(name = "Birth date", size = 300, position = 6)
    public String birthday;

    @SpreadSheetCell(name = "Email", size = 300, position = 7)
    public String email;

    @SpreadSheetCell(name = "M", size = 80, position = 8)
    public boolean gender;

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

SpreadSheetView spreadSheetView = (SpreadSheetView) findViewById(R.id.spread_sheet);
for (int i = 0; i < 30; i++) {
	Data data = new Data();
	// Fill the data

	spreadSheetView.getAdaptor().add(data);
}
spreadSheetView.invalidate();

```


## Contributors

* [Lucas Nelaupe](http://www.lucas-nelaupe.fr/) - <https://github.com/lucas34>

## License

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
