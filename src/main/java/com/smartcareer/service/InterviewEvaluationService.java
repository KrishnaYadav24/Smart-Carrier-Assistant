package com.smartcareer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcareer.model.InterviewEvaluationRequest;

@Service
public class InterviewEvaluationService {

    @Autowired
    private GeminiAIService geminiAIService;

    public String evaluateAnswer(

            InterviewEvaluationRequest request

    ) {

        String prompt =

                "You are a Senior Technical Interviewer.\n\n"

                        +

                        "Evaluate the candidate's answer professionally.\n\n"

                        +

                        "Domain : "

                        +

                        request.getDomain()

                        +

                        "\n"

                        +

                        "Difficulty : "

                        +

                        request.getDifficulty()

                        +

                        "\n\n"

                        +

                        "Interview Question :\n"

                        +

                        request.getQuestion()

                        +

                        "\n\n"

                        +

                        "Candidate Answer :\n"

                        +

                        request.getAnswer()

                        +

                        "\n\n"

                        +

                        "Return response EXACTLY in this format:\n\n"

                        +

                        "Interview Score : X/10\n\n"

                        +

                        "Correctness :\n"

                        +

                        "Explain whether answer is correct.\n\n"

                        +

                        "Missing Concepts :\n"

                        +

                        "Mention missing technical points.\n\n"

                        +

                        "Ideal Answer :\n"

                        +

                        "Give ideal answer within 60 words.\n\n"

                        +

                        "Communication :\n"

                        +

                        "Evaluate communication quality.\n\n"

                        +

                        "Confidence Level :\n"

                        +

                        "Beginner / Intermediate / Advanced\n\n"

                        +

                        "Improvement Tips :\n"

                        +

                        "Provide practical suggestions.\n\n"

                        +

                        "Motivation :\n"

                        +

                        "Motivate candidate positively.\n\n"

                        +

                        "Next Interview Question :\n"

                        +

                        "Generate ONLY ONE next interview question.\n\n"

                        +

                        "Keep total response between 200 and 250 words.";

        return geminiAIService.generatePrompt(prompt);

    }

}