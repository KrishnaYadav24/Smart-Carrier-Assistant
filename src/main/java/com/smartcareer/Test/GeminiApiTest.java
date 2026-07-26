package com.smartcareer.Test;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class GeminiApiTest {

        public static void main(String[] args) {

                // Apni API Key yahan paste karo
                String apiKey = "AQ.Ab8RN6KjFTkJpWg-995SYQDMJDpdZ5qN7kGNusFutVi6c7nIkw";

                String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                                + apiKey;

                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                Map<String, Object> requestBody = Map.of(
                                "contents",
                                new Object[] {
                                                Map.of(
                                                                "parts",
                                                                new Object[] {
                                                                                Map.of(
                                                                                                "text",
                                                                                                "Say Hello from Gemini.")
                                                                })
                                });

                HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

                try {

                        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

                        System.out.println("Status Code : " + response.getStatusCode());

                        System.out.println("\nResponse:\n");

                        System.out.println(response.getBody());

                } catch (Exception e) {

                        e.printStackTrace();

                        System.out.println("\nError:\n" + e.getMessage());
                }
        }
}