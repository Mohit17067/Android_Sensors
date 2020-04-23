package com.example.a2017067_assignment3;

public class Wifi {
    private String timestamp;
    private String ap_names;
    private String ap_strengths;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Wifi(String timestamp, String ap_names, String ap_strengths){
        this.timestamp = timestamp;
        this.ap_names = ap_names;
        this.ap_strengths = ap_strengths;
    }

    public String getAp_names() {
        return ap_names;
    }

    public void setAp_names(String ap_names) {
        this.ap_names = ap_names;
    }

    public String getAp_strengths() {
        return ap_strengths;
    }

    public void setAp_strengths(String ap_strengths) {
        this.ap_strengths = ap_strengths;
    }
}
