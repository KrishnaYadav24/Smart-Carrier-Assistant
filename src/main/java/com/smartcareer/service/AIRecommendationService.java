package com.smartcareer.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AIRecommendationService {

    public String generateSuggestion(

            List<String> missingSkills
    ) {

        StringBuilder suggestion =
                new StringBuilder();

        /*
            NO MISSING SKILLS
         */

        if(missingSkills.isEmpty()){

            return
                "Excellent resume. " +
                "You are ready for software development roles.";
        }

        /*
            DYNAMIC SUGGESTIONS
         */

        suggestion.append(
            "To improve your resume, "
        );

        if(
            missingSkills.contains("java")
        ){

            suggestion.append(

                "learn Java programming, "
            );
        }

        if(
            missingSkills.contains("spring")
        ){

            suggestion.append(

                "build Spring Boot projects, "
            );
        }

        if(
            missingSkills.contains("mysql")
        ){

            suggestion.append(

                "improve database skills, "
            );
        }

        if(
            missingSkills.contains("html")
            ||
            missingSkills.contains("css")
            ||
            missingSkills.contains("javascript")
        ){

            suggestion.append(

                "practice frontend development, "
            );
        }

        if(
            missingSkills.contains("react")
        ){

            suggestion.append(

                "learn React framework, "
            );
        }

        if(
            missingSkills.contains("api")
        ){

            suggestion.append(

                "build REST API projects, "
            );
        }

        suggestion.append(

            "and add more real-world projects."
        );

        return suggestion.toString();
    }
}