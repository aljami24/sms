package com.smha.sms.user.model.repository;

import com.smha.sms.user.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
