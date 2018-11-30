package com.lordoscar.project1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class SensorListener implements SensorEventListener {

    DetailFragment  detailFragment;

    public SensorListener(DetailFragment detailFragment){
        this.detailFragment = detailFragment;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        detailFragment.updateValues(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("Accuracy changed", accuracy + "");
    }
}
