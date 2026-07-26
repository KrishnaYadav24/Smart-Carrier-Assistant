package com.smartcareer.controller;

import com.smartcareer.model.ResumeRequest;
import com.smartcareer.service.ResumePdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume-builder")
@CrossOrigin
public class ResumeBuilderController {

@Autowired
private ResumePdfService pdfService;

@PostMapping("/generate")
public ResponseEntity<byte[]> generateResume(
        @RequestBody ResumeRequest request
) {

    try {

        byte[] pdfBytes =
                pdfService.generatePdf(
                        request
                );

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=resume.pdf"
                )

                .contentType(
                        MediaType.APPLICATION_PDF
                )

                .body(pdfBytes);

    } catch (Exception e) {

        e.printStackTrace();

        return ResponseEntity.internalServerError()
                .body(null);
    }
}

}
