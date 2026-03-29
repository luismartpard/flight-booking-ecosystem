package com.lmp.geographyservice.fixtures;

import com.lmp.geographyservice.domain.model.Continent;

public final class ContinentTestFactory {

    private ContinentTestFactory() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String code = "EU";
        private String name = "Europe";

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Continent build() {
            return Continent.of(code, name);
        }

    }

}
