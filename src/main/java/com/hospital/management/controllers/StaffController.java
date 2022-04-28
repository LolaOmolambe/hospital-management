package com.hospital.management.controllers;

import com.hospital.management.annotations.GenericSuccessResponse;
import com.hospital.management.annotations.ValidStaffUUID;
import com.hospital.management.apimodel.request.StaffCreationModel;
import com.hospital.management.apimodel.response.StaffCreationResponse;
import com.hospital.management.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/staff")
@GenericSuccessResponse
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StaffCreationResponse addStaff(@Valid @RequestBody StaffCreationModel staffCreationModel) {
        return staffService.addStaff(staffCreationModel);
    }

    @PutMapping("/{uuid}")
    @ValidStaffUUID
    public StaffCreationResponse updateStaffProfile(@PathVariable(value = "uuid") String uuid,
                                                    @Validated @RequestBody StaffCreationModel staffCreationModel) {
        return staffService.updateStaff(staffCreationModel, uuid);
    }
}
