package com.hospital.management.services.impl;

import com.hospital.management.apimodel.response.PatientResponse;
import com.hospital.management.entities.Patient;
import com.hospital.management.enums.ResponseCode;
import com.hospital.management.exceptions.BadRequestException;
import com.hospital.management.repositories.PatientRepository;
import com.hospital.management.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public void deletePatients(LocalDate startDate, LocalDate endDate) {
        Pageable pageRequest = PageRequest.of(0, 10);
        boolean hasNextPage = true;

        while (hasNextPage) {
            Page<Patient> patients = patientRepository.findByLastVisitDateBetween(startDate, endDate, pageRequest);
            patients.getContent().forEach(patient -> patientRepository.deleteById(patient.getId()));

            hasNextPage = patients.hasNext();
            pageRequest = pageRequest.next();
        }
    }

    private PatientResponse buildPatientResponse(Patient patient) {
        return PatientResponse.builder()
                .name(patient.getName())
                .age(patient.getAge())
                .lastVisitDate(patient.getLastVisitDate())
                .build();
    }
}
