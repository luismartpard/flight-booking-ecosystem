package com.lmp.geographyservice.fixtures;

import com.lmp.geographyservice.infrastructure.out.persistence.entity.ContinentJpaEntity;

public final class ContinentJpaEntityTestFactory {

    private ContinentJpaEntityTestFactory() {}

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

        public ContinentJpaEntity build() {
            return ContinentJpaEntity.of(code, name);
        }

    }

}
