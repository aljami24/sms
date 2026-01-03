package com.smatik.sms.academic.model.repository;

import com.smatik.sms.academic.model.entity.ClassroomVersionSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassroomVersionSectionRepository extends JpaRepository<ClassroomVersionSection, Long> {

    Optional<ClassroomVersionSection> findByClassRoomIdAndVersionIdAndSectionId(
            Long classId, Long versionId, Long sectionId);
}
