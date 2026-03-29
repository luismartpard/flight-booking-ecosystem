CREATE TABLE IF NOT EXISTS continents
(
    id_continent BIGSERIAL PRIMARY KEY,
    code         VARCHAR(2) UNIQUE NOT NULL,
    name         VARCHAR(50) NOT NULL,
    created_at   TIMESTAMPTZ       NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ
);