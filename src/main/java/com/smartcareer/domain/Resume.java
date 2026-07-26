package com.smartcareer.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "resumes")
public class Resume {

    @Id

    @GeneratedValue(
            strategy =
            GenerationType.IDENTITY
    )

    private Long id;

    private String username;

    private String fileName;

    private int score;

    private int atsScore;

    /*
        LARGE TEXT FIELDS
     */

    @Column(columnDefinition = "TEXT")
    private String detectedSkills;

    @Column(columnDefinition = "TEXT")
    private String missingSkills;

    @Column(columnDefinition = "TEXT")
    private String recommendedJobs;

    @Column(columnDefinition = "LONGTEXT")
    private String suggestion;

    @Column(columnDefinition = "LONGTEXT")
    private String interviewQuestions;

    /*
        GETTERS & SETTERS
     */

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(
            String username
    ) {

        this.username =
                username;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(
            String fileName
    ) {

        this.fileName =
                fileName;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {

        this.score = score;
    }

    public int getAtsScore() {

        return atsScore;
    }

    public void setAtsScore(
            int atsScore
    ) {

        this.atsScore =
                atsScore;
    }

    public String getDetectedSkills() {

        return detectedSkills;
    }

    public void setDetectedSkills(
            String detectedSkills
    ) {

        this.detectedSkills =
                detectedSkills;
    }

    public String getMissingSkills() {

        return missingSkills;
    }

    public void setMissingSkills(
            String missingSkills
    ) {

        this.missingSkills =
                missingSkills;
    }

    public String getRecommendedJobs() {

        return recommendedJobs;
    }

    public void setRecommendedJobs(
            String recommendedJobs
    ) {

        this.recommendedJobs =
                recommendedJobs;
    }

    public String getSuggestion() {

        return suggestion;
    }

    public void setSuggestion(
            String suggestion
    ) {

        this.suggestion =
                suggestion;
    }

    public String getInterviewQuestions() {

        return interviewQuestions;
    }

    public void setInterviewQuestions(
            String interviewQuestions
    ) {

        this.interviewQuestions =
                interviewQuestions;
    }
}