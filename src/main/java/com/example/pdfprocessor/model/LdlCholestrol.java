package com.example.pdfprocessor.model;


public class LdlCholestrol implements MedicalCheckup {
    @Override
    public String checkupName() {
        return "LDL CHOLESTEROL ";
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
        return "< 100";
    }
}