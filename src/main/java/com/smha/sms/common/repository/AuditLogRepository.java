package com.smha.sms.common.repository;

import com.smha.sms.common.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    @Query("SELECT a FROM AuditLog a WHERE a.actionData LIKE %:studentId% ORDER BY a.createdAt DESC")
    List<AuditLog> findByStudentId(@Param("studentId") Long studentId);
}
