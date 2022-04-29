package com.hospital.management.controllers;

import com.hospital.management.annotations.GenericSuccessResponse;
import com.hospital.management.annotations.ValidStaffUUID;
import com.hospital.management.apimodel.response.PatientResponse;
import com.hospital.management.entities.Patient;
import com.hospital.management.services.PatientService;
import com.hospital.management.services.impl.CSVExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@GenericSuccessResponse
public class PatientController {

    private final PatientService patientService;
    private final CSVExportService csvExportService;

    @GetMapping
    @ValidStaffUUID
    public List<PatientResponse> getPatients(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return patientService.getPatients(pageable);
    }

    @GetMapping(path = "/{id}", produces = "text/csv")
    @ValidStaffUUID
    public void getPatient(@PathVariable(value = "id") Long id,
                           HttpServletResponse servletResponse) {
        try{
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=\"patient.csv\"");
            Patient patient = patientService.getPatient(id);
            csvExportService.writePatientToCsv(patient, servletResponse.getWriter());
        } catch (Exception e) {
            log.error("Error while writing csv", e);
        }
    }

    @DeleteMapping
    @ValidStaffUUID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatients(@Valid @NotNull @RequestParam("startDate")
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                               @Valid @NotNull @RequestParam("endDate")
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        patientService.deletePatients(startDate, endDate);
    }

}
