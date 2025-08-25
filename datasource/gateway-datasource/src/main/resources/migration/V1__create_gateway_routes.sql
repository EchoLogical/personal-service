-- Opsional: jika ingin default UUID otomatis
-- membutuhkan ekstensi uuid-ossp
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS gateway_routes (
    uuid           uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    service_id     varchar(1000) NOT NULL UNIQUE,
    uri            varchar(1000) NOT NULL,
    predicates_text text,
    filters_text    text,
    route_order     integer,
    metadata        text,
    enabled         boolean NOT NULL DEFAULT TRUE,
    created_at      timestamp WITHOUT TIME ZONE NOT NULL DEFAULT now()
    );

-- Index opsional untuk mempercepat pencarian route aktif
CREATE INDEX IF NOT EXISTS idx_gateway_routes_enabled ON gateway_routes (enabled);

-- Index opsional jika sering melakukan sort/filter berdasarkan route_order
CREATE INDEX IF NOT EXISTS idx_gateway_routes_route_order ON gateway_routes (route_order);