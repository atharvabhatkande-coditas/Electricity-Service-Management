package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.AlreadyExistException;
import com.coditas.electricityservicemanagement.common.dto.request.InvitationRequest;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.request.TenantOnBoardRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.TenantUpdateRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.ErrorResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.TenantResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.UpdateResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.entity.Tenant;
import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import com.coditas.electricityservicemanagement.platform.mappers.TenantMapper;
import com.coditas.electricityservicemanagement.platform.repository.TenantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

import static com.coditas.electricityservicemanagement.platform.constants.TenantConstants.*;


@Service
@RequiredArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;
    private final InvitationService invitationService;
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;
    private final TenantMapper tenantMapper;

    public SingleResponse onBoardTenant(@Valid TenantOnBoardRequest tenantRequest, PlatformUsers platformUser) {
        Tenant existingTenant = tenantRepository.findByProviderName(tenantRequest.getProviderName())
                .orElse(null);
        if (!Objects.isNull(existingTenant)) {
            throw new AlreadyExistException(TENANT + ALREADY_EXISTS);
        }
        Tenant tenant = Tenant.builder()
                .providerName(tenantRequest.getProviderName())
                .schemaName("tenant_" + tenantRequest.getProviderName())
                .isActive(true)
                .onboardedBy(platformUser)
                .build();
        tenantRepository.save(tenant);

        InvitationRequest invitationRequest = InvitationRequest.builder()
                .email(tenantRequest.getTenantPocEmail())
                .tenantId(tenant.getId())
                .role(RoleType.POC)
                .build();


        final String schemaName = "tenant_" + tenantRequest.getProviderName().toLowerCase();

        //create schema
        final String sql = String.format("CREATE SCHEMA IF NOT EXISTS %s", schemaName);
        jdbcTemplate.execute(sql);

        //run migration
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schemaName)
                .locations("classpath:db/migration/tenant")
                .baselineOnMigrate(true)
                .table("flyway_schema_history")
                .validateOnMigrate(true)
                .cleanDisabled(true)
                .load();

        flyway.migrate();

        invitationService.sendInvitation(invitationRequest, platformUser);


        return SingleResponse.builder()
                .message(TENANT_ONBOARDED)
                .build();

    }

    public List<TenantResponse> getAllTenants(PlatformUsers platformUser,int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,100);
       return tenantRepository.findAll(pageable)
               .stream()
               .map(tenant -> tenantMapper.getALlToDto(tenant,platformUser))
               .toList();
    }

    public TenantResponse getTenant(String tenantId, PlatformUsers platformUser) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException(TENANT + " " + NOT_FOUND));

        return tenantMapper.getALlToDto(tenant, platformUser);
    }

    public UpdateResponse updateTenantData(String tenantId,TenantUpdateRequest tenantUpdateRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException(TENANT + " " + NOT_FOUND));
        if(!Objects.isNull(tenantUpdateRequest.getProviderName())){
            tenant.setProviderName(tenantUpdateRequest.getProviderName());
        }
        if(!Objects.isNull(tenantUpdateRequest.getIsActive())){
            tenant.setActive(tenantUpdateRequest.getIsActive());
        }


        tenantRepository.save(tenant);
        return UpdateResponse.builder()
                .message(TENANT_UPDATED)
                .build();
    }
}
