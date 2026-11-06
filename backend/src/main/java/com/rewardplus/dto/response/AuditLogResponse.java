package com.rewardplus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditLogResponse {

    private Long id;

    private String username;

    private String action;

    private String entityName;

    private Long entityId;

    private String description;

    private LocalDateTime createdAt;

}
