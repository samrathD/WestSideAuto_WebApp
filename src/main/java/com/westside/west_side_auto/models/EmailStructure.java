package com.westside.west_side_auto.models;


public class EmailStructure {
    public String subject;
    public String message;

    public EmailStructure() {
    }
    
    public EmailStructure(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }
    public String getMessage() {
        return message;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
