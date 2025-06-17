package com.example.pdfprocessor.model;

public class TotalCholestrol implements MedicalCheckup {
    @Override
    public String checkupName() {
        return "TOTAL CHOLESTEROL";
    }

    @Override
    public String result() {
        return "";
    }

    @Override
    public String unit() {
        return "mg/dL";
    }

    @Override
    public String reference() {
        return "< 200";
    }
}