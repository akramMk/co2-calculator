package fr.univlyon1.mif10.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class AnswerControllerTest implements ConnectedControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingTestValues.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingFormValues.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGet() throws Exception {
        testGetAllAnswers();
        testGetAnswerById();
        testGetAnswerDescriptionById();
    }

    void testGetAllAnswers() throws Exception {
        String expected = "[{\"id\":1,\"description\":\"Audi A3\"}," +
                "{\"id\":2,\"description\":\"Renault Clio\"}," +
                "{\"id\":3,\"description\":\"Peugeot 208\"}," +
                "{\"id\":4,\"description\":\"Volkswagen Golf\"}," +
                "{\"id\":5,\"description\":\"Less than 10 km\"}," +
                "{\"id\":6,\"description\":\"Between 10 and 50 km\"}," +
                "{\"id\":7,\"description\":\"Between 50 and 100 km\"}," +
                "{\"id\":8,\"description\":\"More than 100 km\"}," +
                "{\"id\":9,\"description\":\"Yes\"}," +
                "{\"id\":10,\"description\":\"No\"}]";

        testGet("/answers", expected, mockMvc, List.of("[]", "[{\"id\":1,\"description\":\"Audi A3\"}]"));
    }

    void testGetAnswerById() throws Exception {
        String expected = "{\"id\":3,\"description\":\"Peugeot 208\"}";

        testGet("/answers/3", expected, mockMvc, List.of("{}", "{\"id\":1,\"description\":\"Audi A3\"}"));
    }

    void testGetAnswerDescriptionById() throws Exception {
        String expected = "Peugeot 208";

        testGet("/answers/3/description", expected, mockMvc, List.of("Renault Clio", "Audi A3", expected + "2"));
    }
}
