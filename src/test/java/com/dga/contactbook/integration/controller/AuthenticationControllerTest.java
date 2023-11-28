package com.dga.contactbook.integration.controller;


import com.dga.contactbook.dto.AuthenticateRequest;
import com.dga.contactbook.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    public static final String USERNAME = "testuser";
    public static final String PASSWORD = "admin";
    public static final String API_V_1_AUTH = "/api/v1/auth";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Sql(statements = "DELETE FROM \"user\" WHERE username = 'testuserTemporary'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void signup() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(USERNAME + "Temporary", PASSWORD);
        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(API_V_1_AUTH + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Test
    @Sql(statements = "INSERT INTO \"user\" (id,username,password,role,deleted) VALUES (10000,'testuser'," +
            "'$2a$10$z4RSdl0PaNMB1GhOLsUREemhn6dXOUaLY65oAHnTSgJR86ijcSMKO'" +
            ",1,false)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM \"user\" WHERE username = 'testuser'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void signin() throws Exception {
        AuthenticateRequest authenticateRequest = new AuthenticateRequest(USERNAME, PASSWORD);
        String jsonRequest = objectMapper.writeValueAsString(authenticateRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(API_V_1_AUTH + "/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

}