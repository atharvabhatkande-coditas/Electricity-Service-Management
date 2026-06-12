package com.coditas.electricityservicemanagement.tenant.service;

import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.platform.dto.response.InvitationResponse;
import com.coditas.electricityservicemanagement.platform.entity.Invitation;
import com.coditas.electricityservicemanagement.tenant.dto.request.TenantInvitationRequest;
import com.coditas.electricityservicemanagement.tenant.entity.TenantInvitation;
import com.coditas.electricityservicemanagement.tenant.entity.TenantUsers;
import com.coditas.electricityservicemanagement.tenant.enums.RoleType;
import com.coditas.electricityservicemanagement.tenant.repository.TenantInvitationRepository;
import com.coditas.electricityservicemanagement.tenant.repository.TenantUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.*;
import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.EMAIL_SENT;
import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.INVITATION_LINK_PLATFORM;

@Service
@RequiredArgsConstructor
public class TenantInvitationService {
    private final TenantInvitationRepository tenantInvitationRepository;
    private final JavaMailSender javaMailSender;
    private final TenantUserRepository tenantUserRepository;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Transactional
    public InvitationResponse sendInvitation(TenantInvitationRequest invitationRequest, TenantUsers tenantUser) {
        TenantUsers invitedBy=tenantUserRepository.findByUsername(tenantUser.getUsername())
                .orElseThrow(()->new AuthenticationException(USER_NOT_FOUND));

        RoleType highestRole=RoleType.getHighestPriorityRoleTenant(invitedBy.getRoles());

        if(!checkInviteAuthority(highestRole,invitationRequest.getRole())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        UUID code=UUID.randomUUID();
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject("Invitation Link");
        simpleMailMessage.setTo(invitationRequest.getEmail());
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setText(String.format(EMAIL_TEXT,INVITATION_LINK_TENANT,code));
        javaMailSender.send(simpleMailMessage);

        TenantInvitation tenantInvitation=TenantInvitation.builder()
                .email(invitationRequest.getEmail())
                .code(code.toString())
                .invitedBy(invitedBy)
                .expireAt(Instant.now().plusSeconds(300))
                .role(invitationRequest.getRole())
                .build();

        tenantInvitationRepository.save(tenantInvitation);
        return InvitationResponse.builder()
                .message(EMAIL_SENT)
                .build();
    }

    private boolean checkInviteAuthority(RoleType highestRole,RoleType invitedRole){
        return highestRole.getValue() < invitedRole.getValue();
    }
}
