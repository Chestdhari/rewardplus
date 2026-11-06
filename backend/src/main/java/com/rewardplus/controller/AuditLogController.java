package com.rewardplus.controller;

import com.rewardplus.dto.response.AuditLogResponse;
import com.rewardplus.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AuditLogResponse get(
            @PathVariable Long id) {

        return auditLogService.get(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AuditLogResponse> getAll() {

        return auditLogService.getAll();
    }

}