package com.romain.mopsis;

public class Project {
    private String name;
    private String ImageTypeString;
    private double amount;
    private double rate;
    private double duration;
    private int type;
    private double monthly;
    private String status;

    public Project(String name, int amount,String ImageTypeString){
        this.name = name;
        this.amount = amount;
        this.ImageTypeString = ImageTypeString;
        this.amount = 0;
        this.rate = 0;
        this.duration = 0;
        this.type = 0;
        this.monthly = 0;
    }

    public String getImageTypeString() {
        return ImageTypeString;
    }

    public void setImageTypeString(String imageTypeString) {
        this.ImageTypeString = imageTypeString;
    }


    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public double getAmount(){
        return this.amount;
    }

    public void setAmount(double amount){this.amount = amount;}

    public int getType(){
        return this.type;
    }

    public void setType(int type){
        this.type = type;
    }

    public String toString(){
        return this.name + String.valueOf(this.amount) + this.type;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }


    public double getMonthly() {
        return monthly;
    }

    public void setMonthly(double monthly) {
        this.monthly = monthly;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
