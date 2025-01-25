package com.example.kycDemo.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.kycDemo.dto.response.OnfidoApplicantResponse;
import com.example.kycDemo.dto.response.OnfidoDocumentResponse;
import com.example.kycDemo.dto.response.OnfidoSdkTokenResponse;

@FeignClient(name = "onfidoClient", url = "${onfido.api.url}")
public interface OnfidoClient {
    // Applicant APIs
    @PostMapping("/applicants")
    OnfidoApplicantResponse createApplicant(@RequestHeader("Authorization") String token,
                                          @RequestBody Map<String, Object> request);

    @GetMapping("/applicants/{applicant_id}")
    OnfidoApplicantResponse getApplicant(@RequestHeader("Authorization") String token,
                                       @PathVariable("applicant_id") String applicantId);

    @GetMapping("/applicants")
    List<OnfidoApplicantResponse> listApplicants(@RequestHeader("Authorization") String token);

    // Document APIs
    @PostMapping(value = "/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    OnfidoDocumentResponse uploadDocument(
            @RequestHeader("Authorization") String token,
            @RequestPart("applicant_id") String applicantId,
            @RequestPart("type") String documentType,
            @RequestPart("file") MultipartFile file
    );

    @GetMapping("/documents/{document_id}")
    OnfidoDocumentResponse getDocument(@RequestHeader("Authorization") String token,
                                     @PathVariable("document_id") String documentId);

    @GetMapping("/documents")
    List<OnfidoDocumentResponse> listDocuments(@RequestHeader("Authorization") String token);

    // SDK Token API
    @PostMapping("/sdk_token")
    OnfidoSdkTokenResponse generateSdkToken(@RequestHeader("Authorization") String token,
                                          @RequestBody Map<String, Object> request);
}