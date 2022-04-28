package com.hospital.management.services;

import com.hospital.management.apimodel.request.StaffCreationModel;
import com.hospital.management.apimodel.response.StaffCreationResponse;

public interface StaffService {
    StaffCreationResponse addStaff(StaffCreationModel staffCreationModel);

    StaffCreationResponse updateStaff(StaffCreationModel staffCreationModel, String uuid);
}
