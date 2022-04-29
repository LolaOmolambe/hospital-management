package com.hospital.management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.management.SQLContainer;
import com.hospital.management.TestUtil;
import com.hospital.management.apimodel.request.StaffCreationModel;
import com.hospital.management.apimodel.response.StaffCreationResponse;
import com.hospital.management.repositories.StaffRepository;
import com.hospital.management.services.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = {
        "classpath:application-test.properties"
})
public class StaffControllerTest extends SQLContainer {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StaffService staffService;

    @MockBean
    private StaffRepository staffRepository;

    @Test
    public void addStaffFails_whenNameLengthIsLessThanTwo() throws Exception {
        String staffCreationModel = "{\"name\":\"a\"}";

        mockMvc.perform(post("/api/v1/staff")
                        .content(staffCreationModel)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.code", is("400")));
    }

    @Test
    public void addStaffFails_whenNameIsEmpty() throws Exception {
        String staffCreationModel = "";

        mockMvc.perform(post("/api/v1/staff")
                        .content(staffCreationModel)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.code", is("400")));
    }

    @Test
    public void addStaffFails_whenRegisteredDateIsEmpty() throws Exception {
        String staffCreationModel = "{\"name\":\"Test\"}";

        mockMvc.perform(post("/api/v1/staff")
                        .content(staffCreationModel)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.code", is("400")));
    }

    @Test
    public void addStaffSuccessfully() throws Exception {
        StaffCreationModel staffModel = TestUtil.createStaffModel();
        StaffCreationResponse staffResponse = TestUtil.createStaffResponse();

        when(staffService.addStaff(staffModel))
                .thenReturn(staffResponse);

        mockMvc.perform(post("/api/v1/staff")
                        .content(objectMapper.writeValueAsString(staffModel))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.code", is("200")))
                .andExpect(jsonPath("$.body.name", is("Test")))
                .andExpect(jsonPath("$.body.uuid", is(notNullValue())))
                .andExpect(jsonPath("$.body.registrationDate", is("2000-04-04")));
    }
}