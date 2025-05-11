package com.userPI.usersmanagementsystem.service;

import com.userPI.usersmanagementsystem.dto.UserClusterRequestDTO;
import com.userPI.usersmanagementsystem.dto.UserClusterResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClusterService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String FAST_API_URL = "http://localhost:8004/predict-cluster";

    public UserClusterResponseDTO getClusterPrediction(UserClusterRequestDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserClusterRequestDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<UserClusterResponseDTO> response = restTemplate.postForEntity(
                FAST_API_URL, request, UserClusterResponseDTO.class
        );

        return response.getBody();
    }
}
