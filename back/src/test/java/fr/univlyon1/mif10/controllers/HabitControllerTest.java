package fr.univlyon1.mif10.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.mif10.controller.HabitController;
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
class HabitControllerTest implements ConnectedControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingTestValues.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingFormValues.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGet() throws Exception {
        testGetAllHabits();
        testGetHabitById();
        testGetHabitDescriptionById();
        testGetQuestionsByHabitId();
    }

    void testGetAllHabits() throws Exception {
        String expected = "[{\"id\":1,\"description\":\"You are a good driver\"}]";

        List<String> notExpected = List.of("[]", expected + "2");

        testGet("/habits", expected, mockMvc, notExpected);
    }

    void testGetHabitById() throws Exception {
        String expected = "{\"id\":1,\"description\":\"You are a good driver\"}";

        List<String> notExpected = List.of("{}", "{\"id\":2,\"description\":\"You are a good driver\"}");

        testGet("/habits/1", expected, mockMvc, notExpected);
    }

    void testGetHabitDescriptionById() throws Exception {
        String expected = "You are a good driver";

        List<String> notExpected = List.of("You are a good driver2", "You are a good driver3", "");

        testGet("/habits/1/description", expected, mockMvc, notExpected);
    }

    void testGetQuestionsByHabitId() throws Exception {
        String expected = "[{\"id\":1,\"description\":\"What is the make and model of your car?\"}," +
                "{\"id\":2,\"description\":\"What is your average daily mileage using your car?\"}," +
                "{\"id\":3,\"description\":\"Do you have a car?\",\"launches\":[{\"id\":1,\"description\":\"You are a good driver\"}],\"idAnswer\":9}]";

        List<String> notExpected = List.of("[]", expected + "2");

        testGet("/habits/1/questions", expected, mockMvc, notExpected);
    }
}
