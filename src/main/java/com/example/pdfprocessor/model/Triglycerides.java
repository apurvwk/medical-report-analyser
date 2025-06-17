package com.example.pdfprocessor.model;

public class Triglycerides implements MedicalCheckup {
    @Override
    public String checkupName() {
        return "TRIGLYCERIDES";
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
        return "< 150";
    }
}