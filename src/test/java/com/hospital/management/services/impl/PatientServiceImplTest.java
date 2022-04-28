package com.hospital.management.services.impl;

import com.hospital.management.TestUtil;
import com.hospital.management.apimodel.response.PatientResponse;
import com.hospital.management.entities.Patient;
import com.hospital.management.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0, 10);
    }

    @Test
    public void getEmptyListOfPatientsSuccessfully() {
        when(patientRepository.findAllByAgeGreaterThanEqual(2, pageRequest))
                .thenReturn(Page.empty());

        List<PatientResponse> patients = patientService.getPatients(pageRequest);

        assertEquals(0, patients.size());
    }

    @Test
    public void getPatientsSuccessfully() {
        Page<Patient> pagedResponse = TestUtil.getPatients();

        when(patientRepository.findAllByAgeGreaterThanEqual(2, pageRequest))
                .thenReturn(pagedResponse);

        List<PatientResponse> patients = patientService.getPatients(pageRequest);

        assertEquals(1, patients.size());
    }

}