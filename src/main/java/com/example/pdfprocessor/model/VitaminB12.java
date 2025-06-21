package com.example.pdfprocessor.model;

public class VitaminB12 implements Report {
    private String vitaminB12;

    public String getVitaminB12() {
        return vitaminB12;
    }

    public void setVitaminB12(String vitaminB12) {
        this.vitaminB12 = vitaminB12;
    }
    @Override
    public String toString() {
        return "VitaminB12{" +
                "vitaminB12='" + vitaminB12 + '\'' +
                '}';
    }
}