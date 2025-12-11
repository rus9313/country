package com.example.country.service;

import com.example.country.data.CountryEntity;
import com.example.country.domain.Country;
import com.example.country.domain.CountryGql;
import com.example.country.domain.CountryInputGql;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CountryService {
    List<Country> allCountries();

    Page<CountryGql> allGqlCountries(Pageable pageable);

    CountryGql countryGqlById(String id);

    CountryEntity add(CountryEntity country);

    CountryGql addCountryGql(CountryInputGql country);

    CountryEntity update(CountryEntity country);

    Country countryById(String id);
}
