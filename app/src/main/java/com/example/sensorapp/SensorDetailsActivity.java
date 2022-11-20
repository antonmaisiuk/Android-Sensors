package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SensorDetailsActivity extends AppCompatActivity implements SensorEventListener {

    private static final String KEY_EXTRA_SENSOR_TYPE = "KEY_EXTRA_SENSOR_TYPE";
    private static final String KEY_EXTRA_SENSOR_NAME = "KEY_EXTRA_SENSOR_NAME";
    private String sensorName;
    private SensorManager sensorManager;
    private Sensor selectedSensor;
    private TextView sensorLightTextView;
    private TextView sensorNameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_details);

        sensorLightTextView = findViewById(R.id.sensor_Light_label);
        sensorNameTextView = findViewById(R.id.sensor_name);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Bundle bundle = getIntent().getExtras();
        int sensorType = bundle.getInt(KEY_EXTRA_SENSOR_TYPE);
        sensorName = bundle.getString(KEY_EXTRA_SENSOR_NAME);

//        int sensorType = (int)intent.getSerializableExtra(KEY_EXTRA_SENSOR_TYPE);
//        Log.d("TAG", "onCreate: " + sensorType);

        sensorNameTextView.setText(getResources().getString(R.string.sensor_name, sensorName));
        selectedSensor = sensorManager.getDefaultSensor(sensorType);

        if (selectedSensor == null){
            sensorLightTextView.setText(R.string.missing_sensor);
            sensorNameTextView.setText(R.string.missing_sensor);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (selectedSensor != null){
            sensorManager.registerListener(this,selectedSensor, sensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];


        switch (sensorType){
            case Sensor.TYPE_LIGHT:
//                Log.d("TAG", "onSensorChanged: LIGHT");
                sensorLightTextView.setText(getResources().getString(R.string.Light_sensor_label, currentValue));

                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
//                Log.d("TAG", "onSensorChanged: TEMP");
                sensorLightTextView.setText(getResources().getString(R.string.Light_sensor_label, currentValue));
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("TAG", "onAccuracyChanged");
    }
}