package com.smatik.sms.academic.model.repository;


import com.smatik.sms.academic.model.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
