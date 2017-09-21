package com.company.backend;

import java.util.List;

public class Payment {
    private String creditor;
    private List<String> credited;
    private Double sum;

    public Payment(String creditor, List<String> credited, Double sum) {
        this.creditor = creditor;
        this.credited = credited;
        this.sum = sum;
    }

    public String getCreditor() {
        return creditor;
    }

    public List<String> getCredited() {
        return credited;
    }

    public Double getSum() {
        return sum;
    }
}
