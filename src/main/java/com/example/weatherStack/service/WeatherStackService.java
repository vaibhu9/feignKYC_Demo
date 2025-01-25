package com.example.weatherStack.service;

import com.example.weatherStack.dto.WeatherStackResponse;

public interface WeatherStackService {
    
    public WeatherStackResponse getWeather(String city);  
}
