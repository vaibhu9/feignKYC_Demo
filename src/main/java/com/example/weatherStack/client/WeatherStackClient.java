package com.example.weatherStack.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.weatherStack.dto.WeatherStackResponse;

@FeignClient(name = "weatherStackClient", url = "${weatherstack.api.url}")
public interface WeatherStackClient {

    @GetMapping("/current")
    WeatherStackResponse getCurrentWeather(@RequestParam("query") String query,
                                            @RequestParam("access_key") String apiKey);
}