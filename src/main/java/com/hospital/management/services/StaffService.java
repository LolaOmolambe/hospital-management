package com.hospital.management.services;

import com.hospital.management.apimodel.StaffCreationModel;
import com.hospital.management.apimodel.StaffCreationResponse;

public interface StaffService {
    StaffCreationResponse addStaff(StaffCreationModel staffCreationModel);
}
