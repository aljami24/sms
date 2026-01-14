package com.smha.sms.academic.model.repository;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Section;
import com.smha.sms.academic.model.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomVersionSectionRepository extends JpaRepository<ClassroomVersionSection, Long> {

    // class অনুযায়ী version/section fetch করার জন্য custom query
    List<ClassroomVersionSection> findByClassRoomId(Long classRoomId);

    // Find by ClassRoom, Version, and Section (section can be null for classes 6-8)
    @Query("SELECT cvs FROM ClassroomVersionSection cvs WHERE cvs.classRoom.id = :classRoomId AND cvs.version.id = :versionId AND (:sectionId IS NULL OR cvs.section.id = :sectionId)")
    Optional<ClassroomVersionSection> findByClassRoomIdAndVersionIdAndSectionId(
            @Param("classRoomId") Long classRoomId,
            @Param("versionId") Long versionId,
            @Param("sectionId") Long sectionId
    );

    // class অনুযায়ী distinct versions fetch করার জন্য
    @Query("SELECT DISTINCT cvs.version FROM ClassroomVersionSection cvs WHERE cvs.classRoom.id = :classRoomId")
    List<Version> findDistinctVersionsByClassRoomId(@Param("classRoomId") Long classRoomId);

    // class অনুযায়ী distinct sections fetch করার জন্য (nullable sections আসবে না)
    @Query("SELECT DISTINCT cvs.section FROM ClassroomVersionSection cvs WHERE cvs.classRoom.id = :classRoomId AND cvs.section IS NOT NULL")
    List<Section> findDistinctSectionsByClassRoomId(@Param("classRoomId") Long classRoomId);

    // class + version অনুযায়ী distinct sections fetch করার জন্য
    @Query("SELECT DISTINCT cvs.section FROM ClassroomVersionSection cvs WHERE cvs.classRoom.id = :classRoomId AND cvs.version.id = :versionId AND cvs.section IS NOT NULL")
    List<Section> findDistinctSectionsByClassRoomIdAndVersionId(@Param("classRoomId") Long classRoomId, @Param("versionId") Long versionId);
}
