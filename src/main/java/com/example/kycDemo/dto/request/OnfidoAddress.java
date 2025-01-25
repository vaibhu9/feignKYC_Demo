package com.example.kycDemo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnfidoAddress {
    
    @JsonProperty("building_number")
    private String buildingNumber;
    private String street;
    private String town;
    private String state;
    private String postcode;
    private String country;
}
