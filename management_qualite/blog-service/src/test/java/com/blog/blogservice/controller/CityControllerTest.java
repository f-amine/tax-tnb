package com.blog.blogservice.controller;

import com.blog.blogservice.model.City;
import com.blog.blogservice.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CityService cityService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void CityController_CreateCity_ReturnsCity() throws Exception {
        // Arrange
        City city = City.builder()
                .name("Test City")
                .country("Test Country")
                .build();
        String cityJson = objectMapper.writeValueAsString(city);

        when(cityService.save(any(City.class))).thenReturn(city);

        // Act & Assert
        mockMvc.perform(post("/api/blogs/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cityJson))
                .andExpect(status().isOk())
                .andExpect(content().json(cityJson));
    }

    @Test
    public void CityController_FindById_ReturnsCity() throws Exception {
        // Arrange
        City city = City.builder()
                .name("Test City")
                .country("Test Country")
                .build();
        when(cityService.findById(anyLong())).thenReturn(Optional.of(city));

        // Act & Assert
        mockMvc.perform(get("/api/blogs/city/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(city)));
    }

    @Test
    public void CityController_FindAll_ReturnsAllCities() throws Exception {
        // Arrange
        City city1 = City.builder().name("Test City 1").country("Test Country 1").build();
        City city2 = City.builder().name("Test City 2").country("Test Country 2").build();
        List<City> cities = Arrays.asList(city1, city2);

        when(cityService.findAll()).thenReturn(cities);

        // Act & Assert
        mockMvc.perform(get("/api/blogs/city")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cities)));
    }

}