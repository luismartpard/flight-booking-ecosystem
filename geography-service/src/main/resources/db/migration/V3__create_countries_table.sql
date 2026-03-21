CREATE TABLE IF NOT EXISTS countries
(
    id_country  BIGSERIAL PRIMARY KEY ,
    iso2 VARCHAR(2) NOT NULL UNIQUE ,
    iso3 VARCHAR(3) NOT NULL UNIQUE ,
    default_name VARCHAR(100) NOT NULL UNIQUE,
    iso_numeric INTEGER NOT NULL UNIQUE ,
    phone_code VARCHAR(10),
    currency_code VARCHAR(3) NOT NULL,
    id_continent BIGINT NOT NULL,
    travel_status VARCHAR(20) NOT NULL CHECK (
        travel_status IN ('AVAILABLE', 'SUSPENDED', 'RESTRICTED')
        ),
    created_at   TIMESTAMPTZ       NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ
);