package com.hospital.management.services;

import com.hospital.management.apimodel.response.PatientResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    List<PatientResponse> getPatients(Pageable pageable);
}
