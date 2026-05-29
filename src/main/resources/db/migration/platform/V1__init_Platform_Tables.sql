CREATE TABLE area
(
    id        VARCHAR(255) NOT NULL,
    area_name VARCHAR(255),
    city_id   VARCHAR(255),
    pincode   VARCHAR(255),
    CONSTRAINT pk_area PRIMARY KEY (id)
);

CREATE TABLE available_slots
(
    id                  VARCHAR(255) NOT NULL,
    user_id             VARCHAR(255),
    slot_id             VARCHAR(255),
    available_date      TIMESTAMP WITHOUT TIME ZONE,
    availability_status VARCHAR(255),
    CONSTRAINT pk_available_slots PRIMARY KEY (id)
);

CREATE TABLE city
(
    id           VARCHAR(255) NOT NULL,
    city_name    VARCHAR(255),
    district_id  VARCHAR(255),
    city_head_id VARCHAR(255),
    CONSTRAINT pk_city PRIMARY KEY (id)
);

CREATE TABLE district
(
    id               VARCHAR(255) NOT NULL,
    district_name    VARCHAR(255),
    state_id         VARCHAR(255),
    district_head_id VARCHAR(255),
    CONSTRAINT pk_district PRIMARY KEY (id)
);

CREATE TABLE platform_users
(
    id         VARCHAR(255) NOT NULL,
    username   VARCHAR(255),
    password   VARCHAR(255),
    is_enabled BOOLEAN,
    tenant_id  VARCHAR(255),
    CONSTRAINT pk_platform_users PRIMARY KEY (id)
);

CREATE TABLE platform_users_roles
(
    platform_users_id VARCHAR(255) NOT NULL,
    roles             VARCHAR(255)
);

CREATE TABLE portfolio
(
    id            VARCHAR(255) NOT NULL,
    tenant_id     VARCHAR(255),
    sales_user_id VARCHAR(255),
    assigned_by   VARCHAR(255),
    is_active     BOOLEAN,
    CONSTRAINT pk_portfolio PRIMARY KEY (id)
);

CREATE TABLE service_area
(
    id           VARCHAR(255) NOT NULL,
    user_id      VARCHAR(255),
    area_id      VARCHAR(255),
    service_type VARCHAR(255),
    CONSTRAINT pk_service_area PRIMARY KEY (id)
);

CREATE TABLE slot_booking
(
    id           VARCHAR(255) NOT NULL,
    user_id      VARCHAR(255),
    slot_id      VARCHAR(255),
    booking_date TIMESTAMP WITHOUT TIME ZONE,
    tenant_id    VARCHAR(255),
    CONSTRAINT pk_slot_booking PRIMARY KEY (id)
);

CREATE TABLE state
(
    id            VARCHAR(255) NOT NULL,
    state_name    VARCHAR(255),
    state_head_id VARCHAR(255),
    CONSTRAINT pk_state PRIMARY KEY (id)
);

CREATE TABLE tenant
(
    id            VARCHAR(255) NOT NULL,
    provider_name VARCHAR(255),
    schema_name   VARCHAR(255),
    is_active     BOOLEAN,
    onboarded_by  VARCHAR(255),
    CONSTRAINT pk_tenant PRIMARY KEY (id)
);

CREATE TABLE tenant_billing
(
    id             VARCHAR(255) NOT NULL,
    amount         DOUBLE PRECISION,
    due_date       TIMESTAMP WITHOUT TIME ZONE,
    billing_status VARCHAR(255),
    CONSTRAINT pk_tenant_billing PRIMARY KEY (id)
);

CREATE TABLE time_slot
(
    id         VARCHAR(255) NOT NULL,
    start_time TIMESTAMP WITHOUT TIME ZONE,
    end_time   TIMESTAMP WITHOUT TIME ZONE,
    slot_name  VARCHAR(255),
    CONSTRAINT pk_time_slot PRIMARY KEY (id)
);

ALTER TABLE area
    ADD CONSTRAINT FK_AREA_ON_CITY FOREIGN KEY (city_id) REFERENCES city (id);

ALTER TABLE available_slots
    ADD CONSTRAINT FK_AVAILABLE_SLOTS_ON_SLOT FOREIGN KEY (slot_id) REFERENCES time_slot (id);

ALTER TABLE available_slots
    ADD CONSTRAINT FK_AVAILABLE_SLOTS_ON_USER FOREIGN KEY (user_id) REFERENCES platform_users (id);

ALTER TABLE city
    ADD CONSTRAINT FK_CITY_ON_CITY_HEAD FOREIGN KEY (city_head_id) REFERENCES platform_users (id);

ALTER TABLE city
    ADD CONSTRAINT FK_CITY_ON_DISTRICT FOREIGN KEY (district_id) REFERENCES district (id);

ALTER TABLE district
    ADD CONSTRAINT FK_DISTRICT_ON_DISTRICT_HEAD FOREIGN KEY (district_head_id) REFERENCES platform_users (id);

ALTER TABLE district
    ADD CONSTRAINT FK_DISTRICT_ON_STATE FOREIGN KEY (state_id) REFERENCES state (id);

ALTER TABLE platform_users
    ADD CONSTRAINT FK_PLATFORM_USERS_ON_TENANT FOREIGN KEY (tenant_id) REFERENCES tenant (id);

ALTER TABLE portfolio
    ADD CONSTRAINT FK_PORTFOLIO_ON_ASSIGNED_BY FOREIGN KEY (assigned_by) REFERENCES platform_users (id);

ALTER TABLE portfolio
    ADD CONSTRAINT FK_PORTFOLIO_ON_SALES_USER FOREIGN KEY (sales_user_id) REFERENCES platform_users (id);

ALTER TABLE portfolio
    ADD CONSTRAINT FK_PORTFOLIO_ON_TENANT FOREIGN KEY (tenant_id) REFERENCES tenant (id);

ALTER TABLE service_area
    ADD CONSTRAINT FK_SERVICE_AREA_ON_AREA FOREIGN KEY (area_id) REFERENCES area (id);

ALTER TABLE service_area
    ADD CONSTRAINT FK_SERVICE_AREA_ON_USER FOREIGN KEY (user_id) REFERENCES platform_users (id);

ALTER TABLE slot_booking
    ADD CONSTRAINT FK_SLOT_BOOKING_ON_SLOT FOREIGN KEY (slot_id) REFERENCES time_slot (id);

ALTER TABLE slot_booking
    ADD CONSTRAINT FK_SLOT_BOOKING_ON_TENANT FOREIGN KEY (tenant_id) REFERENCES tenant (id);

ALTER TABLE slot_booking
    ADD CONSTRAINT FK_SLOT_BOOKING_ON_USER FOREIGN KEY (user_id) REFERENCES platform_users (id);

ALTER TABLE state
    ADD CONSTRAINT FK_STATE_ON_STATE_HEAD FOREIGN KEY (state_head_id) REFERENCES platform_users (id);

ALTER TABLE tenant
    ADD CONSTRAINT FK_TENANT_ON_ONBOARDED_BY FOREIGN KEY (onboarded_by) REFERENCES platform_users (id);

ALTER TABLE platform_users_roles
    ADD CONSTRAINT fk_platformusers_roles_on_platform_users FOREIGN KEY (platform_users_id) REFERENCES platform_users (id);