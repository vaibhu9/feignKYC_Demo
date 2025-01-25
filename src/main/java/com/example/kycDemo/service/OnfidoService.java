package com.example.kycDemo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.kycDemo.dto.request.OnfidoRequest;
import com.example.kycDemo.dto.response.OnfidoApplicantResponse;
import com.example.kycDemo.dto.response.OnfidoDocumentResponse;
import com.example.kycDemo.dto.response.OnfidoSdkTokenResponse;

public interface OnfidoService {
    
    OnfidoApplicantResponse createApplicant(OnfidoRequest request);
    OnfidoApplicantResponse getApplicant(String applicantId);
    List<OnfidoApplicantResponse> listApplicants();
    OnfidoDocumentResponse uploadDocument(String applicantId, String documentType, MultipartFile documentFile);
    OnfidoDocumentResponse getDocument(String documentId);
    List<OnfidoDocumentResponse> listDocuments();
    OnfidoSdkTokenResponse generateSdkToken(String applicantId);
}
