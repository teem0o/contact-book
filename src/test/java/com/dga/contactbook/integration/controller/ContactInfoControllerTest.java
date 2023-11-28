package com.dga.contactbook.integration.controller;

import com.dga.contactbook.dto.ContactInfoRequest;
import com.dga.contactbook.entity.User;
import com.dga.contactbook.repository.UserRepository;
import com.dga.contactbook.security.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ContactInfoControllerTest {
    public static final String TESTUSER_TEMPORARY = "testuserTemporary";
    public static final String API_V_1_CONTACT_INFO = "/api/v1/contact-info";
    public static final String FULL_NAME = "fullName";
    public static final String PHONE_NUMBER = "555555555";
    public static final String EMAIL = "email.com";
    public static final String ADDRERSS = "addrerss";
    public static final long ID = 10000L;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private ObjectMapper objectMapper;
    private String token;


    @BeforeEach
    void setUp() {
        User user = modelMapper.map(userRepository.findByUsername(TESTUSER_TEMPORARY), User.class);
        token = jwtService.generateToken(user);
    }

    @Test
    @Sql(statements = {
            "INSERT INTO \"user\" (id,username,password,deleted) VALUES (10000,'testuserTemporary'," +
                    "'$2a$10$z4RSdl0PaNMB1GhOLsUREemhn6dXOUaLY65oAHnTSgJR86ijcSMKO'" +
                    ",false)",
            "INSERT INTO contact_info (id,full_name,phone_number,email,deleted,user_id) " +
                    "VALUES (10000,'fullName','555555555','email.com',false,10000)"

    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {"DELETE FROM contact_info WHERE id = 10000",
            "DELETE FROM \"user\" WHERE username = 'testuserTemporary'",},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllContactInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_V_1_CONTACT_INFO)
                        .header("Authorization", "Bearer " + token)
                        .param("page", "0")
                        .param("size", "10")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].fullName").value(FULL_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].phoneNumber").value(PHONE_NUMBER))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].email").value(EMAIL));

    }


    @Test
    @Sql(statements = {
            "INSERT INTO \"user\" (id,username,password,deleted) VALUES (10000,'testuserTemporary'," +
                    "'$2a$10$z4RSdl0PaNMB1GhOLsUREemhn6dXOUaLY65oAHnTSgJR86ijcSMKO'" +
                    ",false)",
            "INSERT INTO contact_info (id,full_name,phone_number,email,deleted,user_id) " +
                    "VALUES (10000,'fullName','555555555','email.com',false,10000)"

    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {"DELETE FROM contact_info WHERE id = 10000",
            "DELETE FROM \"user\" WHERE username = 'testuserTemporary'",},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getContactInfoById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_V_1_CONTACT_INFO + "/" + ID)
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(PHONE_NUMBER))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(EMAIL));
    }

    @Test
    @Sql(statements = {
            "INSERT INTO \"user\" (id,username,password,deleted) VALUES (10000,'testuserTemporary'," +
                    "'$2a$10$z4RSdl0PaNMB1GhOLsUREemhn6dXOUaLY65oAHnTSgJR86ijcSMKO'" +
                    ",false)"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {
            "DELETE FROM \"user\" WHERE username = 'testuserTemporary'",},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createContactInfo() throws Exception {
        ContactInfoRequest contactInfoRequest = new ContactInfoRequest(ID, FULL_NAME, PHONE_NUMBER, EMAIL, ADDRERSS);
        String jsonRequest = objectMapper.writeValueAsString(contactInfoRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(API_V_1_CONTACT_INFO)
                        .header("Authorization", "Bearer " + token)
                        .content(jsonRequest)
                        .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(FULL_NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(PHONE_NUMBER));
    }

    @Test
    @Sql(statements = {
            "INSERT INTO \"user\" (id,username,password,deleted) VALUES (10000,'testuserTemporary'," +
                    "'$2a$10$z4RSdl0PaNMB1GhOLsUREemhn6dXOUaLY65oAHnTSgJR86ijcSMKO'" +
                    ",false)",
            "INSERT INTO contact_info (id,full_name,phone_number,email,deleted,user_id) " +
                    "VALUES (10000,'fullName','555555555','email.com',false,10000)"

    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = {"DELETE FROM contact_info WHERE id = 10000",
            "DELETE FROM \"user\" WHERE username = 'testuserTemporary'",},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateContactInfo() throws Exception {
        ContactInfoRequest contactInfoRequest = new ContactInfoRequest(ID, FULL_NAME + "1",
                PHONE_NUMBER + "1", EMAIL + "1", ADDRERSS + "1");
        String jsonRequest = objectMapper.writeValueAsString(contactInfoRequest);

        mockMvc.perform(MockMvcRequestBuilders.put(API_V_1_CONTACT_INFO)
                        .header("Authorization", "Bearer " + token)
                        .content(jsonRequest)
                        .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(FULL_NAME + "1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(PHONE_NUMBER + "1"));
    }


    @Test
    @Sql(statements = {
            "INSERT INTO \"user\" (id,username,password,deleted) VALUES (10000,'testuserTemporary'," +
                    "'$2a$10$z4RSdl0PaNMB1GhOLsUREemhn6dXOUaLY65oAHnTSgJR86ijcSMKO'" +
                    ",false)",
            "INSERT INTO contact_info (id,full_name,phone_number,email,deleted,user_id) " +
                    "VALUES (10000,'fullName','555555555','email.com',false,10000)"

    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteContactInfo() throws Exception {
        ContactInfoRequest contactInfoRequest = new ContactInfoRequest(ID, FULL_NAME, PHONE_NUMBER, EMAIL, ADDRERSS);
        String jsonRequest = objectMapper.writeValueAsString(contactInfoRequest);

        mockMvc.perform(MockMvcRequestBuilders.delete(API_V_1_CONTACT_INFO + "/" + ID)
                        .header("Authorization", "Bearer " + token)
                        .content(jsonRequest)
                        .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
