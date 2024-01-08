package com.example.logd;

public class ListData {

    private String date, time, remarks, schoolID;


    public ListData(String date, String time, String remarks, String schoolID) {

        this.date = date;
        this.time = time;
        this.remarks = remarks;
        this.schoolID =schoolID;

    }
    public  String getTime(){
        return time;
    }
    protected void setTime(String time){
        this.time = time;
    }
    public  String getDate(){
        return date;
    }
    protected void setDate(String date){
        this.date = date;
    }
    public  String getRemarks(){
        return remarks;
    }
    protected void setRemarks(String remarks){
        this.remarks = remarks;
    }
    public  String getSchoolID(){
        return schoolID;
    }
    protected void setSchoolID(String schoolID){
        this.schoolID = schoolID;
    }
}
