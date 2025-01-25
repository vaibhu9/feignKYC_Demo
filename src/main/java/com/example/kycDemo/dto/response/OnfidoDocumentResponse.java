package com.example.kycDemo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnfidoDocumentResponse {
    private String id;
    private String createdAt;
    private String fileName;
    private String fileType;
    private String fileSize;
    private String type;
    private String side;
    private String issuingCountry;
    private String status;
    private DocumentDownloadResponse download;
}