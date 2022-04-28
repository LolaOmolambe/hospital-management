package com.hospital.management.services.impl;


import com.hospital.management.TestUtil;
import com.hospital.management.apimodel.request.StaffCreationModel;
import com.hospital.management.apimodel.response.StaffCreationResponse;
import com.hospital.management.exceptions.BadRequestException;
import com.hospital.management.repositories.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

    @Test
    public void updateStaffProfileFails_whenStaffWithUUIDDoesNotExist() {
        when(staffRepository.findByUuidIgnoreCase("SFA116C848"))
                .thenReturn(null);

        assertThatThrownBy(() -> staffService.updateStaff(staffCreationModel, "SFA116C848"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Staff with this UUID does not exist.");

    }

    @Test
    public void shouldUpdateStaffProfileSuccessfully() {
        when(staffRepository.findByUuidIgnoreCase("SFA116C848"))
                .thenReturn(TestUtil.createStaff());
        when(staffRepository.save(ArgumentMatchers.any()))
                .thenReturn(TestUtil.createStaff());

        StaffCreationResponse creationResponse = staffService.updateStaff(staffCreationModel, "SFA116C848");

        assertNotNull(creationResponse.getName());
        assertNotNull(creationResponse.getRegistrationDate());
        assertNotNull(creationResponse.getUUID());
    }
}