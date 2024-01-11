package com.blog.blogservice.service;

import com.blog.blogservice.model.Category;
import com.blog.blogservice.model.City;
import com.blog.blogservice.repository.CityRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City save(City city) {
        return cityRepository.save(city);
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public Optional<City> findById(Long aLong) {
        return cityRepository.findById(aLong);
    }

    public void delete(Long id) {
        cityRepository.deleteById(id);
    }
}
