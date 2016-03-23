package com.sdsmdg.harshit.customview;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    Rocket rocket;
    Surface surface;
    private ViewGroup mRootLayout;
    private double _xDelta;
    private double _yDelta;

    SensorManager senSensorManager;
    Sensor senAccelerometer;
    AccelerometerData Adata;
    double gravity[];
    String LOG_TAG = "harshit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootLayout = (ViewGroup) findViewById(R.id.root);

        surface = (Surface) findViewById(R.id.rocket);

        //Declaring the accelerometer
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        Adata = new AccelerometerData();

        gravity = new double[3];


        final MoveRocket moveRocket = new MoveRocket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        Thread.sleep(100);
                    } catch(Exception e) { }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            moveRocket();
                        }
                    });
                }
            }
        }).start();

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate



        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            final double alpha = 0.8;

            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            Adata.update(gravity[0], gravity[1], gravity[2]);
        }

    }

    public void moveRocket()
    {
        surface.update(Adata);
    }

}
