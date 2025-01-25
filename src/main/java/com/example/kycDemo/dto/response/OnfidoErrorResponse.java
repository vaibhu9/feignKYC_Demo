package com.example.kycDemo.dto.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnfidoErrorResponse {
    private Error error;

    @Data
    public static class Error {
        private String type;
        private String message;
        private Map<String, Object> fields;
    }
}