package com.hospital.management.apimodel.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffCreationResponse {
    private String name;

    private LocalDate registrationDate;

    private String UUID;
}

