package com.lmp.geographyservice.domain.model;

import java.util.Objects;

public class Continent {

    private String code;

    public Continent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Continent)) return false;
        Continent continent = (Continent) o;
        return Objects.equals(code, continent.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

}
