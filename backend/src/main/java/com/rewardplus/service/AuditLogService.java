package com.rewardplus.service;

import com.rewardplus.dto.response.AuditLogResponse;

import java.util.List;

public interface AuditLogService {

    AuditLogResponse get(Long id);

    List<AuditLogResponse> getAll();

    void log(String username, String action, String entityName, Long entityId, String description);

}