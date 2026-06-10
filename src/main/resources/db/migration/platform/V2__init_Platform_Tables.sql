
ALTER TABLE tenant_billing
    ADD COLUMN tenant_id VARCHAR(255);

ALTER TABLE tenant_billing
    ADD COLUMN billing_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE tenant_billing
    ADD CONSTRAINT FK_TENANT_BILLING_ON_TENANT FOREIGN KEY (tenant_id) REFERENCES tenant (id);

