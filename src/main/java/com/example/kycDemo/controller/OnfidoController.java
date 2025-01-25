package com.example.kycDemo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.kycDemo.dto.request.OnfidoRequest;
import com.example.kycDemo.dto.response.OnfidoApplicantResponse;
import com.example.kycDemo.dto.response.OnfidoDocumentResponse;
import com.example.kycDemo.dto.response.OnfidoSdkTokenResponse;
import com.example.kycDemo.service.OnfidoService;

@RestController
@RequestMapping("/api/kyc")
public class OnfidoController {

    private static final Logger logger = LoggerFactory.getLogger(OnfidoController.class);

    private final OnfidoService onfidoService;

    public OnfidoController(OnfidoService onfidoService) {
        this.onfidoService = onfidoService;
    }

    // Applicant endpoints
    @PostMapping("/applicants")
    public ResponseEntity<OnfidoApplicantResponse> createApplicant(@RequestBody OnfidoRequest request) {
        logger.info("Creating applicant for: {} {}", request.getFirstName(), request.getLastName());
        return ResponseEntity.ok(onfidoService.createApplicant(request));
    }

    @GetMapping("/applicants/{applicant-id}")
    public ResponseEntity<OnfidoApplicantResponse> getApplicant(@PathVariable("applicant-id") String applicantId) {
        logger.info("Retrieving applicant with ID: {}", applicantId);
        return ResponseEntity.ok(onfidoService.getApplicant(applicantId));
    }

    @GetMapping("/applicants")
    public ResponseEntity<List<OnfidoApplicantResponse>> listApplicants() {
        logger.info("Retrieving all applicants");
        return ResponseEntity.ok(onfidoService.listApplicants());
    }

    // Document endpoints
    // @PostMapping("/documents")
    // public ResponseEntity<OnfidoDocumentResponse> uploadDocument(
    //         @RequestParam("applicant-id") String applicantId,
    //         @RequestParam("document-type") String documentType,
    //         @RequestParam("type") MultipartFile file) {
                
    //     try {
    //         logger.info("Uploading document for applicantId: {}", applicantId);
    //         OnfidoDocumentResponse response = onfidoService.uploadDocument(
    //                 applicantId,
    //                 documentType,
    //                 file);
    //         return ResponseEntity.ok(response);
    //     } catch (Exception e) {
    //         logger.error("Error uploading document: {}", e.getMessage());
    //         return ResponseEntity.internalServerError().build();
    //     }
    // }

    @PostMapping("/documents")
    public ResponseEntity<OnfidoDocumentResponse> uploadDocument(
            @RequestParam("applicantId") String applicantId,
            @RequestParam("documentType") String documentType,
            @RequestParam(value = "side", required = false) String side,
            @RequestParam("countryCode") String countryCode,
            @RequestParam("file") MultipartFile file) {
        
        try {
            logger.info("Uploading document for applicantId: {}", applicantId);
            DocumentUploadRequest request = DocumentUploadRequest.builder()
                .applicantId(applicantId)
                .documentType(documentType)
                .side(side)
                .location(new Location(countryCode))
                .file(file)
                .build();

            OnfidoDocumentResponse response = onfidoService.uploadDocument(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error uploading document: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new OnfidoDocumentResponse(null, "Error uploading document: " + e.getMessage()));
        }
    }

    @GetMapping("/documents/{applicant-id}")
    public ResponseEntity<OnfidoDocumentResponse> getDocument(@PathVariable String documentId) {
        logger.info("Retrieving document with ID: {}", documentId);
        return ResponseEntity.ok(onfidoService.getDocument(documentId));
    }

    @GetMapping("/documents")
    public ResponseEntity<List<OnfidoDocumentResponse>> listDocuments() {
        logger.info("Retrieving all documents");
        return ResponseEntity.ok(onfidoService.listDocuments());
    }

    // SDK Token endpoint
    @GetMapping("/sdk-token")
    public ResponseEntity<OnfidoSdkTokenResponse> generateSdkToken(@RequestParam("applicant-id") String applicantId) {
        logger.info("Generating SDK token for applicant: {}", applicantId);
        return ResponseEntity.ok(onfidoService.generateSdkToken(applicantId));
    }
}
