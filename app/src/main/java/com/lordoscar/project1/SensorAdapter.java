package com.lordoscar.project1;

import android.content.Context;
import android.hardware.Sensor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SensorAdapter extends ArrayAdapter<Sensor> implements AdapterView.OnItemClickListener {

    public SensorAdapter(@NonNull Context context, int resource, @NonNull List<Sensor> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sensor sensor = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sensor_list_item, parent, false);
        }

        TextView sensorName = convertView.findViewById(R.id.sensorName);
        sensorName.setText(sensor.getName());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((MainActivity) getContext()).showDetail(getItem(position));
    }
}
