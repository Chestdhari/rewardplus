package com.rewardplus.service.impl;

import com.rewardplus.dto.response.AuditLogResponse;
import com.rewardplus.entity.AuditLog;
import com.rewardplus.exception.AuditLogNotFoundException;
import com.rewardplus.repository.AuditLogRepository;
import com.rewardplus.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public AuditLogResponse get(Long id) {

        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() ->
                        new AuditLogNotFoundException(
                                "Audit Log not found"));

        return map(auditLog);
    }

    @Override
    public List<AuditLogResponse> getAll() {

        return auditLogRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public void log(String username, String action, String entityName, Long entityId, String description) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUsername(username != null ? username : "SYSTEM");
        auditLog.setAction(action);
        auditLog.setEntityName(entityName);
        auditLog.setEntityId(entityId != null ? entityId : 0L);
        auditLog.setDescription(description);
        auditLogRepository.save(auditLog);
    }

    private AuditLogResponse map(AuditLog auditLog) {

        AuditLogResponse response = new AuditLogResponse();

        response.setId(auditLog.getId());
        response.setUsername(auditLog.getUsername());
        response.setAction(auditLog.getAction());
        response.setEntityName(auditLog.getEntityName());
        response.setEntityId(auditLog.getEntityId());
        response.setDescription(auditLog.getDescription());
        response.setCreatedAt(auditLog.getCreatedAt());

        return response;
    }

}