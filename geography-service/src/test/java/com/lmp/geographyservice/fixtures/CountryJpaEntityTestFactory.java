package com.lmp.geographyservice.fixtures;

import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.CountryJpaEntity;

public final class CountryJpaEntityTestFactory {

    private CountryJpaEntityTestFactory() {}

    public static Builder builder() {return new Builder();}

    public static class Builder {

        private String iso2 =  "ES";
        private String iso3 = "ESP";
        private Integer isoNumeric = 724;
        private String defaultName = "Spain";
        private String currencyCode = "EUR";
        private TravelStatus travelStatus = TravelStatus.AVAILABLE;

        public Builder iso2(String iso2) {
            this.iso2 = iso2;
            return this;
        }

        public Builder iso3(String iso3) {
            this.iso3 = iso3;
            return this;
        }

        public Builder isoNumeric(Integer isoNumeric) {
            this.isoNumeric = isoNumeric;
            return this;
        }

        public  Builder defaultName(String defaultName) {
            this.defaultName = defaultName;
            return this;
        }

        public Builder currencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Builder travelStatus(TravelStatus travelStatus) {
            this.travelStatus = travelStatus;
            return this;
        }

        public CountryJpaEntity build() {
            return new CountryJpaEntity(iso2, iso3, isoNumeric, defaultName, currencyCode, travelStatus);
        }

    }

}
