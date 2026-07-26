package com.smartcareer.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiAIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String MODEL = "llama-3.3-70b-versatile";

    public String generatePrompt(String prompt) {

        try {

            String url = "https://api.groq.com/openai/v1/chat/completions";

            System.out.println("\n========== GROQ DEBUG ==========");
            System.out.println("API Key Length : " + apiKey.length());
            System.out.println("Starts With    : " + apiKey.substring(0, 4));
            System.out.println("Ends With      : " + apiKey.substring(apiKey.length() - 4));
            System.out.println("================================");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey.trim());

            Map<String, Object> requestBody = Map.of(
                    "model", MODEL,
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", prompt)),
                    "temperature", 0.3,
                    "max_tokens", 2048);

            HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

            System.out.println("\n========== GROQ REQUEST ==========");
            System.out.println("Model : " + MODEL);
            System.out.println("URL   : " + url);
            System.out.println("Authorization : Bearer " + apiKey.substring(0, 8) + "********");
            System.out.println("==================================");

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            url,
                            entity,
                            String.class);

            String body = response.getBody();

            System.out.println("\n========== GROQ RESPONSE ==========");
            System.out.println(body);
            System.out.println("===================================");

            JSONObject json = new JSONObject(body);

            if (!json.has("choices")) {
                return body;
            }

            JSONArray choices = json.getJSONArray("choices");

            if (choices.length() == 0) {
                return "No response generated.";
            }

            JSONObject message = choices
                    .getJSONObject(0)
                    .getJSONObject("message");

            return message.getString("content");

        }

        catch (HttpStatusCodeException e) {

            System.out.println("\n========== GROQ ERROR ==========");
            System.out.println("Status : " + e.getStatusCode());
            System.out.println("Headers : " + e.getResponseHeaders());

            byte[] body = e.getResponseBodyAsByteArray();

            System.out.println("Body Length : " + body.length);

            if (body.length > 0) {
                System.out.println("Body : ");
                System.out.println(new String(body, StandardCharsets.UTF_8));
            } else {
                System.out.println("Response body is empty.");
            }

            System.out.println("================================");

            return "Groq API Error\n\n"
                    + "Status : " + e.getStatusCode()
                    + "\n\n"
                    + (body.length > 0
                    ? new String(body, StandardCharsets.UTF_8)
                    : "Empty Response");

        }

        catch (Exception e) {

            e.printStackTrace();

            return "Exception : " + e.getMessage();
        }

    }

    public String generateResumeAnalysis(String resumeText) {

        String prompt = """
                You are an ATS Resume Analyzer.

                Analyze the following resume professionally.

                Provide:

                1. Resume Summary

                2. Strengths

                3. Weaknesses

                4. Missing Skills

                5. ATS Score (0-100)

                6. ATS Optimization Tips

                7. Career Guidance

                8. Suggested Improvements

                Resume:

                """ + resumeText;

        return generatePrompt(prompt);
    }
}