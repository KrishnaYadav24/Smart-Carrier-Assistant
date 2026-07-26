package com.smartcareer.model;

public class InterviewEvaluationResponse {

    private double score;

    private String correctness;

    private String missingConcepts;

    private String idealAnswer;

    private String communication;

    private String confidence;

    private String improvementTips;

    private String motivation;

    private String nextQuestion;

    public InterviewEvaluationResponse() {
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getCorrectness() {
        return correctness;
    }

    public void setCorrectness(String correctness) {
        this.correctness = correctness;
    }

    public String getMissingConcepts() {
        return missingConcepts;
    }

    public void setMissingConcepts(String missingConcepts) {
        this.missingConcepts = missingConcepts;
    }

    public String getIdealAnswer() {
        return idealAnswer;
    }

    public void setIdealAnswer(String idealAnswer) {
        this.idealAnswer = idealAnswer;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getImprovementTips() {
        return improvementTips;
    }

    public void setImprovementTips(String improvementTips) {
        this.improvementTips = improvementTips;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getNextQuestion() {
        return nextQuestion;
    }

    public void setNextQuestion(String nextQuestion) {
        this.nextQuestion = nextQuestion;
    }

}