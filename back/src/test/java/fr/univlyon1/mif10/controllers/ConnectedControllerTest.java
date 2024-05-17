package fr.univlyon1.mif10.controllers;

import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public interface ConnectedControllerTest {
    default String Connect(String user, String password, MockMvc mockMvc) throws Exception {
        String json = "{\"login\":\"" + user + "\",\"password\":\"" + password + "\"}";
        String origin = "http://localhost";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header("Origin", origin);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(header().string("Access-Control-Expose-Headers", "Authorization"));

        MvcResult response = mockMvc.perform(request).andReturn();
        String token = Objects.requireNonNull(response.getResponse()
                        .getHeader("Authorization"))
                .replace("Bearer ", "");
        return token;
    }

    default void testGet(String apiURl, String expected, MockMvc mockMvc, List<String> notExpected) throws Exception {
        String token = Connect("test_user", "user2", mockMvc);
        String origin = "http://localhost";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(apiURl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Origin", origin)
                .header("Authorization", "Bearer " + token);
        mockMvc.perform(request)
                .andExpect(status().isOk());

        MvcResult response = mockMvc.perform(request).andReturn();
        String content = response.getResponse().getContentAsString();

        assertEquals(expected, content);
        for (String s : notExpected) {
            assertNotEquals(s, content);
        }
    }
}
