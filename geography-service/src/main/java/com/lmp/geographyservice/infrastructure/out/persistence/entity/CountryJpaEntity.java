package com.lmp.geographyservice.infrastructure.out.persistence.entity;

import com.lmp.geographyservice.domain.enums.TravelStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "countries", schema = "geography")
public class CountryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_country")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(name = "iso2", nullable = false, unique = true, length = 2)
    private String iso2;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "iso3", nullable = false, unique = true, length = 3)
    private String iso3;

    @NotNull
    @Min(1)
    @Max(999)
    @Column(name = "iso_numeric", nullable = false, unique = true)
    private Integer isoNumeric;

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "default_name", nullable = false, unique = true, length = 100)
    private String defaultName;

    @Column(name = "phone_code", length = 10)
    private String phoneCode;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "currency_code", nullable = false, unique = true, length = 3)
    private String currencyCode;

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

    private CountryJpaEntity(String iso2, String iso3, Integer isoNumeric, String defaultName, String phoneCode, String currencyCode, TravelStatus travelStatus) {
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.isoNumeric = isoNumeric;
        this.defaultName = defaultName;
        this.phoneCode = phoneCode;
        this.currencyCode = currencyCode;
        this.travelStatus = travelStatus;
    }

    public static CountryJpaEntity of(String iso2, String iso3, Integer isoNumeric, String defaultName, String phoneCode, String currencyCode, TravelStatus travelStatus) {
        return new CountryJpaEntity(iso2, iso3, isoNumeric, defaultName, phoneCode, currencyCode, travelStatus);
    }

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
