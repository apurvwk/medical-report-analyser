package com.example.pdfprocessor.service;

import com.example.pdfprocessor.model.LipidProfile;
import com.example.pdfprocessor.model.Report;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PdfProcessingService {

    private Map<String, Map<LocalDate,List<Report>>> userReportData = new HashMap<>();

    public String processPdfFile(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String content = stripper.getText(document);
            String[] str = content.split("\\r?\\n");
            createReports(str);
            System.out.println(userReportData);
            return content;
        } catch (Exception e) {
            System.out.println("Exception occured while calling upload: " + e);
        }
        return "";
    }

    private List<Report> createReports(String[] str) {
        List<Report> reports = new ArrayList<>();
        LipidProfile lipidProfile = new LipidProfile();
        Optional<String> optionalName = Arrays.stream(str).filter(s -> s.startsWith("Patient Name :")).map(nameString-> nameString.split(":")[1]).findFirst();
        String name = optionalName.orElse("DefaultName");
        LocalDate localDate= null;
        for(String data:str){

            if(data.startsWith("TOTAL CHOLESTEROL") && (data.endsWith("CHOD-PAD") || data.endsWith("CHO-POD")) ){
                String[] values = data.split(" ");
                lipidProfile.setTotalCholesterol(values[2]);
            }
            if(data.startsWith("TRIGLYCERIDES") && (data.endsWith("GPO-PAP")|| data.endsWith("GPO-POD"))){
                String[] values = data.split(" ");
                lipidProfile.setTriglycerides(values[1]);

            }
            if(data.startsWith("LDL CHOLESTEROL") && data.endsWith("Calculated")){
                String[] values = data.split(" ");
                lipidProfile.setLdlCholesterol(values[2]);
            }
            if(data.startsWith("Reported :")){
                //Reported : 15/Jun/2025 08:18AM
                String[] values = data.split(" ");
                // Define the pattern to match the input string
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
                // Parse the string into a LocalDateTime
                String dateString = (values[2]).trim();
                localDate = LocalDate.parse(dateString, formatter);
            }
        }
        reports.add(lipidProfile);
        if(userReportData.get(name) != null){
            userReportData.get(name).put(localDate,reports);
        }else{
            assert localDate != null;
            Map<LocalDate,List<Report>> reportsDateMap = new HashMap<>();
            reportsDateMap.put(localDate,reports);
            userReportData.put(name,reportsDateMap);
        }
        return reports;
    }
}