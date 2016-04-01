package com.sdsmdg.harshit.customview;

/**
 * Created by Apple on 15/03/16.
 */
public class AccelerometerData
{
    double x=0 , y=0 ;
    double ax=0 , ay=0 , az=0 ;
    double vx = 0, vy = 0, vz = 0;

    public AccelerometerData()
    {

    }

    public void update(double ax, double ay, double az)
    {
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    public void calculateVelocity()
    {
        vx -= ax;
        vy += ay;
        vz += az;
    }

}

