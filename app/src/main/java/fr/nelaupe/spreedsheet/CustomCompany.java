/**
 * Copyright
 */
package fr.nelaupe.spreedsheet;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public class CustomCompany implements Comparable<CustomCompany> {

    private int id;

    private String mData;

    public CustomCompany(String data) {
        this.mData = data;
    }

    @Override
    public int compareTo(CustomCompany another) {
        return mData.compareTo(another.mData);
    }

    @Override
    public String toString() {
        return mData;
    }
}
