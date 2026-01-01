package com.smatik.sms.academic_management.model.repository;

import com.smatik.sms.academic_management.model.entity.ClassRoom;
import com.smatik.sms.academic_management.model.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
