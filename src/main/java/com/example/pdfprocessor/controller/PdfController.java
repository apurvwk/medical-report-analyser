package com.example.pdfprocessor.controller;

import com.example.pdfprocessor.service.PdfProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfProcessingService pdfProcessingService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        System.out.println("uploadPdf method called");

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            if (!file.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest().build();
            }

            String processedDocument = pdfProcessingService.processPdfFile(file);
            return ResponseEntity.ok(processedDocument);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("test method called");
        return ResponseEntity.ok("Call successfully made");
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2() {
        System.out.println("test method called");
        return ResponseEntity.ok("Call successfully made");
    }
}