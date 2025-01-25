package com.example.weatherStack.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.weatherStack.client.WeatherStackClient;
import com.example.weatherStack.dto.WeatherStackResponse;
import com.example.weatherStack.service.WeatherStackService;

@Service
public class WeatherStackServiceImpl implements WeatherStackService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherStackServiceImpl.class); 
    
    private final WeatherStackClient weatherStackClient;
    
    @Value("${weatherstack.api.key}")
    private String apiKey;

    public WeatherStackServiceImpl(WeatherStackClient weatherStackClient) {
        this.weatherStackClient = weatherStackClient;
    }

    @Override
    public WeatherStackResponse getWeather(String city) {
        logger.debug("Fetching weather for city: {}", city);
        try {
            WeatherStackResponse response = weatherStackClient.getCurrentWeather(city, apiKey);
            logger.debug("Received response: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("Error fetching weather data: ", e);
            throw e;
        }
    }
}
