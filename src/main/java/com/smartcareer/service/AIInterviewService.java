package com.smartcareer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcareer.model.InterviewRequest;

@Service
public class AIInterviewService {

        @Autowired
        private GeminiAIService geminiAIService;

        /*
         * =============================================
         * OLD METHOD
         * (For Interview Preparation Page)
         * =============================================
         */

        public String generateInterviewQuestions(

                        String resumeText,

                        String domain

        ) {

                String prompt =

                                "You are an expert technical interviewer.\n\n"

                                                +

                                                "Generate professional interview questions "

                                                +

                                                "for the following candidate.\n\n"

                                                +

                                                "Domain : "

                                                +

                                                domain

                                                +

                                                "\n\n"

                                                +

                                                "Resume :\n"

                                                +

                                                resumeText

                                                +

                                                "\n\n"

                                                +

                                                "Generate:\n"

                                                +

                                                "1. Five Technical Questions\n"

                                                +

                                                "2. Three HR Questions\n"

                                                +

                                                "3. Two Project Based Questions\n"

                                                +

                                                "4. Five Career Improvement Tips\n";

                return geminiAIService.generatePrompt(prompt);

        }

        /*
         * =============================================
         * NEW METHOD
         * AI MOCK INTERVIEW
         * FIRST QUESTION
         * =============================================
         */

        public String generateFirstQuestion(

                        InterviewRequest request

        ) {

                String prompt =

                                "You are a Senior Software Engineer conducting a real technical interview.\n\n"

                                                +

                                                "Candidate Details\n"

                                                +

                                                "Domain : "

                                                +

                                                request.getDomain()

                                                +

                                                "\n"

                                                +

                                                "Experience : "

                                                +

                                                request.getExperience()

                                                +

                                                "\n"

                                                +

                                                "Difficulty : "

                                                +

                                                request.getDifficulty()

                                                +

                                                "\n\n"

                                                +

                                                "Resume :\n"

                                                +

                                                request.getResumeText()

                                                +

                                                "\n\n"

                                                +

                                                "Rules:\n"

                                                +

                                                "1. Ask ONLY ONE interview question.\n"

                                                +

                                                "2. Do NOT provide answer.\n"

                                                +

                                                "3. Do NOT explain anything.\n"

                                                +

                                                "4. Question should match candidate profile.\n"

                                                +

                                                "5. Keep question interview level.\n"

                                                +

                                                "6. Return ONLY the question.";

                return geminiAIService.generatePrompt(prompt);

        }

}