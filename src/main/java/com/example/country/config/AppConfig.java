package com.example.country.config;

import com.example.country.service.CountryErrorAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;

public class AppConfig {

    @Value("${api.version}")
    private String apiVersion;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        return om;
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new CountryErrorAttributes(apiVersion);
    }
}