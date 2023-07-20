package com.titan.path.params;

import com.google.gson.annotations.SerializedName;
import com.titan.path.core.Country;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CountriesDto {
    @SerializedName("cca3")
    public String cca3;

    @SerializedName("borders")
    ArrayList<String> borders;

    @SerializedName("latlng")
    public ArrayList<Double> latlng;

    public String getCca3() {
        return cca3;
    }

    public Set<String> getSetBorders() {
        return new HashSet<>(borders);
    }

    public Country mapCountry() {
        return new Country(cca3, cca3, latlng.get(0), latlng.get(1));
    }

}
