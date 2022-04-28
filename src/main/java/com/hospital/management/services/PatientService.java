package com.hospital.management.services;

import com.hospital.management.apimodel.response.PatientResponse;
import com.hospital.management.entities.Patient;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface PatientService {
    List<PatientResponse> getPatients(Pageable pageable);

    Patient getPatient(Long id);

    void deletePatients(LocalDate startDate, LocalDate endDate);
}
