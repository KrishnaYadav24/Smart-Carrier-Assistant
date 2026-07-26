package com.smartcareer.dto;

import java.util.List;

public class ATSMatchResult {

    private int matchScore;

    private List<String> matchedSkills;

    private List<String> missingSkills;

    private String suggestion;

    public ATSMatchResult(

            int matchScore,

            List<String> matchedSkills,

            List<String> missingSkills,

            String suggestion
    ) {

        this.matchScore = matchScore;

        this.matchedSkills = matchedSkills;

        this.missingSkills = missingSkills;

        this.suggestion = suggestion;
    }

    public int getMatchScore() {

        return matchScore;
    }

    public List<String> getMatchedSkills() {

        return matchedSkills;
    }

    public List<String> getMissingSkills() {

        return missingSkills;
    }

    public String getSuggestion() {

        return suggestion;
    }
}