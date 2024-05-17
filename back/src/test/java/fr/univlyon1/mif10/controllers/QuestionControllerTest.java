package fr.univlyon1.mif10.controllers;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest implements ConnectedControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingTestValues.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingFormValues.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGet() throws Exception {
        testGetAllQuestions();
        testGetQuestionById();
        testGetQuestionDescriptionById();
        testGetAnswersByQuestionId();
    }

    @Test
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingTestValues.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingFormValues.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testFailedPost() throws Exception {
        testPostUserAnswersByQuestionIdRefusingWhenUserNotFound();
        testPostUserAnswersByQuestionIdRefusingWhenQuestionNotFound();
        testPostUserAnswersByQuestionIdRefusingWhenAnswerNotFound();
        testPostUserAnswersByQuestionIdRefusingWhenAnswerNotCorrectForTheQuestion();
    }

    void testGetAllQuestions() throws Exception {
        String expected = "[3,2,1]";

        testGet("/questions", expected, mockMvc, List.of("[]", "[1,2,3]"));
    }

    void testGetQuestionById() throws Exception {
        String expected = "{\"question\":{\"id\":1,\"description\":\"What is the make and model of your car?\"}," +
                "\"answers\":[{\"id\":1,\"description\":\"Audi A3\"}," +
                "{\"id\":2,\"description\":\"Renault Clio\"}," +
                "{\"id\":3,\"description\":\"Peugeot 208\"}," +
                "{\"id\":4,\"description\":\"Volkswagen Golf\"}]," +
                "\"constraint\":{\"questionId\":3,\"answerId\":9}}";

        String notExpected = "{\"question\":{\"id\":1,\"description\":\"What is the make and model of your car?\"}," +
                "\"answers\":[{\"id\":1,\"description\":\"Audi A3\"}," +
                "{\"id\":2,\"description\":\"Renault Clio\"}," +
                "{\"id\":3,\"description\":\"Peugeot 208\"}," +
                "{\"id\":4,\"description\":\"Volkswagen Golf\"}]," +
                "\"constraint\":{\"questionId\":3,\"answerId\":10}}";

        testGet("/questions/1", expected, mockMvc, List.of("{}", notExpected));
    }

    void testGetQuestionDescriptionById() throws Exception {
        String expected = "What is the make and model of your car?";

        List<String> notExpected = List.of("", "What is the make and modelee of your car?");

        testGet("/questions/1/description", expected, mockMvc, notExpected);
    }

    void testGetAnswersByQuestionId() throws Exception {
        String expected = "[{\"id\":1,\"description\":\"Audi A3\"}," +
                "{\"id\":2,\"description\":\"Renault Clio\"}," +
                "{\"id\":3,\"description\":\"Peugeot 208\"}," +
                "{\"id\":4,\"description\":\"Volkswagen Golf\"}]";

        List<String> notExpected = List.of("[]",
                "[{\"id\":4,\"description\":\"Volkswagen Golf\"}," +
                "{\"id\":3,\"description\":\"Peugeot 208\"}," +
                "{\"id\":2,\"description\":\"Renault Clio\"}," +
                "{\"id\":1,\"description\":\"Audi A3\"}]");

        testGet("/questions/1/answers", expected, mockMvc, notExpected);
    }

    @Test
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingTestValues.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:/database/GenerateTables.sql", "classpath:/database/InsertingFormValues.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testPostUserAnswersByQuestionIdCreatingCorrectly() throws Exception {
        String token = Connect("test_user", "user2", mockMvc);
        String origin = "http://localhost";
        String body = "{\"login\":\"test_user\",\"idAnswers\":[1,2]}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/questions/1/userAnswers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header("Origin", origin)
                .header("Authorization", "Bearer " + token);

        MvcResult response = mockMvc.perform(request).andReturn();
        String content = response.getResponse().getContentAsString();

        assertEquals(200, response.getResponse().getStatus());
        assertEquals("", content);
    }

    void testPostUserAnswersByQuestionIdRefusingWhenUserNotFound() throws Exception {
        String token = Connect("test_user", "user2", mockMvc);
        String origin = "http://localhost";
        String body = "{\"login\":\"admin-1532\",\"idAnswers\":[1,2]}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/questions/1/userAnswers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header("Origin", origin)
                .header("Authorization", "Bearer " + token);

        MvcResult response = mockMvc.perform(request).andReturn();
        String content = response.getResponse().getContentAsString();

        assertEquals(404, response.getResponse().getStatus());
        assertEquals("", content);
    }

    void testPostUserAnswersByQuestionIdRefusingWhenQuestionNotFound() throws Exception {
        // preparing the data
        Long idQuestion = -1154152L;
        String token = Connect("test_user", "user2", mockMvc);
        String login = "admin";
        String body = "{\"login\":\"" + login + "\",\"idAnswers\":[1,2]}";
        String origin = "http://localhost";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/questions/" + idQuestion + "/userAnswers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header("Origin", origin)
                .header("Authorization", "Bearer " + token);

        MvcResult response = mockMvc.perform(request).andReturn();
        String content = response.getResponse().getContentAsString();

        assertEquals(404, response.getResponse().getStatus());
        assertEquals("", content);
    }

    void testPostUserAnswersByQuestionIdRefusingWhenAnswerNotFound() throws Exception {
        // preparing the data
        String token = Connect("test_user", "user2", mockMvc);
        String login = "admin";
        String body = "{\"login\":\"" + login + "\",\"idAnswers\":[1892,-2586]}";
        String origin = "http://localhost";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/questions/1/userAnswers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header("Origin", origin)
                .header("Authorization", "Bearer " + token);

        MvcResult response = mockMvc.perform(request).andReturn();
        String content = response.getResponse().getContentAsString();

        assertEquals(404, response.getResponse().getStatus());
        assertEquals("", content);
    }

    void testPostUserAnswersByQuestionIdRefusingWhenAnswerNotCorrectForTheQuestion() throws Exception {
        // preparing the data
        String token = Connect("test_user", "user2", mockMvc);
        String login = "admin";
        String body = "{\"login\":\"" + login + "\",\"idAnswers\":[5]}";
        String origin = "http://localhost";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/questions/3/userAnswers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header("Origin", origin)
                .header("Authorization", "Bearer " + token);

        MvcResult response = mockMvc.perform(request).andReturn();
        String content = response.getResponse().getContentAsString();

        assertEquals(400, response.getResponse().getStatus());
        assertEquals("", content);
    }
}
