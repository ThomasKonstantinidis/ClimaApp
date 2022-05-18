package com.example.project;

public class State {

    private int temperature;
    private  String airType, airIntensity;

    State(int temp, String type, String intensity){
        this.temperature = temp;
        this.airType = type;
        this.airIntensity = intensity;
    }

    public int getTemperature(){return this.temperature;}

    public String getAirType(){return this.airType;}
    public void setAirType(String type){this.airType = type;}

    public String getAirIntensity(){return this.airIntensity;}
    public void setAirIntensity(String temp){this.airIntensity = temp;}
}
