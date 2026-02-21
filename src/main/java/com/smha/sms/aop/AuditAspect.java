package com.smha.sms.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smha.sms.annotation.Audit;
import com.smha.sms.common.entity.AuditLog;
import com.smha.sms.common.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;

    @AfterReturning(pointcut = "@annotation(audit)", returning = "result")
    public void auditLog(Audit audit, Object result) {

        AuditLog<Object> auditLog = new AuditLog();
        auditLog.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        // Convert object to JSON string for database storage
        try {
            String jsonData = objectMapper.writeValueAsString(result);
            auditLog.setActionData(jsonData);
        } catch (Exception e) {
            // Log error but don't fail the entire operation
            e.printStackTrace();
            auditLog.setActionData(null);
        }

        auditLog.setActionType(audit.permission());

        // Save to DB
        auditLogRepository.save(auditLog);

    }

}
