package com.lmp.geographyservice.infrastructure.out.persistence.repository;

import com.lmp.geographyservice.infrastructure.out.persistence.entity.ContinentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContinentRepositoryJpa extends JpaRepository<ContinentJpaEntity, Long> {

    Optional<ContinentJpaEntity> findByCode(String code);

}
