package com.example.kycDemo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnfidoSdkTokenResponse {
    private String applicantId;
    private String referrer;
    private String token;
    private String expires;
}