package com.hospital.management.apimodel.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffCreationModel {
    @NotBlank(message = "Name is required.")
    private String name;

    @Past(message = "Date registered cannot be in the future.")
    @NotNull(message = "Date registered is required.")
    private LocalDate registrationDate;
}

