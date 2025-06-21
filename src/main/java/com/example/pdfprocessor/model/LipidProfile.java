package com.example.pdfprocessor.model;

public class LipidProfile implements Report {
    private String totalCholesterol;
    private String triglycerides;
    private String ldlCholesterol;

    public String getTotalCholesterol() {
        return totalCholesterol;
    }

    public void setTotalCholesterol(String totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }

    public String getTriglycerides() {
        return triglycerides;
    }

    public void setTriglycerides(String triglycerides) {
        this.triglycerides = triglycerides;
    }

    public String getLdlCholesterol() {
        return ldlCholesterol;
    }

    public void setLdlCholesterol(String ldlCholesterol) {
        this.ldlCholesterol = ldlCholesterol;
    }

    @Override
    public String toString() {
        return "LipidProfile{" +
                "totalCholesterol='" + totalCholesterol + '\'' +
                ", triglycerides='" + triglycerides + '\'' +
                ", ldlCholesterol='" + ldlCholesterol + '\'' +
                '}';
    }
}