package com.smartcareer.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobRecommendationService {

    /*
        JOB DATABASE
     */

    private final Map<String,List<String>> jobSkills =

            new HashMap<>();

    public JobRecommendationService(){

        /*
            SOFTWARE JOBS
         */

        jobSkills.put(

                "Backend Developer",

                Arrays.asList(

                        "java",
                        "spring",
                        "api",
                        "mysql"
                )
        );

        jobSkills.put(

                "Frontend Developer",

                Arrays.asList(

                        "html",
                        "css",
                        "javascript",
                        "react"
                )
        );

        jobSkills.put(

                "AI Engineer",

                Arrays.asList(

                        "python",
                        "ai",
                        "machine learning",
                        "nlp"
                )
        );

        jobSkills.put(

                "Full Stack Developer",

                Arrays.asList(

                        "java",
                        "react",
                        "mysql",
                        "html",
                        "css"
                )
        );

        /*
            CIVIL ENGINEERING
         */

        jobSkills.put(

                "Civil Site Engineer",

                Arrays.asList(

                        "autocad",
                        "construction",
                        "surveying",
                        "site"
                )
        );

        jobSkills.put(

                "Structural Engineer",

                Arrays.asList(

                        "staad",
                        "autocad",
                        "design"
                )
        );

        jobSkills.put(

                "Project Engineer",

                Arrays.asList(

                        "construction",
                        "planning",
                        "site",
                        "project"
                )
        );

        /*
            MECHANICAL
         */

        jobSkills.put(

                "Mechanical Design Engineer",

                Arrays.asList(

                        "solidworks",
                        "cad",
                        "manufacturing"
                )
        );

        jobSkills.put(

                "Production Engineer",

                Arrays.asList(

                        "manufacturing",
                        "production",
                        "mechanical"
                )
        );

        /*
            MBA
         */

        jobSkills.put(

                "Business Analyst",

                Arrays.asList(

                        "business",
                        "finance",
                        "management"
                )
        );

        jobSkills.put(

                "Marketing Executive",

                Arrays.asList(

                        "marketing",
                        "sales",
                        "leadership"
                )
        );

        /*
            MEDICAL
         */

        jobSkills.put(

                "Healthcare Assistant",

                Arrays.asList(

                        "medical",
                        "patient",
                        "healthcare"
                )
        );

        jobSkills.put(

                "Clinical Specialist",

                Arrays.asList(

                        "clinical",
                        "hospital",
                        "surgery"
                )
        );
    }

    /*
        DYNAMIC RECOMMENDATION ENGINE
     */

    public List<String> recommendJobs(

            List<String> detectedSkills
    ) {

        Map<String,Integer> scores =
                new HashMap<>();

        /*
            CALCULATE MATCH SCORE
         */

        for(

            String job :

            jobSkills.keySet()
        ){

            int score = 0;

            List<String> requiredSkills =

                    jobSkills.get(job);

            for(

                String skill :

                requiredSkills
            ){

                if(

                    detectedSkills.contains(skill)
                ){

                    score += 25;
                }
            }

            scores.put(
                    job,
                    score
            );
        }

        /*
            SORT JOBS BY SCORE
         */

        List<Map.Entry<String,Integer>> sortedJobs =

                new ArrayList<>(

                        scores.entrySet()
                );

        sortedJobs.sort(

                (a,b) ->

                        b.getValue()
                        -
                        a.getValue()
        );

        /*
            FINAL RECOMMENDATIONS
         */

        List<String> recommendations =
                new ArrayList<>();

        for(

            Map.Entry<String,Integer> entry :

            sortedJobs
        ){

            if(entry.getValue() > 0){

                recommendations.add(

                        entry.getKey()

                        +

                        " - "

                        +

                        entry.getValue()

                        +

                        "% Match"
                );
            }
        }

        /*
            FALLBACK
         */

        if(recommendations.isEmpty()){

            recommendations.add(

                    "General Professional Role"
            );
        }

        return recommendations;
    }
}