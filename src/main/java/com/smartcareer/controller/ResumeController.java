package com.smartcareer.controller;

import com.smartcareer.dto.ResumeResult;
import com.smartcareer.service.ResumeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public ResumeResult uploadResume(

            @RequestParam("file")
            MultipartFile file,

            @RequestParam("username")
            String username
    ) {

        System.out.println(
                "UPLOAD API HIT"
        );

        return resumeService.analyzeResume(
                file,
                username
        );
    }
}