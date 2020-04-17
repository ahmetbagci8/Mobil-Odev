package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends Activity implements SensorEventListener {
    TextView luxText,accText,timeText;
    private SensorManager sensorMgr;
    private Sensor sLight,sAccelerometer;
    private long lastUpdate;
    private boolean noMovement;
    private View sensorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        luxText=findViewById(R.id.lux_text);
        accText=findViewById(R.id.acc_text);
        timeText=findViewById(R.id.time_text);
        sensorView=findViewById(R.id.sensor_background);
        timeText.setText("Hareketsiz süre: "+Float.toString(0));
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sAccelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sLight = sensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorMgr.registerListener(this, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorMgr.registerListener(this, sensorMgr.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        noMovement=false;

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lux = event.values[0];
            luxText.setText(Float.toString(lux));
            if(lux>10){
                sensorView.setBackgroundColor(Color.WHITE);
                luxText.setTextColor(Color.BLACK);
                accText.setTextColor(Color.BLACK);
                timeText.setTextColor(Color.BLACK);
            }
            else{
                sensorView.setBackgroundColor(Color.BLACK);
                luxText.setTextColor(Color.WHITE);
                accText.setTextColor(Color.WHITE);
                timeText.setTextColor(Color.WHITE);
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x= event.values[0];
            float y= event.values[1];
            float z= event.values[2];
            String s=String.format("Eksenler: %.2f  %.2f  %.2f", x,y,z);
            accText.setText(s);
            long actualTime=System.currentTimeMillis();
            if(x>-1.0 && x<1.0 && y>-1.0 && y<1.0 && z>9.5 && z<10.5){
                if(noMovement==false){
                    lastUpdate=actualTime;
                }
                long difference=actualTime-lastUpdate;
                timeText.setText("Hareketsiz süre: "+Float.toString(difference/1000));
                noMovement=true;
                if(difference>5000){
                    finishAffinity();
                    System.exit(0);
                }
            }
            else{
                noMovement=false;
            }

        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorMgr.unregisterListener(this);
    }

}
