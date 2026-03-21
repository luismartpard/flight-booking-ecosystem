package com.lmp.geographyservice.infrastructure.out.persistence.mapper;

import com.lmp.geographyservice.domain.model.Country;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.CountryJpaEntity;

public final class CountryPersistenceMapper {

    private CountryPersistenceMapper() {
    }

    public static Country toDomain(CountryJpaEntity entity) {

        if (entity == null) {
            return null;
        }

        return new Country(
                entity.getIso2(),
                entity.getIso3(),
                entity.getIsoNumeric(),
                entity.getDefaultName(),
                entity.getPhoneCode(),
                entity.getCurrencyCode(),
                entity.getTravelStatus()
        );

    }

}
