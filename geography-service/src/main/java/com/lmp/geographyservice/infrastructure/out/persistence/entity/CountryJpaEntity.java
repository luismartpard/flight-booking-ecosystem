package com.lmp.geographyservice.infrastructure.out.persistence.entity;

import com.lmp.geographyservice.domain.enums.TravelStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "countries", schema = "geography")
public class CountryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_country")
    private Long id;

    @NonNull
    @NotBlank
    @Size(min = 2, max = 2)
    @Column(name = "iso2", nullable = false, unique = true, length = 2)
    private String iso2;

    @NonNull
    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "iso3", nullable = false, unique = true, length = 3)
    private String iso3;

    @NonNull
    @NotNull
    @Min(1)
    @Max(999)
    @Column(name = "iso_numeric", nullable = false, unique = true)
    private Integer isoNumeric;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "default_name", nullable = false, unique = true, length = 100)
    private String defaultName;

    @Column(name = "phone_code", length = 10)
    private String phoneCode;

    @NonNull
    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "currency_code", nullable = false, unique = true, length = 3)
    private String currencyCode;

    @NonNull
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "travel_status", nullable = false, unique = true, length = 20)
    private TravelStatus  travelStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_continent")
    private ContinentJpaEntity continent;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt =  OffsetDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (Hibernate.getClass(this)
                != Hibernate.getClass(o)) return false;
        CountryJpaEntity that = (CountryJpaEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return org.hibernate.Hibernate.getClass(this).hashCode() ;
    }

}
