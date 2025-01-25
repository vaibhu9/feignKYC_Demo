package com.example.kycDemo.dto.request;

import org.springframework.beans.factory.parsing.Location;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentUploadRequest {
    private String applicantId;
    private String documentType;
    private String side;
    private Location location;
    private MultipartFile file;
}