package com.smartcareer.controller;

import com.smartcareer.model.InterviewRequest;
import com.smartcareer.service.AIInterviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interview")
@CrossOrigin
public class InterviewController {

    @Autowired
    private AIInterviewService interviewService;

    @PostMapping("/start")
    public String startInterview(

            @RequestBody InterviewRequest request

    ) {

        return interviewService.generateFirstQuestion(request);

    }

}