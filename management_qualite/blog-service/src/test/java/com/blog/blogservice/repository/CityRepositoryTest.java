package com.blog.blogservice.repository;

import com.blog.blogservice.model.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void CityRepository_save_ReturnsCity() {
        //arrange
        City city = City.builder()
                .name("Test")
                .country("TestCountry")
                .build();
        //act
        City savedCity = cityRepository.save(city);
        //assert
        Assertions.assertNotNull(savedCity);
        Assertions.assertEquals(city.getName(), savedCity.getName());
        Assertions.assertEquals(city.getCountry(), savedCity.getCountry());
    }

    @Test
    public void CityRepository_findById_ReturnsCity() {
        //arrange
        City city = City.builder()
                .name("Test")
                .country("TestCountry")
                .build();
        City savedCity = cityRepository.save(city);
        //act
        City foundCity = cityRepository.findById(savedCity.getId()).orElse(null);
        //assert
        Assertions.assertNotNull(foundCity);
        Assertions.assertEquals(savedCity.getId(), foundCity.getId());
    }

    @Test
    public void CityRepository_delete_DeletesCity() {
        //arrange
        City city = City.builder()
                .name("Test")
                .country("TestCountry")
                .build();
        City savedCity = cityRepository.save(city);
        //act
        cityRepository.delete(savedCity);
        //assert
        Assertions.assertEquals(0, cityRepository.findAll().size());
    }
    @Test
    public void CityRepository_findAll_ReturnsAllCities() {
        //arrange
        City city = City.builder()
                .name("Test")
                .country("TestCountry")
                .build();
        City city1 = City.builder()
                .name("Test2")
                .country("TestCountry2")
                .build();
        City savedCity = cityRepository.save(city);
        City savedCity1 = cityRepository.save(city1);
        //act
        List<City> cities = cityRepository.findAll();
        //assert
        Assertions.assertEquals(2, cities.size());
    }
}