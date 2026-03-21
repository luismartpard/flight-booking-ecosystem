package com.lmp.geographyservice.infrastructure.out.persistence.repository;

import com.lmp.geographyservice.infrastructure.out.persistence.entity.CountryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CountryRepositoryJpa extends JpaRepository<CountryJpaEntity, Long>,
        JpaSpecificationExecutor<CountryJpaEntity>,
        CountryRepositoryCustom {

    Optional<CountryJpaEntity> findByIso2(String iso2);

    Optional<CountryJpaEntity> findByIso3(String iso3);

}
