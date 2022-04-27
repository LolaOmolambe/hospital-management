package com.hospital.management.services.impl;


import com.hospital.management.TestUtil;
import com.hospital.management.apimodel.StaffCreationModel;
import com.hospital.management.apimodel.StaffCreationResponse;
import com.hospital.management.repositories.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StaffServiceImplTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffServiceImpl staffService;

    private StaffCreationModel staffCreationModel;

    @BeforeEach
    void setUp() {
        staffCreationModel = TestUtil.createStaffModel();
    }

    @Test
    public void addStaffSuccessfully() {
        when(staffRepository.existsByUuidIgnoreCase(ArgumentMatchers.any()))
                .thenReturn(false);
        when(staffRepository.save(ArgumentMatchers.any()))
                .thenReturn(TestUtil.createStaff());

        StaffCreationResponse staffCreationResponse = staffService.addStaff(staffCreationModel);

        verify(staffRepository).save(ArgumentMatchers.any());

        assertNotNull(staffCreationResponse.getName());
        assertNotNull(staffCreationResponse.getRegistrationDate());
        assertNotNull(staffCreationResponse.getUUID());
        assertEquals("SF|123456", staffCreationResponse.getUUID());
        assertEquals(staffCreationModel.getName(), staffCreationResponse.getName());
        assertEquals(staffCreationModel.getRegistrationDate(), staffCreationResponse.getRegistrationDate());

    }
}