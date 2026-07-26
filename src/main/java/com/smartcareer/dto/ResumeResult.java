package com.smartcareer.dto;

import java.util.List;

public class ResumeResult {

    private int score;

    private List<String> detectedSkills;

    private List<String> missingSkills;

    private List<String> recommendedJobs;

    private int atsScore;

    private String suggestion;

    private String interviewQuestions;

    /*
        CONSTRUCTOR
     */

    public ResumeResult(

            int score,

            List<String> detectedSkills,

            List<String> missingSkills,

            List<String> recommendedJobs,

            int atsScore,

            String suggestion,

            String interviewQuestions
    ) {

        this.score = score;

        this.detectedSkills =
                detectedSkills;

        this.missingSkills =
                missingSkills;

        this.recommendedJobs =
                recommendedJobs;

        this.atsScore =
                atsScore;

        this.suggestion =
                suggestion;

        this.interviewQuestions =
                interviewQuestions;
    }

    /*
        GETTERS
     */

    public int getScore() {

        return score;
    }

    public List<String> getDetectedSkills() {

        return detectedSkills;
    }

    public List<String> getMissingSkills() {

        return missingSkills;
    }

    public List<String> getRecommendedJobs() {

        return recommendedJobs;
    }

    public int getAtsScore() {

        return atsScore;
    }

    public String getSuggestion() {

        return suggestion;
    }

    public String getInterviewQuestions() {

        return interviewQuestions;
    }
}