package com.example.weatherStack.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherStackResponse {
    private Location location;
    private Current current;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {
        private Integer temperature;
        
        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;
        
        @JsonProperty("wind_speed")
        private Integer windSpeed;
        
        @JsonProperty("is_day")
        private String isDay;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        private String name;
        private String country;
        private String region;
        
        @JsonProperty("timezone_id")
        private String timezoneId;
    }
}