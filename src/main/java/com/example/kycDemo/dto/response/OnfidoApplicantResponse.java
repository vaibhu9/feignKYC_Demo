package com.example.kycDemo.dto.response;

import com.example.kycDemo.dto.request.OnfidoAddress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnfidoApplicantResponse {

    private String id;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private OnfidoAddress address;
    private String href;
    private Boolean sandbox;

}
