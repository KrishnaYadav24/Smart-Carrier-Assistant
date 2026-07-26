package com.smartcareer.controller;

import com.smartcareer.domain.Resume;

import com.smartcareer.repository.ResumeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin
public class HistoryController {

    @Autowired
    private ResumeRepository resumeRepository;

    @GetMapping("/{username}")
    public List<Resume> getUserHistory(

            @PathVariable
            String username
    ){

        return resumeRepository.findByUsername(
                username
        );
    }
}