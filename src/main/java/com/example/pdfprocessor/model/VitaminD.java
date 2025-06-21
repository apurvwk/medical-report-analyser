package com.example.pdfprocessor.model;

public class VitaminD implements Report {
    private String vitaminD;

    public String getVitaminD() {
        return vitaminD;
    }

    public void setVitaminD(String vitaminD) {
        this.vitaminD = vitaminD;
    }

    @Override
    public String toString() {
        return "VitaminD{" +
                "vitaminD='" + vitaminD + '\'' +
                '}';
    }
}