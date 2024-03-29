package com.gamestore.config;

import com.gamestore.domain.models.GameCartModel;
import com.gamestore.services.validators.GameValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public GameValidator gameValidator() {
        return new GameValidator();
    }

    @Bean
    public Map<String, GameCartModel> cart() {
        return new HashMap<>();
    }
}