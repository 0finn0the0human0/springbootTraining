-- ============================================================
-- SUPPLIERS
-- Represents companies that supply products through the catalog.
-- A supplier must be active to have product managers assigned.
-- ============================================================

CREATE TABLE IF NOT EXISTS SUPPLIERS (
    SUPP_ID      UUID         PRIMARY KEY DEFAULT gen_random_uuid(), -- DB generates UUID, never rely on app to supply it
    SUPP_NAME    VARCHAR(255) NOT NULL UNIQUE,
    SUPP_ADDRESS VARCHAR(255) NOT NULL,
    STATUS       CHAR(1)      NOT NULL DEFAULT 'A' CHECK (STATUS IN ('A', 'I', 'S')),
    CREATED_AT   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    UPDATED_AT   TIMESTAMPTZ  NOT NULL DEFAULT NOW() -- kept current by trigger
    );



-- ============================================================
-- PRODUCT MANAGERS
-- Employees of a supplier who own and manage product listings.
-- A PM belongs to exactly one supplier (SUPP_ID NOT NULL).
-- ============================================================

CREATE TABLE IF NOT EXISTS PRODUCT_MANAGERS (
    PM_ID      UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    SUPP_ID    UUID         NOT NULL REFERENCES SUPPLIERS(SUPP_ID) ON DELETE RESTRICT,
    PM_NAME    VARCHAR(100) NOT NULL,                   -- bumped from 36 — full names can exceed that
    PM_EMAIL   VARCHAR(320) NOT NULL UNIQUE,            -- RFC 5321 max, UNIQUE enforces one account per email
    IS_ACTIVE  BOOLEAN      NOT NULL DEFAULT TRUE,      -- simpler than CHAR(1) status for a binary state
    CREATED_AT TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    UPDATED_AT TIMESTAMPTZ  NOT NULL DEFAULT NOW()
    );

-- Index the FK — every join from products/credentials to PM goes through SUPP_ID
CREATE INDEX idx_pm_supp_id ON PRODUCT_MANAGERS(SUPP_ID);

-- ============================================================
-- PRODUCTS
-- A product is owned by the PM who manages it.
-- Supplier is derived through PM → SUPP_ID, not stored here directly.
-- ============================================================

CREATE TABLE IF NOT EXISTS PRODUCTS (
    PROD_ID      UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    PM_ID        UUID          NOT NULL REFERENCES PRODUCT_MANAGERS(PM_ID) ON DELETE RESTRICT,
    SKU          VARCHAR(100)  NOT NULL UNIQUE,            -- stock keeping unit — natural business key for a product
    PROD_NAME    VARCHAR(255)  NOT NULL,
    PROD_DESC    TEXT,
    RETAIL_PRICE NUMERIC(12,2) NOT NULL CHECK (RETAIL_PRICE > 0),
    VENDOR_PRICE NUMERIC(12,2) NOT NULL CHECK (VENDOR_PRICE > 0 AND VENDOR_PRICE < RETAIL_PRICE),
    IS_PUBLISHED BOOLEAN       NOT NULL DEFAULT FALSE,     -- controls catalog visibility, default to draft
    CREATED_AT   TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    UPDATED_AT   TIMESTAMPTZ   NOT NULL DEFAULT NOW()
    );

CREATE INDEX idx_products_pm_id ON PRODUCTS(PM_ID);

-- Partial index — only indexes published products since that's the hot read path
CREATE INDEX idx_products_published ON PRODUCTS(IS_PUBLISHED) WHERE IS_PUBLISHED = TRUE;

-- ============================================================
-- CREDENTIALS
-- Auth data for PMs. Kept separate from PRODUCT_MANAGERS so
-- sensitive auth fields are isolated and permissioned differently.
-- ============================================================

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    CRED_ID            UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    PM_ID              UUID        NOT NULL UNIQUE REFERENCES PRODUCT_MANAGERS(PM_ID) ON DELETE CASCADE,
    PASS_HASH          TEXT        NOT NULL,             -- bcrypt ~60 chars, argon2 ~95 — TEXT covers both
    TOKEN_HASH         TEXT,                             -- SHA-256 hex of current session token (64 chars) — null if logged out
    REFRESH_TOKEN_HASH TEXT,                             -- hash of long-lived refresh token — null if not issued
    REFRESH_EXPIRES    TIMESTAMPTZ,                      -- when the refresh token expires — null means none issued
    STATUS             CHAR(1)     NOT NULL DEFAULT 'A' CHECK (STATUS IN ('A', 'I', 'S')),
    FAILED_ATTEMPTS    SMALLINT    NOT NULL DEFAULT 0,
    LOCKED_UNTIL       TIMESTAMPTZ,
    CREATED_AT         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UPDATED_AT         TIMESTAMPTZ NOT NULL DEFAULT NOW()
    );


-- ============================================================
-- UPDATED_AT TRIGGER
-- Automatically keeps UPDATED_AT current on any row change.
-- One function, applied to all four tables.
-- ============================================================
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.UPDATED_AT = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_suppliers_updated_at
    BEFORE UPDATE ON SUPPLIERS
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_pm_updated_at
    BEFORE UPDATE ON PRODUCT_MANAGERS
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_products_updated_at
    BEFORE UPDATE ON PRODUCTS
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER trg_credentials_updated_at
    BEFORE UPDATE ON CREDENTIALS
    FOR EACH ROW EXECUTE FUNCTION set_updated_at();