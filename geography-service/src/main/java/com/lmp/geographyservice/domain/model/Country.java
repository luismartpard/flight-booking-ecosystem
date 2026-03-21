package com.lmp.geographyservice.domain.model;

import com.lmp.geographyservice.domain.enums.TravelStatus;

import java.util.Objects;

public class Country {

    private final String iso2;

    private final String iso3;

    private final Integer isoNumeric;

    private final String defaultName;

    private final String phoneCode;

    private final String currencyCode;

    private TravelStatus travelStatus;

    public Country(String iso2, String iso3, Integer isoNumeric, String defaultName, String phoneCode, String currencyCode, TravelStatus travelStatus) {
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.isoNumeric = isoNumeric;
        this.defaultName = defaultName;
        this.phoneCode = phoneCode;
        this.currencyCode = currencyCode;
        this.travelStatus = travelStatus;
    }

    public String getIso2() {
        return iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public Integer getIsoNumeric() {
        return isoNumeric;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public TravelStatus getTravelStatus() {
        return travelStatus;
    }

    public void changeTravelStatus(TravelStatus newTravelStatus) {

        Objects.requireNonNull(newTravelStatus);

        if (this.travelStatus == newTravelStatus) {
            return;
        }

        travelStatus = newTravelStatus;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(iso2, country.iso2) && Objects.equals(iso3, country.iso3) && Objects.equals(isoNumeric, country.isoNumeric) && Objects.equals(defaultName, country.defaultName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iso2, iso3, isoNumeric, defaultName);
    }

}
