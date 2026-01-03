package com.smatik.sms.academic.model.repository;

import com.smatik.sms.academic_management.model.entity.ClassRoom;
import com.smatik.sms.academic_management.model.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Long> {
}
