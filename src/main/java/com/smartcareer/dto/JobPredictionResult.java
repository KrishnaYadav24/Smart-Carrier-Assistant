package com.smartcareer.dto;

import java.util.List;

public class JobPredictionResult {

    private List<String> roles;
    private String explanation;

    public JobPredictionResult(
            List<String> roles,
            String explanation
    ) {
        this.roles = roles;
        this.explanation = explanation;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getExplanation() {
        return explanation;
    }
}