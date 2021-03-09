package com.ticketing.controller;

import com.ticketing.dto.ProjectDTO;
import com.ticketing.dto.RoleDTO;
import com.ticketing.dto.UserDTO;
import com.ticketing.enums.Gender;
import com.ticketing.enums.Status;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String token = "eyJhbGciOiJIUzI1NiJ9.eyJmaXJzdE5hbWUiOiJhZG1pbiIsImxhc3ROYW1lIjoiYWRtaW4iLCJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJpZCI6MSwiZXhwIjoxNjE1MDkzODQ2LCJpYXQiOjE2MTUwNTc4NDYsInVzZXJuYW1lIjoiYWRtaW5AYWRtaW4uY29tIn0.sS1OXeMkTv1FEWHTyDsHh5l1QSpS9t5bkTgV8ggcq5Y";

    static UserDTO userDTO;
    static ProjectDTO projectDTO;

    @BeforeAll
    static void setUp(){

        userDTO = UserDTO.builder()
                .id(2L)
                .firstName("Mike")
                .lastName("Smith")
                .userName("ozzy@cybertekschool.com")
                .passWord("abc123")
                .confirmPassword("abc123")
                .role(new RoleDTO(2L,"Manager"))
                .gender(Gender.MALE)
                .build();

        projectDTO = ProjectDTO.builder()
                .projectCode("Api1")
                .projectName("Api")
                .assignedManager(userDTO)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .projectDetail("Api Test")
                .projectStatus(Status.OPEN)
                .completeTaskCounts(0)
                .unfinishedTaskCounts(0)
                .build();




    }
}