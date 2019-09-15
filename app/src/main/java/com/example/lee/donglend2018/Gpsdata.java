package com.example.lee.donglend2018;

import java.io.Serializable;

public class Gpsdata implements Serializable {
    public String x,y;
    public Gpsdata (){};
    public Gpsdata (String x, String y){
        this.x=x;
        this.y=y;
    }
}
