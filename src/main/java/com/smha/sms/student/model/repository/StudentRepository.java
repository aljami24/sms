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
    boolean existsByRoll(Integer roll);

    Optional<Student> findByRoll(int roll);

    @Query("SELECT s FROM Student s " +
            "JOIN s.studentAcademicRecords sar " +
            "JOIN sar.classroomVersionSection cvs " +
            "LEFT JOIN cvs.section sec " +
            "LEFT JOIN cvs.version ver " +
            "WHERE (:rollNumber IS NULL OR s.roll = :rollNumber) " +
            "AND (:classRoomId IS NULL OR cvs.classRoom.id = :classRoomId) " +
            "AND (:section IS NULL OR sec.name = :section) " +
            "AND (:version IS NULL OR ver.name = :version)")
    Page<Student> filterStudents(
            @Param("rollNumber") Integer rollNumber,
            @Param("classRoomId") Long classRoomId,
            @Param("section") String section,
            @Param("version") String version,
            Pageable pageable
    );

}