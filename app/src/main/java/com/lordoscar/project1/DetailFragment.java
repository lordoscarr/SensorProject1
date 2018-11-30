package com.lordoscar.project1;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailFragment extends DialogFragment implements SensorEventListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initializeComponents(view);
        return view;
    }

    Sensor sensor;
    SensorManager sensorManager;
    TextView valuesText;
    Button registerButton;

    public void setSensor(Sensor sensor){
        this.sensor = sensor;
    }

    private void initializeComponents(View view){

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        TextView nameText = view.findViewById(R.id.nameText);
        TextView maxRangeText = view.findViewById(R.id.maxRangeText);
        TextView minDelayText = view.findViewById(R.id.minDelayText);
        TextView powerText = view.findViewById(R.id.powerText);
        TextView resolutionText = view.findViewById(R.id.resolutionText);
        TextView vendorText = view.findViewById(R.id.vendorText);
        TextView versionText = view.findViewById(R.id.versionText);
        valuesText = view.findViewById(R.id.valuesText);
        registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new ButtonListener());

        nameText.setText(sensor.getName());
        maxRangeText.setText(sensor.getMaximumRange() + "");
        minDelayText.setText(sensor.getMinDelay() + "");
        powerText.setText(sensor.getPower() + "");
        resolutionText.setText(sensor.getResolution() + "");
        vendorText.setText(sensor.getVendor());
        versionText.setText(sensor.getVersion()+ "");
    }

    private String fromTimestamp(long timestamp){
        long timeInMillis = (new Date()).getTime()
                + (timestamp - System.nanoTime()) / 1000000L;

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SS");
        return dateFormat.format(new Date(timeInMillis));
    }

    public void updateValues(SensorEvent sensorEvent){
        StringBuilder sb = new StringBuilder();

        sb.append("Accuracy: " + sensorEvent.accuracy);
        sb.append("\nTimestamp: " + fromTimestamp(sensorEvent.timestamp));
        int count = 0;
        for(float value : sensorEvent.values){
            sb.append("\nValue " + count++ + ": " + value);
        }
        if(sb.toString().length() == 0){
            valuesText.setText("No data from sensor.");
        }else {
            valuesText.setText(sb.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!register)
            sensorManager.registerListener(this, sensor ,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    } @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager = null;
        sensor = null;
    }

    boolean register = true;

    @Override
    public void onSensorChanged(SensorEvent event) {
        updateValues(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("Accuracy changed", accuracy + "");
    }

    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(register){
                sensorManager.registerListener(DetailFragment.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                register = false;
                registerButton.setText("UNREGISTER");
                Toast.makeText(getActivity(), "Registered listener", Toast.LENGTH_SHORT).show();
            }else{
                sensorManager.unregisterListener(DetailFragment.this);
                register = true;
                registerButton.setText("REGISTER");
                Toast.makeText(getActivity(), "Unregistered listener", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
