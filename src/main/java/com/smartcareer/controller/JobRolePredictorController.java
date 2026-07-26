package com.smartcareer.controller;

import com.smartcareer.dto.JobPredictionResult;
import com.smartcareer.service.JobRolePredictorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/job-role")
@CrossOrigin
public class JobRolePredictorController {

    @Autowired
    private JobRolePredictorService service;

    @PostMapping("/predict")
    public JobPredictionResult predict(
            @RequestParam("file")
            MultipartFile file
    ) {

        return service.predictRole(file);
    }
}
