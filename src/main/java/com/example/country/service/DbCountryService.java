package com.example.country.service;

import com.example.country.data.CountryEntity;
import com.example.country.data.CountryRepository;
import com.example.country.domain.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DbCountryService implements CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public DbCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> allCountries() {
        return countryRepository.findAll()
                .stream()
                .map(countryEntity -> new Country(
                        countryEntity.getName(),
                        countryEntity.getCode()
                ))
                .toList();
    }

    @Override
    public CountryEntity add(CountryEntity country) {
        return countryRepository.save(country);
    }

    @Override
    public CountryEntity update(CountryEntity country) {
        Optional<CountryEntity> optionalCountry = countryRepository.findAll()
                .stream()
                .filter(ce -> ce.getCode().equals(country.getCode()))
                .map(countryEntity -> {
                    countryEntity.setName(country.getName());
                    countryEntity.setCode(country.getCode());
                    return countryEntity;
                })
                .findFirst();
        if (optionalCountry.isEmpty()) {
            return countryRepository.save(country);
        } else {
            return countryRepository.save(optionalCountry.get());
        }
    }
}
