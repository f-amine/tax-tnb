package com.blog.blogservice.service;

import com.blog.blogservice.model.City;
import com.blog.blogservice.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void CityService_save_ReturnsCity() {
        //arrange
        City city = City.builder()
                .name("Test")
                .country("TestCountry")
                .build();

        when(cityRepository.save(city)).thenReturn(city);

        //act
        City savedCity = cityService.save(city);

        //assert
        assertEquals(city, savedCity);
    }

    @Test
    public void CityService_findAll_ReturnsAllCities() {
        //arrange
        City city1 = City.builder().name("Test1").country("TestCountry1").build();
        City city2 = City.builder().name("Test2").country("TestCountry2").build();
        List<City> expectedCities = Arrays.asList(city1, city2);

        when(cityRepository.findAll()).thenReturn(expectedCities);

        //act
        List<City> actualCities = cityService.findAll();

        //assert
        assertEquals(expectedCities, actualCities);
    }

    @Test
    public void CityService_findById_ReturnsCity() {
        //arrange
        City city = City.builder().name("Test").country("TestCountry").build();
        Optional<City> expectedCity = Optional.of(city);

        when(cityRepository.findById(anyLong())).thenReturn(expectedCity);

        //act
        Optional<City> actualCity = cityService.findById(1L);

        //assert
        assertEquals(expectedCity, actualCity);
    }

//    @Test
//    public void CityService_delete_DeletesCity() {
//        //arrange
//        City city = City.builder().name("Test").country("TestCountry").build();
//
//        //act
//        cityService.delete(city);
//
//        //assert
//        verify(cityRepository, times(1)).delete(city);
//    }
}