package com.smartcareer.controller;

import com.smartcareer.model.InterviewEvaluationRequest;
import com.smartcareer.service.InterviewEvaluationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interview")
@CrossOrigin
public class InterviewEvaluationController {

    @Autowired
    private InterviewEvaluationService evaluationService;

    @PostMapping("/evaluate")
    public String evaluateAnswer(

            @RequestBody InterviewEvaluationRequest request

    ) {

        return evaluationService.evaluateAnswer(request);

    }

}