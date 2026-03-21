package com.lmp.geographyservice.infrastructure.out.persistence.specification;

import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.CountryJpaEntity;
import org.springframework.data.jpa.domain.Specification;

public class CountrySpecification {

    private  CountrySpecification() {
    }

    public static Specification<CountryJpaEntity> hasContinentCode(String continentCode) {
        return (root, query, cb) -> {
            if (continentCode == null || continentCode.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("continent").get("code"), continentCode.trim());
        };
    }

    public static Specification<CountryJpaEntity> hasCurrencyCode(String currencyCode) {
        return(root, query, cb) -> {
            if(currencyCode == null || currencyCode.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("currencyCode"), currencyCode.trim());
        };
    }

    public static Specification<CountryJpaEntity> hasTravelStatus(TravelStatus travelStatus) {
        return (root, query, cb) -> {
            if (travelStatus == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("travelStatus"), travelStatus);
        };
    }


}
