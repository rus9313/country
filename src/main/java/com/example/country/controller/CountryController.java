package com.example.country.controller;

import com.example.country.data.CountryEntity;
import com.example.country.domain.Country;
import com.example.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/country")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public List<Country> all() {
        return countryService.allCountries();
    }

    @GetMapping("/{id}")
    public Country byId(@PathVariable("id") String id) {
        return countryService.countryById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CountryEntity add(@RequestBody CountryEntity newCountry) {
        return countryService.add(newCountry);
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public CountryEntity update(@RequestBody CountryEntity newCountry) {
        return countryService.update(newCountry);
    }
}
