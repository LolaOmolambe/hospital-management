package com.hospital.management.repositories;

import com.hospital.management.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAllByAgeGreaterThanEqual(int age, Pageable pageable);

    Page<Patient> findByLastVisitDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
