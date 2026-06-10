package com.coditas.electricityservicemanagement.platform.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateHeadInvitationRequest {
    private String stateId;
    private String email;
}
