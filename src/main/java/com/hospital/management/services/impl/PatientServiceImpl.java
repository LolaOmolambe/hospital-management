package com.hospital.management.services.impl;

import com.hospital.management.apimodel.response.PatientResponse;
import com.hospital.management.entities.Patient;
import com.hospital.management.enums.ResponseCode;
import com.hospital.management.exceptions.BadRequestException;
import com.hospital.management.repositories.PatientRepository;
import com.hospital.management.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<PatientResponse> getPatients(Pageable pageable) {

        Page<Patient> patientPage = patientRepository.findAllByAgeGreaterThanEqual(2, pageable);
        List<Patient> patientList = patientPage.getContent();

        return patientList.stream()
                .map(this::buildPatientResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Patient getPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ResponseCode.INVALID_PATIENT_ID));
    }

    private PatientResponse buildPatientResponse(Patient patient) {
        return PatientResponse.builder()
                .name(patient.getName())
                .age(patient.getAge())
                .lastVisitDate(patient.getLastVisitDate())
                .build();
    }
}
