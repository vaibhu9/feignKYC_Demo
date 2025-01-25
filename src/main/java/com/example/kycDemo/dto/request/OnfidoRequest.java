package com.example.kycDemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnfidoRequest {
    
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String documentType;
    private OnfidoAddress address;
}
