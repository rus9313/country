package com.example.country.service;

import com.example.country.data.CountryEntity;
import com.example.country.data.CountryRepository;
import com.example.country.domain.Country;
import com.example.country.domain.CountryGql;
import com.example.country.domain.CountryInputGql;
import com.example.country.ex.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<CountryGql> allGqlCountries(Pageable pageable) {
        return countryRepository.findAll(pageable)
                .map(fe -> new CountryGql(
                        fe.getId(),
                        fe.getName(),
                        fe.getCode()
                ));
    }

    @Override
    public Country countryById(String id) {
        return countryRepository.findById(UUID.fromString(id))
                .map(countryEntity -> new Country(
                        countryEntity.getName(),
                        countryEntity.getCode()
                )).orElseThrow(CountryNotFoundException::new);

    }

    @Override
    public CountryGql countryGqlById(String id) {
        return countryRepository.findById(UUID.fromString(id))
                .map(countryEntity -> new CountryGql(
                        countryEntity.getId(),
                        countryEntity.getName(),
                        countryEntity.getCode()
                )).orElseThrow(CountryNotFoundException::new);

    }

    @Override
    public CountryEntity add(CountryEntity country) {
        return countryRepository.save(country);
    }

    @Override
    public CountryGql addCountryGql(CountryInputGql country) {
        CountryEntity ce = new CountryEntity();
        ce.setCode(country.code());
        ce.setName(country.name());

        CountryEntity saved = countryRepository.save(ce);

        return new CountryGql(
                saved.getId(),
                saved.getName(),
                saved.getCode());
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
