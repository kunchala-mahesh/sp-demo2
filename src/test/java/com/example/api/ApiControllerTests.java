package com.example.api;

import com.example.api.dto.PostRequest;
import com.example.api.dto.ProductRequest;
import com.example.api.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET / should return application discovery metadata")
    void testRootEndpoint() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("Spring Boot Basic APIs")))
                .andExpect(jsonPath("$.status", is("UP")))
                .andExpect(jsonPath("$.endpoints['Posts API']", is("/posts")))
                .andExpect(jsonPath("$.endpoints['Products API']", is("/products")))
                .andExpect(jsonPath("$.endpoints['Users API']", is("/users")));
    }

    @Test
    @DisplayName("GET /posts should return seeded posts")
    void testGetAllPosts() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0].title", notNullValue()));
    }

    @Test
    @DisplayName("POST /posts should create a new post")
    void testCreatePost() throws Exception {
        PostRequest request = new PostRequest(
                "Spring Boot REST APIs",
                "Learn how to create scalable RESTful APIs with Spring Boot 3.",
                "tester",
                "Tutorial"
        );

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("Spring Boot REST APIs")))
                .andExpect(jsonPath("$.author", is("tester")));
    }

    @Test
    @DisplayName("GET /products should return seeded products")
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(4))))
                .andExpect(jsonPath("$[0].price", notNullValue()));
    }

    @Test
    @DisplayName("POST /products should create a new product")
    void testCreateProduct() throws Exception {
        ProductRequest request = new ProductRequest(
                "Wireless Mouse",
                "Ergonomic rechargeable mouse with silent clicks",
                new BigDecimal("29.99"),
                "Electronics",
                100
        );

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Wireless Mouse")))
                .andExpect(jsonPath("$.price", is(29.99)));
    }

    @Test
    @DisplayName("GET /users should return seeded users")
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0].username", notNullValue()));
    }

    @Test
    @DisplayName("POST /users should register a new user")
    void testCreateUser() throws Exception {
        UserRequest request = new UserRequest(
                "emily_rose",
                "emily@example.com",
                "Emily Rose",
                "USER"
        );

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username", is("emily_rose")))
                .andExpect(jsonPath("$.email", is("emily@example.com")));
    }

    @Test
    @DisplayName("POST /users with invalid email should return 400 Bad Request")
    void testCreateUserValidationError() throws Exception {
        UserRequest request = new UserRequest(
                "invalid_user",
                "not-an-email",
                "Invalid User",
                "USER"
        );

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Validation Failed")))
                .andExpect(jsonPath("$.message", containsStringIgnoringCase("validation failed")))
                .andExpect(jsonPath("$.validationErrors.email", containsStringIgnoringCase("valid email")));
    }
}
