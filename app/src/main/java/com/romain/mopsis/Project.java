package com.romain.mopsis;

public class Project {
    private String name;
    private int amount;
    private String type;

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

    public int getAmount(){
        return this.amount;
    }

    public void setAmount(int amount){this.amount = amount;}

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String toString(){
        return this.name + String.valueOf(this.amount) + this.type;
    }
}
