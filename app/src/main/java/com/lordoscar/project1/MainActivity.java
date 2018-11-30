package com.lordoscar.project1;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        SensorAdapter adapter = new SensorAdapter(this, R.layout.sensor_list_item, sensors);

        ListView listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);
    }

    public void showDetail(Sensor sensor){
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setSensor(sensor);
        detailFragment.show(getSupportFragmentManager(), "detail");
    }
}
