package com.smha.sms.student.model.repository;

import com.smha.sms.student.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT DISTINCT s FROM Student s " +
            "JOIN s.studentAcademicRecords sar " +
            "WHERE sar.roll = :roll")
    Optional<Student> findByRoll(@Param("roll") Integer roll);

    Optional<Student> findByRegistration(Integer registration);

    boolean existsByRegistration(Integer registration);

    @Query("SELECT s FROM Student s " +
            "JOIN s.studentAcademicRecords sar " +
            "JOIN sar.classroomVersionSection cvs " +
            "LEFT JOIN cvs.section sec " +
            "LEFT JOIN cvs.version ver " +
            "LEFT JOIN sar.year y " +
            "WHERE (:rollNumber IS NULL OR sar.roll = :rollNumber) " +
            "AND (:registrationNumber IS NULL OR s.registration = :registrationNumber) " +
            "AND (:classRoomId IS NULL OR cvs.classRoom.id = :classRoomId) " +
            "AND (:section IS NULL OR sec.name = :section) " +
            "AND (:version IS NULL OR ver.name = :version) " +
            "AND (:yearId IS NULL OR y.id = :yearId)")
    Page<Student> filterStudents(
            @Param("rollNumber") Integer rollNumber,
            @Param("registrationNumber") Integer registrationNumber,
            @Param("classRoomId") Long classRoomId,
            @Param("section") String section,
            @Param("version") String version,
            @Param("yearId") Long yearId,
            Pageable pageable
    );

    @Query("SELECT COALESCE(MAX(sar.roll), 0) FROM StudentAcademicRecord sar " +
            "JOIN sar.classroomVersionSection cvs " +
            "WHERE cvs.classRoom.id = :classRoomId " +
            "AND cvs.version.id = :versionId")
    Integer findMaxRollByClassRoomAndVersion(@Param("classRoomId") Long classRoomId,
                                              @Param("versionId") Long versionId);

}