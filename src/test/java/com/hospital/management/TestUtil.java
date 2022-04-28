package com.hospital.management;

import com.hospital.management.apimodel.request.StaffCreationModel;
import com.hospital.management.entities.Patient;
import com.hospital.management.entities.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TestUtil {
    public static StaffCreationModel createStaffModel() {
        return StaffCreationModel.builder()
                .name("Test")
                .registrationDate(LocalDate.of(2000, 4, 4))
                .build();
    }

    public static Staff createStaff() {
        return Staff.builder()
                .name("Test")
                .registrationDate(LocalDate.of(2000, 4, 4))
                .uuid("SF|123456")
                .build();
    }

    public static Page<Patient> getPatients() {
        Patient patient = generatePatient();
        List<Patient> employees = Collections.singletonList(patient);
        return new PageImpl(employees);
    }

    public static Patient generatePatient() {
        Patient patient = Patient.builder()
                .name("Tester")
                .age(10)
                .lastVisitDate(LocalDate.of(2000, 4, 4))
                .build();

        patient.setId(1L);
        return patient;
    }
}
