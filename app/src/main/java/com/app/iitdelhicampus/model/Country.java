package com.app.iitdelhicampus.model;

import java.util.Locale;

/**
 * Created by pradeepbishnoi on 24/06/16.
 */
public final class Country {
    public final String name;
    public final String countryCode;
    public final String isoCode;
    private final String data;

    public Country(String countryInfo) {
        data = countryInfo;
        String[] info = countryInfo.split(",");
        countryCode = String.format("+%s", info[0]);
        isoCode = info[1];
        name = new Locale("", isoCode).getDisplayCountry();
    }

    @Override
    public String toString() {
        return data;
    }
}
