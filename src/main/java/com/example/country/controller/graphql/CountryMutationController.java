package com.example.country.controller.graphql;

import com.example.country.domain.Country;
import com.example.country.domain.CountryGql;
import com.example.country.domain.CountryInputGql;
import com.example.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CountryMutationController {
    private final CountryService countryService;

    @Autowired
    public CountryMutationController(CountryService countryService) {
        this.countryService = countryService;
    }

    @MutationMapping
    public CountryGql addCountry(@Argument CountryInputGql input) {
        return countryService.addCountryGql(input);
    }
}
