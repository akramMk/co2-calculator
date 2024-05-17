package fr.univlyon1.mif10.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.mif10.controller.FormController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FormControllerTest implements ConnectedControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingTestValues.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingFormValues.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGet() throws Exception {
        testGetFormByUserId();
        testGetAnsweredFormByUserId();
    }

    void testGetFormByUserId() throws Exception {
        String expected = "{\"form\":[{\"question\":{\"id\":1,\"description\":\"What is the make and model of your car?\"}," +
                "\"answers\":[{\"id\":1,\"description\":\"Audi A3\"}," +
                "{\"id\":2,\"description\":\"Renault Clio\"}]}," +
                "{\"question\":{\"id\":2,\"description\":\"What is your average daily mileage using your car?\"}," +
                "\"answers\":[{\"id\":6,\"description\":\"Between 10 and 50 km\"}]}]}";

        List<String> notExpected = List.of("{\"form\":[{\"question\":{\"id\":1,\"description\":\"What is the make and model of your car?\"}," +
                "\"answers\":[{\"id\":1,\"description\":\"Audi A3\"}," +
                "{\"id\":2,\"description\":\"Renault Clio\"}]}," +
                "{\"question\":{\"id\":2,\"description\":\"What is your average daily mileage using your car?\"}," +
                "\"answers\":[{\"id\":6,\"description\":\"Between 10 and 50 km\"}," +
                "{\"id\":7,\"description\":\"Between 50 and 100 km\"}]}]}",
                expected + "5");

        testGet("/form/4", expected, mockMvc, notExpected);
    }

    void testGetAnsweredFormByUserId() throws Exception {
        String expected = "[1,2]";
        testGet("/form/4/answered", expected, mockMvc, List.of("[]", "[1,2,3]", "[2,1]"));
    }
}
