package com.mhchoudh.appeal.model;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

public class Appeal {
   
    private final List<ComposeAppeal> items;
    @XmlTransient
    private AppealStatus status = AppealStatus.NotSent;
    
    public Appeal(AppealStatus status, List<ComposeAppeal> items) {
        this.items = items;
        this.status = status;
    }
    
    public List<ComposeAppeal> getItems() {
        return items;
    }

    public double calculateCost() {
        double total = 0.0;
        
        total = 5.0;
        
        return total;
    }

    public void setStatus(AppealStatus status) {
        this.status = status;
    }

    public AppealStatus getStatus() {
        return status;
    }
    
     public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Status of the appeal: " + status + "\n");
        int count = 0;
        for(ComposeAppeal i : items) {
            sb.append("Appeal item " + count++ +": " + i.toString()+ "\n");
        }
        return sb.toString();
    }
}