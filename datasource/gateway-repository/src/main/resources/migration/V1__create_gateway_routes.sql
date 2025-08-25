-- Flyway migration: create routes table for Spring Cloud Gateway and seed example routes

IF OBJECT_ID(N'dbo.gateway_routes', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.gateway_routes (
        id              nvarchar(200)  NOT NULL,
        uri             nvarchar(1000) NOT NULL,
        predicates_text nvarchar(max)  NULL,
        filters_text    nvarchar(max)  NULL,
        route_order     int            NULL,
        metadata        nvarchar(max)  NULL,
        enabled         bit            NOT NULL CONSTRAINT DF_gateway_routes_enabled DEFAULT (1),
        created_at      datetime2      NOT NULL CONSTRAINT DF_gateway_routes_created_at DEFAULT (SYSDATETIME()),
        CONSTRAINT PK_gateway_routes PRIMARY KEY CLUSTERED (id ASC)
    );
END
GO

-- Seed example routes (idempotent)
IF NOT EXISTS (SELECT 1 FROM dbo.gateway_routes WHERE id = 'eureka-server')
BEGIN
    INSERT INTO dbo.gateway_routes (id, uri, predicates_text, filters_text, route_order, metadata, enabled)
    VALUES ('eureka-server', 'http://localhost:8761', '["Path=/eureka/**"]', '[]', 0, NULL, 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM dbo.gateway_routes WHERE id = 'welcome-service')
BEGIN
    INSERT INTO dbo.gateway_routes (id, uri, predicates_text, filters_text, route_order, metadata, enabled)
    VALUES ('welcome-service', 'lb://welcome-service', '["Path=/api/**"]', '[]', 0, NULL, 1);
END
GO
