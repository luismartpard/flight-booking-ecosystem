package com.lmp.geographyservice.infrastructure.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Immutable;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Getter
@Immutable
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name= "continents", schema = "geography")
public class ContinentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_continent")
    private Long id;

    @NonNull
    @Column(name = "code", nullable = false, unique = true, length = 2)
    private String code;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ContinentJpaEntity continent = (ContinentJpaEntity) o;
        return id != null && Objects.equals(id, continent.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
