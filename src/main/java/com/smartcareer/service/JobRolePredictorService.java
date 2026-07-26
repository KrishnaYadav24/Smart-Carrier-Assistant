package com.smartcareer.service;

import com.smartcareer.dto.JobPredictionResult;
import com.smartcareer.utils.PdfExtractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;

@Service
public class JobRolePredictorService {

    @Autowired
    private GeminiAIService geminiAIService;

    public JobPredictionResult predictRole(
            MultipartFile file
    ) {

        try {

            File temp =
                    File.createTempFile(
                            "resume",
                            ".pdf"
                    );

            file.transferTo(temp);

            String text =
                    PdfExtractor.extractText(
                            temp
                    );

            String prompt =

                    "Analyze this resume.\n\n"

                    +

                    "Predict top 5 job roles.\n"

                    +

                    "Explain why.\n\n"

                    +

                    text;

            String response =

                    geminiAIService
                            .generatePrompt(
                                    prompt
                            );

            return new JobPredictionResult(

                    Arrays.asList(

                            "Software Engineer",

                            "Java Developer",

                            "Backend Developer",

                            "Spring Boot Developer",

                            "Full Stack Developer"
                    ),

                    response
            );

        } catch(Exception e){

            e.printStackTrace();

            return new JobPredictionResult(

                    Arrays.asList(),

                    "Prediction failed."
            );
        }
    }
}