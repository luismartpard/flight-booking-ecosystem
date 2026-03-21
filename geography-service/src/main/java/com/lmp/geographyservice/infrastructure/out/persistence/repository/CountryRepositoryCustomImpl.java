package com.lmp.geographyservice.infrastructure.out.persistence.repository;

import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.QCountryJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryCustomImpl implements CountryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public long updateCountryTravelStatus(String iso2, TravelStatus travelStatus) {

        QCountryJpaEntity country = QCountryJpaEntity.countryJpaEntity;

        long update = jpaQueryFactory
                .update(country)
                .set(country.travelStatus, travelStatus)
                .where(country.iso2.eq(iso2))
                .execute();

        entityManager.clear();
        return update;

    }
}
