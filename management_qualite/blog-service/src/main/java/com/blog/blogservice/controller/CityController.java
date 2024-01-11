package com.blog.blogservice.controller;

import com.blog.blogservice.model.City;
import com.blog.blogservice.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogs/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public City save(@RequestBody City city) {
        return cityService.save(city);
    }

    @GetMapping
    public List<City> findAll() {
        return cityService.findAll();
    }

    @GetMapping(path ="/{id}")
    public Optional<City> findById(@PathVariable ("id") Long id) {
        return cityService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cityService.delete(id);
    }
}
