package com.smha.sms.academic.model.repository;


import com.smha.sms.academic.model.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VersionRepository extends JpaRepository<Version, Long> {
    Optional<Version> findByName(String name);
}
