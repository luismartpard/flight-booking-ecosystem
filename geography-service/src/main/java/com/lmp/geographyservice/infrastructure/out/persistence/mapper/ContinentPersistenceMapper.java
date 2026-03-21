package com.lmp.geographyservice.infrastructure.out.persistence.mapper;

import com.lmp.geographyservice.domain.model.Continent;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.ContinentJpaEntity;

public final class ContinentPersistenceMapper {

    private ContinentPersistenceMapper() {}

    public static Continent toDomain(ContinentJpaEntity entity) {

        if (entity == null) {
            return null;
        }

        return new Continent(
                entity.getCode()
        );

    }

}
