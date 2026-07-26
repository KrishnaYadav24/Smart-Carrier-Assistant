package com.smartcareer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoadmapService {

    @Autowired
    private GeminiAIService geminiAIService;

    public String generateRoadmap(String role){

        String prompt =

                "Create a complete learning roadmap for "
                + role +

                "\n\nInclude:\n"

                + "1. Required Skills\n"
                + "2. Month-wise Learning Plan\n"
                + "3. Projects\n"
                + "4. Certifications\n"
                + "5. Interview Preparation\n"
                + "6. Career Tips";

        return geminiAIService.generatePrompt(prompt);
    }
}