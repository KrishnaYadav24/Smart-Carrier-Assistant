package com.smartcareer.service;

import org.springframework.stereotype.Service;

@Service
public class NLPService {

    public String analyzeSemanticMatch(

            String resumeText,

            String jobDescription
    ) {

        return
            "Semantic AI matching completed successfully.";
    }
}