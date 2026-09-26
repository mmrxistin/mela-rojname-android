// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.model;

import com.google.gson.annotations.SerializedName;

public enum Gender {
    @SerializedName("MALE")
    MALE("ERKEK", "Male"),

    @SerializedName("FEMALE")
    FEMALE("KADIN", "Female");

    private final String trName;
    private final String enName;

    Gender(String trName, String enName) {
        this.trName = trName;
        this.enName = enName;
    }

    public String getTrName() {
        return trName;
    }

    public String getEnName() {
        return enName;
    }

    public static Gender fromString(String value) {
        if (value == null) return MALE;
        String valUpper = value.trim().toUpperCase();
        if (valUpper.contains("FEMALE") || valUpper.contains("KADIN") || valUpper.startsWith("F") || valUpper.startsWith("K")) {
            return FEMALE;
        }
        return MALE;
    }
}
