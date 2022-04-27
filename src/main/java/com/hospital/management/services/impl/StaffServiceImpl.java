package com.hospital.management.services.impl;

import com.hospital.management.apimodel.StaffCreationModel;
import com.hospital.management.apimodel.StaffCreationResponse;
import com.hospital.management.entities.Staff;
import com.hospital.management.repositories.StaffRepository;
import com.hospital.management.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    public StaffCreationResponse addStaff(StaffCreationModel staffCreationModel) {
        String uuid = UUIDGeneration.generateUUID();

        while (staffRepository.existsByUuidIgnoreCase(uuid)){
            uuid = UUIDGeneration.generateUUID();
        }

        Staff staff = Staff.builder()
                .name(staffCreationModel.getName())
                .uuid(uuid)
                .registrationDate(staffCreationModel.getRegistrationDate())
                .build();

        Staff savedStaff = staffRepository.save(staff);

        return getStaffCreationResponse(savedStaff);

    }

    private StaffCreationResponse getStaffCreationResponse(Staff savedStaff) {
        return StaffCreationResponse.builder()
                .UUID(savedStaff.getUuid())
                .name(savedStaff.getName())
                .registrationDate(savedStaff.getRegistrationDate())
                .build();
    }
}
