package com.smartcareer.controller;

import com.smartcareer.dto.ATSMatchResult;
import com.smartcareer.service.ATSMatchingService;
import com.smartcareer.utils.PdfExtractor;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/ats")
@CrossOrigin
public class ATSController {

    @Autowired
    private ATSMatchingService
            atsMatchingService;

    @PostMapping(

            value = "/match",

            consumes =
            MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ATSMatchResult matchResume(

            @RequestParam("file")
            MultipartFile file,

            @RequestParam("jobDescription")
            String jobDescription
    ) {

        try{

            File tempFile =
                    File.createTempFile(
                            "resume",
                            ".pdf"
                    );

            file.transferTo(tempFile);

            String resumeText =
                    PdfExtractor.extractText(
                            tempFile
                    );

            return atsMatchingService
                    .compareResumeWithJD(

                            resumeText,

                            jobDescription
                    );

        }catch(Exception e){

            e.printStackTrace();

            return null;
        }
    }
}