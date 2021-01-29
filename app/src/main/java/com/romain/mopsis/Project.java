package com.romain.mopsis;

public class Project {
    private String name;
    private String type;
    private double amount;
    private double rate;
    private double duration;
    private double loanType;
    private double monthly;
    private String status;

    public Project(String name, int amount, String type){
        this.name = name;
        this.amount = amount;
        this.type = type;
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

    public String getType(){
        return this.type;
    }

    public void setType(String type){
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

    public double getLoanType() {
        return loanType;
    }

    public void setLoanType(double loanType) {
        this.loanType = loanType;
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
