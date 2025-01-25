package com.example.kycDemo.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.kycDemo.client.OnfidoClient;
import com.example.kycDemo.dto.request.OnfidoRequest;
import com.example.kycDemo.dto.response.OnfidoApplicantResponse;
import com.example.kycDemo.dto.response.OnfidoDocumentResponse;
import com.example.kycDemo.dto.response.OnfidoSdkTokenResponse;
import com.example.kycDemo.service.OnfidoService;

import feign.FeignException;

@Service
public class OnfidoServiceImpl implements OnfidoService {

    private static final Logger logger = LoggerFactory.getLogger(OnfidoServiceImpl.class);

    private final OnfidoClient onfidoClient;

    @Value("${onfido.api.token}")
    private String apiToken;

    public OnfidoServiceImpl(OnfidoClient onfidoClient) {
        this.onfidoClient = onfidoClient;
    }

    @Override
    public OnfidoApplicantResponse createApplicant(OnfidoRequest request) {
        logger.info("Creating Onfido applicant for: {} {}",
                request.getFirstName(), request.getLastName());

        try {
            Map<String, Object> applicantRequest = new HashMap<>();
            applicantRequest.put("first_name", request.getFirstName());
            applicantRequest.put("last_name", request.getLastName());
            applicantRequest.put("email", request.getEmail());

            if (request.getAddress() != null) {
                Map<String, String> address = new HashMap<>();
                address.put("building_number", request.getAddress().getBuildingNumber());
                address.put("street", request.getAddress().getStreet());
                address.put("town", request.getAddress().getTown());
                address.put("state", request.getAddress().getState());
                address.put("postcode", request.getAddress().getPostcode());
                address.put("country", request.getAddress().getCountry());
                applicantRequest.put("address", address);
            }

            return onfidoClient.createApplicant("Token token=" + apiToken, applicantRequest);
        } catch (FeignException.Forbidden e) {
            logger.error("Onfido API access forbidden. Please verify account permissions and API token: {}",
                    e.contentUTF8());
            throw new RuntimeException("Unable to create applicant - account permissions issue", e);
        } catch (Exception e) {
            logger.error("Unexpected error during applicant creation: {}", e.getMessage());
            throw new RuntimeException("Applicant creation failed", e);
        }
    }

    @Override
    public OnfidoApplicantResponse getApplicant(String applicantId) {
        logger.info("Retrieving applicant with ID: {}", applicantId);
        try {
            return onfidoClient.getApplicant("Token token=" + apiToken, applicantId);
        } catch (Exception e) {
            logger.error("Error retrieving applicant: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve applicant", e);
        }
    }

    @Override
    public List<OnfidoApplicantResponse> listApplicants() {
        logger.info("Retrieving all applicants");
        try {
            return onfidoClient.listApplicants("Token token=" + apiToken);
        } catch (Exception e) {
            logger.error("Error listing applicants: {}", e.getMessage());
            throw new RuntimeException("Failed to list applicants", e);
        }
    }

    // @Override
    // public OnfidoDocumentResponse uploadDocument(String applicantId, String documentType, MultipartFile documentFile) {
    //     logger.info("Uploading document for applicant: {}", applicantId);
    //     try {
    //         String encodedFile = Base64.getEncoder().encodeToString(documentFile.getBytes());

    //         Map<String, Object> documentRequest = new HashMap<>();
    //         documentRequest.put("applicant_id", applicantId);
    //         documentRequest.put("type", documentType);
    //         documentRequest.put("file", encodedFile);

    //         return onfidoClient.uploadDocument("Token token=" + apiToken, documentRequest);
    //     } catch (Exception e) {
    //         logger.error("Error uploading document: {}", e.getMessage());
    //         throw new RuntimeException("Document upload failed", e);
    //     }
    // }

    public OnfidoDocumentResponse uploadDocument(String applicantId, String documentType, MultipartFile documentFile) {
        logger.info("Uploading document for applicant: {}", applicantId);
        try {
            return onfidoClient.uploadDocument("Token token=" + apiToken, applicantId, documentType, documentFile);
        } catch (Exception e) {
            logger.error("Error uploading document: {}", e.getMessage(), e);
            throw new RuntimeException("Document upload failed", e);
        }
    }

    @Override
    public OnfidoDocumentResponse getDocument(String documentId) {
        logger.info("Retrieving document with ID: {}", documentId);
        try {
            return onfidoClient.getDocument("Token token=" + apiToken, documentId);
        } catch (Exception e) {
            logger.error("Error retrieving document: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve document", e);
        }
    }

    @Override
    public List<OnfidoDocumentResponse> listDocuments() {
        logger.info("Retrieving all documents");
        try {
            return onfidoClient.listDocuments("Token token=" + apiToken);
        } catch (Exception e) {
            logger.error("Error listing documents: {}", e.getMessage());
            throw new RuntimeException("Failed to list documents", e);
        }
    }

    @Override
    public OnfidoSdkTokenResponse generateSdkToken(String applicantId) {
        logger.info("Generating SDK token for applicant: {}", applicantId);
        try {
            Map<String, Object> tokenRequest = new HashMap<>();
            tokenRequest.put("applicant_id", applicantId);
            // You might want to make the referrer configurable through application
            // properties
            tokenRequest.put("referrer", "${onfido.api.url}");

            return onfidoClient.generateSdkToken("Token token=" + apiToken, tokenRequest);
        } catch (Exception e) {
            logger.error("Error generating SDK token: {}", e.getMessage());
            throw new RuntimeException("Failed to generate SDK token", e);
        }
    }
}
