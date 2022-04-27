package com.hospital.management;

import com.hospital.management.apimodel.StaffCreationModel;
import com.hospital.management.entities.Staff;

import java.time.LocalDate;

public class TestUtil {
    public static StaffCreationModel createStaffModel(){
        return StaffCreationModel.builder()
                .name("Test")
                .registrationDate(LocalDate.of(2000, 4, 4))
                .build();
    }

    public static Staff createStaff(){
        return Staff.builder()
                .name("Test")
                .registrationDate(LocalDate.of(2000, 4, 4))
                .uuid("SF|123456")
                .build();
    }
}
