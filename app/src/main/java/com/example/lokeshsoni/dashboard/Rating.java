package com.example.lokeshsoni.dashboard;

/**
 * Created by adi on 30/03/18.
 */

public class Rating {
    String name;
    int positive;
    public Rating(String name, int positive){
        this.name = name;
        this.positive = positive;

    }

    public int getPositive() {
        return positive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public String getName() {
        return name;
    }
    public int getPercentage(){
        return (((int) positive)/5)*100;
    }
}

