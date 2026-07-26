package com.smartcareer.controller;

import com.smartcareer.service.RoadmapService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roadmap")
@CrossOrigin
public class RoadmapController {

    @Autowired
    private RoadmapService roadmapService;

    @GetMapping("/generate")
    public String generateRoadmap(

            @RequestParam String role
    ){

        return roadmapService
                .generateRoadmap(role);
    }
} 
