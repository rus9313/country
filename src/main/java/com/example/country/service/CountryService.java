package com.example.country.service;

import com.example.country.data.CountryEntity;
import com.example.country.domain.Country;

import java.util.List;

public interface CountryService {
    List<Country> allCountries();
    CountryEntity add(CountryEntity country);
    CountryEntity update(CountryEntity country);

}
