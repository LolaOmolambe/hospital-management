package com.hospital.management.services.impl;

import com.hospital.management.apimodel.request.StaffCreationModel;
import com.hospital.management.apimodel.response.StaffCreationResponse;
import com.hospital.management.entities.Staff;
import com.hospital.management.enums.ResponseCode;
import com.hospital.management.exceptions.BadRequestException;
import com.hospital.management.repositories.StaffRepository;
import com.hospital.management.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    public StaffCreationResponse addStaff(StaffCreationModel staffCreationModel) {
        String uuid = UUIDGeneration.generateUUID();

        while (staffRepository.existsByUuidIgnoreCase(uuid)) {
            uuid = UUIDGeneration.generateUUID();
        }

        Staff staff = Staff.builder()
                .name(staffCreationModel.getName())
                .uuid(uuid)
                .registrationDate(staffCreationModel.getRegistrationDate())
                .build();

        Staff savedStaff = staffRepository.save(staff);

        return generateResponse(savedStaff);

    }

    @Override
    public StaffCreationResponse updateStaff(StaffCreationModel staffCreationModel, String uuid) {
        Staff staff = staffRepository.findByUuidIgnoreCase(uuid);

        if (Objects.isNull(staff)) {
            throw new BadRequestException(ResponseCode.INVALID_STAFF_UUID);
        }

        staff.setName(staffCreationModel.getName());
        staff.setRegistrationDate(staffCreationModel.getRegistrationDate());

        Staff updatedStaff = staffRepository.save(staff);
        return generateResponse(updatedStaff);
    }

    private StaffCreationResponse generateResponse(Staff savedStaff) {
        return StaffCreationResponse.builder()
                .UUID(savedStaff.getUuid())
                .name(savedStaff.getName())
                .registrationDate(savedStaff.getRegistrationDate())
                .build();
    }
}
