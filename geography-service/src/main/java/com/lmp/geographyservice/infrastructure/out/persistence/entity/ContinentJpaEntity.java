package com.lmp.geographyservice.infrastructure.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Immutable;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Getter
@Immutable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name= "continents", schema = "geography")
public class ContinentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_continent")
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 2)
    private String code;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    private ContinentJpaEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ContinentJpaEntity of(String code, String name) {
        return new ContinentJpaEntity(code, name);
    }

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
