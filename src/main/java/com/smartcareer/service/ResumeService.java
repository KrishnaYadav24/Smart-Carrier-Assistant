package com.smartcareer.service;

import com.smartcareer.domain.Resume;
import com.smartcareer.dto.ResumeResult;
import com.smartcareer.repository.ResumeRepository;
import com.smartcareer.utils.PdfExtractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ResumeService {

    @Autowired
    private ResumeAnalyzerService analyzerService;

    @Autowired
    private ResumeRepository resumeRepository;

    public ResumeResult analyzeResume(

            MultipartFile file,

            String username
    ) {

        try {

            /*
                CREATE TEMP FILE
             */

            File tempFile =
                    File.createTempFile(
                            "resume",
                            ".pdf"
                    );

            /*
                SAVE FILE
             */

            file.transferTo(
                    tempFile
            );

            /*
                EXTRACT TEXT
             */

            String text =
                    PdfExtractor.extractText(
                            tempFile
                    );

            /*
                DEBUG
             */

            System.out.println(
                    "===== EXTRACTED PDF TEXT ====="
            );

            System.out.println(
                    text
            );

            System.out.println(
                    "=============================="
            );

            /*
                CHECK EMPTY PDF
             */

            if(
                text == null
                ||
                text.trim().isEmpty()
            ){

                throw new RuntimeException(
                        "No text found in PDF. Use a text-based PDF, not a scanned image PDF."
                );
            }

            /*
                ANALYZE
             */

            ResumeResult result =
                    analyzerService.analyze(
                            text
                    );

            /*
                CHECK RESULT
             */

            if(result == null){

                throw new RuntimeException(
                        "Resume analysis returned null."
                );
            }

            /*
                SAVE DATABASE
             */

            Resume resume =
                    new Resume();

            resume.setUsername(
                    username
            );

            resume.setFileName(
                    file.getOriginalFilename()
            );

            resume.setScore(
                    result.getScore()
            );

            resume.setAtsScore(
                    result.getAtsScore()
            );

            resume.setDetectedSkills(

                    result.getDetectedSkills()
                            .toString()
            );

            resume.setMissingSkills(

                    result.getMissingSkills()
                            .toString()
            );

            resume.setRecommendedJobs(

                    result.getRecommendedJobs()
                            .toString()
            );

            resume.setSuggestion(
                    result.getSuggestion()
            );

            resume.setInterviewQuestions(
                    result.getInterviewQuestions()
            );

            /*
                SAVE
             */

            resumeRepository.save(
                    resume
            );

            /*
                DELETE TEMP FILE
             */

            tempFile.delete();

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(

                    "Resume analysis failed: "

                    +

                    e.getMessage()
            );
        }
    }
}