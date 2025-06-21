package com.example.pdfprocessor.service;

import com.example.pdfprocessor.model.LipidProfile;
import com.example.pdfprocessor.model.Report;
import com.example.pdfprocessor.model.VitaminB12;
import com.example.pdfprocessor.model.VitaminD;
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
            System.out.println("Exception occurred while calling upload: " + e);
        }
        return "";
    }

    private List<Report> createReports(String[] str) {
        List<Report> reports = new ArrayList<>();
        LipidProfile lipidProfile = new LipidProfile();
        VitaminD vitaminD = new VitaminD();
        VitaminB12 vitaminB12 = new VitaminB12();
        Optional<String> optionalName = Arrays.stream(str).filter(s -> s.startsWith("Patient Name :")).map(nameString-> nameString.split(":")[1]).findFirst();
        String name = optionalName.orElse("DefaultName");
        LocalDate localDate= null;
        for(String data:str){
            populateLipidProfileData(data, lipidProfile);
            populateVitaminD3Data(data,vitaminD);
            populateVitaminB12Data(data,vitaminB12);
            if(data.startsWith("Reported :")){
                String[] values = data.split(" ");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
                String dateString = (values[2]).trim();
                localDate = LocalDate.parse(dateString, formatter);
            }
        }
        reports.add(lipidProfile);
        reports.add(vitaminB12);
        reports.add(vitaminD);
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

    private static void populateLipidProfileData(String data, LipidProfile lipidProfile) {
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
    }

    private static void populateVitaminB12Data(String data, VitaminB12 vitaminB12) {
        if(data.startsWith("VITAMIN B12 , SERUM")){
            String[] values = data.split(" ");
            vitaminB12.setVitaminB12(values[4]);
        }
    }

    private static void populateVitaminD3Data(String data, VitaminD vitaminD) {
        if(data.startsWith("VITAMIN D (25 - OH VITAMIN D)")){
            String[] values = data.split(" ");
            vitaminD.setVitaminD(values[6]);
        }
    }
}