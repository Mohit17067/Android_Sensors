package com.example.a2017067_assignment3;

public class Accelerometer {
    private String timestamp;
    private String xvalue;
    private String yvalue;
    private String zvalue;

    public Accelerometer(String timestamp, String xvalue, String yvalue, String zvalue){
        this.timestamp = timestamp;
        this.xvalue = xvalue;
        this.yvalue = yvalue;
        this.zvalue = zvalue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getXvalue() {
        return xvalue;
    }

    public void setXvalue(String xvalue) {
        this.xvalue = xvalue;
    }

    public String getYvalue() {
        return yvalue;
    }

    public void setYvalue(String yvalue) {
        this.yvalue = yvalue;
    }

    public String getZvalue() {
        return zvalue;
    }

    public void setZvalue(String zvalue) {
        this.zvalue = zvalue;
    }
}
