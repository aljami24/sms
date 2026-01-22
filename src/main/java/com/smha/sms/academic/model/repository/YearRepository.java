package com.smha.sms.academic.model.repository;

import com.smha.sms.academic.model.entity.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YearRepository extends JpaRepository<Year, Long> {
    Optional<Year> findByName(String name);
}
