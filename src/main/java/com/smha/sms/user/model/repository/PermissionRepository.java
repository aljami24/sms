package com.smha.sms.user.model.repository;

import com.smha.sms.user.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
