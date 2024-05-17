package fr.univlyon1.mif10.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UserOperationsControllerTest implements ConnectedControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginToNotExistingUser() throws Exception {
        String user = "test1937";
        String password = "test1937";
        String json = "{\"login\":\"" + user + "\",\"password\":\"" + password + "\"}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void testLoginToExistingUser() throws Exception {
        String user = "admin";
        String password = "admin";
        String json = "{\"login\":\"" + user + "\",\"password\":\"" + password + "\"}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"))
                .andExpect(header().string("Access-Control-Expose-Headers", "Authorization"));
    }

    @Test
    void testLoginToExistingUserWithWrongPassword() throws Exception {
        String user = "admin";
        String password = "wrongpassword";
        String json = "{\"login\":\"" + user + "\",\"password\":\"" + password + "\"}";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAuthenticateWrongToken() throws Exception {
        // Connecting to existing user to get a token
        String token = Connect("admin", "admin", mockMvc);
        String origin = "http://localhost";

        // Trying to authenticate with a wrong token
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/authenticate")
                .header("Authorization", "Bearer " + token + "wrong")
                .header("Origin", origin);
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAuthenticateWithWrongOrigin() throws Exception {
        String token = Connect("admin", "admin", mockMvc);
        String origin = "http://localhost";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/authenticate")
                .header("Authorization", "Bearer " + token)
                .header("Origin", "http://wrongorigin");
        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    void testAuthenticateCorrectly() throws Exception {
        String token = Connect("admin", "admin", mockMvc);
        String origin = "http://localhost";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/authenticate")
                .header("Authorization", "Bearer " + token)
                .header("Origin", origin);
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void testLogout() throws Exception {
        String token = Connect("admin", "admin", mockMvc);
        String origin = "http://localhost";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/logout")
                .header("Authorization", "Bearer " + token)
                .header("Origin", origin);
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void testLogoutWithWrongToken() throws Exception {
        String token = Connect("admin", "admin", mockMvc);
        String origin = "http://localhost";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/logout")
                .header("Authorization", "Bearer " + token + "wrong")
                .header("Origin", origin);
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }
}
