package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.common.dto.request.InvitationRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.InvitationResponse;
import com.coditas.electricityservicemanagement.platform.entity.Invitation;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.repository.PocInvitationRepository;
import com.coditas.electricityservicemanagement.platform.repository.PlatformUserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.*;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private final PocInvitationRepository pocInvitationRepository;
    private final PlatformUserRepository platformUserRepository;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Transactional
    public InvitationResponse sendInvitationToTenantPoc(@Valid InvitationRequest invitationRequest, PlatformUsers platformUser) {

        PlatformUsers invitedBy=platformUserRepository.findByUsername(platformUser.getUsername())
                .orElseThrow(()->new AuthenticationException(USER_NOT_FOUND));

        UUID code=UUID.randomUUID();
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject("Invitation Link");
        simpleMailMessage.setTo(invitationRequest.getEmail());
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setText(String.format(EMAIL_TEXT,INVITATION_LINK,code));

        Invitation invitation = Invitation.builder()
                .email(invitationRequest.getEmail())
                .tenantId(invitationRequest.getTenantId())
                .code(code.toString())
                .expireAt(Instant.now().plusSeconds(3000))
                .invitedBy(invitedBy)
                .role(invitationRequest.getRole())
                .build();

        javaMailSender.send(simpleMailMessage);
        pocInvitationRepository.save(invitation);
        return InvitationResponse.builder()
                .message(EMAIL_SENT)
                .build();

    }


    private String sendMail(String recipient){

    }


}
