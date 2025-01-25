package com.example.weatherStack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.weatherStack.dto.WeatherStackResponse;
import com.example.weatherStack.service.WeatherStackService;

@RestController
@RequestMapping("feign-client")
public class WeatherStackController {
    
    private WeatherStackService weatherStackService;

    public WeatherStackController(WeatherStackService weatherStackService) {
        this.weatherStackService = weatherStackService;
    }

    @GetMapping("/weather")
    public WeatherStackResponse getWeather(@RequestParam("city") String city) {
        return weatherStackService.getWeather(city);
    }
}
