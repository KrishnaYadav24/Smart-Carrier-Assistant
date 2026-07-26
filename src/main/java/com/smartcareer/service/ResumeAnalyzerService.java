package com.smartcareer.service;

import com.smartcareer.dto.ResumeResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeAnalyzerService {

    @Autowired
    private ATSService atsService;

    @Autowired
    private GeminiAIService geminiAIService;

    @Autowired
    private JobRecommendationService jobRecommendationService;

    @Autowired
    private AIInterviewService aiInterviewService;

    /*
        SOFTWARE SKILLS
     */

    private final String[] SOFTWARE_SKILLS = {

            "java",
            "python",
            "sql",
            "mysql",
            "html",
            "css",
            "javascript",
            "react",
            "spring",
            "api",
            "nlp",
            "ai",
            "machine learning",
            "web development",
            "android",
            "php",
            "c"
    };

    /*
        CIVIL SKILLS
     */

    private final String[] CIVIL_SKILLS = {

            "autocad",
            "staad",
            "construction",
            "surveying",
            "site",
            "civil"
    };

    /*
        MECHANICAL SKILLS
     */

    private final String[] MECHANICAL_SKILLS = {

            "solidworks",
            "manufacturing",
            "cad",
            "thermodynamics",
            "mechanical"
    };

    /*
        MBA SKILLS
     */

    private final String[] MBA_SKILLS = {

            "marketing",
            "finance",
            "business",
            "sales",
            "leadership"
    };

    /*
        MEDICAL SKILLS
     */

    private final String[] MEDICAL_SKILLS = {

            "hospital",
            "patient",
            "clinical",
            "medical",
            "healthcare"
    };

    public ResumeResult analyze(String text) {

        /*
            LOWERCASE
         */

        text = text.toLowerCase();

        String[] requiredSkills;

        String domain;

        /*
            DOMAIN DETECTION
         */

        if(

            text.contains("autocad")
            ||
            text.contains("construction")
            ||
            text.contains("surveying")
            ||
            text.contains("civil")
        ){

            requiredSkills =
                    CIVIL_SKILLS;

            domain =
                    "Civil Engineering";
        }

        else if(

            text.contains("solidworks")
            ||
            text.contains("manufacturing")
            ||
            text.contains("mechanical")
        ){

            requiredSkills =
                    MECHANICAL_SKILLS;

            domain =
                    "Mechanical Engineering";
        }

        else if(

            text.contains("marketing")
            ||
            text.contains("finance")
            ||
            text.contains("business")
            ||
            text.contains("sales")
        ){

            requiredSkills =
                    MBA_SKILLS;

            domain =
                    "MBA";
        }

        else if(

            text.contains("hospital")
            ||
            text.contains("medical")
            ||
            text.contains("clinical")
        ){

            requiredSkills =
                    MEDICAL_SKILLS;

            domain =
                    "Medical";
        }

        else{

            requiredSkills =
                    SOFTWARE_SKILLS;

            domain =
                    "Software Engineering";
        }

        /*
            SKILL ANALYSIS
         */

        List<String> detectedSkills =
                new ArrayList<>();

        List<String> missingSkills =
                new ArrayList<>();

        int score = 0;

        for(String skill : requiredSkills){

            if(text.contains(skill)){

                detectedSkills.add(skill);

                score += 10;

            }else{

                missingSkills.add(skill);
            }
        }

        /*
            EXTRA ATS CHECKS
         */

        if(text.contains("education")){

            score += 10;
        }

        if(text.contains("experience")){

            score += 10;
        }

        if(text.contains("project")){

            score += 10;
        }

        if(text.contains("certification")){

            score += 10;
        }

        if(text.contains("@")){

            score += 5;
        }

        /*
            MAX SCORE
         */

        if(score > 100){

            score = 100;
        }

        /*
            JOB RECOMMENDATION
         */

        List<String> recommendedJobs =

                jobRecommendationService
                        .recommendJobs(
                                detectedSkills
                        );

        /*
            ATS SCORE
         */

        int atsScore =

                atsService.calculateATSScore(
                        text
                );

        /*
            AI SUGGESTION
         */

        String suggestion =
                "No AI suggestions generated.";

        try{

           suggestion =

    geminiAIService
            .generateResumeAnalysis(
                    text
            );
        }catch(Exception e){

            e.printStackTrace();
        }

        /*
            INTERVIEW QUESTIONS
         */

        String interviewQuestions =
                "No interview questions generated.";

        try{

            interviewQuestions =

                    aiInterviewService
                            .generateInterviewQuestions(

                                    text,

                                    domain
                            );

        }catch(Exception e){

            e.printStackTrace();
        }

        /*
            FINAL SUGGESTION
         */

        suggestion =

                "Detected Domain: "

                +

                domain

                +

                "\n\n"

                +

                suggestion;

        /*
            RETURN RESULT
         */

        return new ResumeResult(

                score,

                detectedSkills,

                missingSkills,

                recommendedJobs,

                atsScore,

                suggestion,

                interviewQuestions
        );
    }
}