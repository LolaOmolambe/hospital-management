package com.hospital.management.services.impl;

import com.hospital.management.entities.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CSVExportService {

    public void writePatientToCsv(Patient patient, Writer writer) {
        List<Patient> patientList = Collections.singletonList(patient);
        writeToCsv(writer, patientList);
    }

    private void writeToCsv(Writer writer, List<Patient> patients) {
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Patient patient : patients) {
                csvPrinter.printRecord(patient.getId(), patient.getName(), patient.getAge(), patient.getLastVisitDate());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }

}
