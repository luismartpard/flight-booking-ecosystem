package com.lmp.geographyservice.domain.model;

import java.util.Objects;

public class Continent {

    private final String code;

    private final String name;

    private Continent(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Continent of(String code, String name) {
        return new Continent(code, name);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Continent continent)) return false;
        return Objects.equals(code, continent.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

}
