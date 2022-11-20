package com.example.sensorapp;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SensorActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_SENSOR_TYPE = "KEY_EXTRA_SENSOR_TYPE";
    public static final String KEY_EXTRA_SENSOR_NAME = "KEY_EXTRA_SENSOR_NAME";
    private TextView sensorNameTextView;
    private ImageView sensorIconTextView;

    private RecyclerView recyclerView;

    //private Sensor sensor;
    private SensorAdapter adapter;
    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private boolean subtitleVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        recyclerView = findViewById(R.id.sensor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setHasOptionsMenu(true);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

//        sensorList

//        Log.d("TAG", "onCreate: " + sensorList);

        if (adapter == null){
            adapter = new SensorAdapter(sensorList);
            recyclerView.setAdapter(adapter);
        } else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sensor_menu, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        subtitleItem.setTitle(R.string.show_subtitle);

        return true;

//        if (subtitleVisible){
//            subtitleItem.setTitle(R.string.hide_subtitle);
//        } else{
//
//        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.show_subtitle:
                subtitleVisible = !subtitleVisible;

                int sensorCount = 0;
                for (Sensor sensor : sensorList){
                    sensorCount++;

                }
                String subtitle = getString(R.string.sensors_count, sensorCount);
                if (!subtitleVisible){
                    subtitle = null;
                }

                getSupportActionBar().setSubtitle(subtitle);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class SensorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Sensor sensor;

        public SensorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_sensor, parent, false));

            sensorNameTextView = itemView.findViewById(R.id.sensor_item_text);
            sensorIconTextView = itemView.findViewById(R.id.sensor_item_icon);

        }

        public void bind(Sensor sensor){

            this.sensor = sensor;
            Log.d("TAG", "SENSOR: " + sensor.getName() + " " + sensor.getType());
            if (Objects.equals(sensor.getName(), "Goldfish Light sensor") || Objects.equals(sensor.getName(), "Goldfish Ambient Temperature sensor") ){
                itemView.setOnClickListener(this);
                itemView.setBackgroundColor(Color.parseColor("yellow"));
            }
            if (Objects.equals(sensor.getName(), "GeoMag Rotation Vector Sensor")){
                itemView.setOnClickListener(this);
                itemView.setBackgroundColor(Color.parseColor("green"));
            }

            sensorNameTextView.setText(sensor.getName());
            sensorIconTextView.setImageResource(R.drawable.ic_sensor);
        }

        @Override
        public void onClick(View view) {
            Log.d("TAG", "CLICK " + sensor.getType());

            if (sensor.getType() == 20){
                Intent intent = new Intent(view.getContext(), LocationActivity.class);
//                intent.putExtra(KEY_EXTRA_SENSOR_TYPE, sensor.getType());
//                intent.putExtra(KEY_EXTRA_SENSOR_NAME, sensor.getName());
                startActivity(intent);
            } else {
                Intent intent = new Intent(view.getContext(), SensorDetailsActivity.class);
                intent.putExtra(KEY_EXTRA_SENSOR_TYPE, sensor.getType());
                intent.putExtra(KEY_EXTRA_SENSOR_NAME, sensor.getName());
                startActivity(intent);
            }


        }
    }

    private class SensorAdapter extends  RecyclerView.Adapter<SensorHolder> {

        private List<Sensor> sensorList;

        public SensorAdapter(List<Sensor> sensorList){
            this.sensorList = sensorList;
        }

//        public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////            View view = mInflater.inflate(R.layout.list_item_sensor, parent, false);
////            return new SensorHolder(view);
////            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
////            return new SensorHolder(layoutInflater, parent);
//
////            val view = LayoutInflater.from(viewGroup.context)
////                    .inflate(R.layout.text_row_item, viewGroup, false)
////
////            return ViewHolder(view)
//
//        }


        @Override
        public SensorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//            View listItem = layoutInflater.inflate(R.layout.list_item_sensor, parent, false);
//            SensorHolder viewHolder = new SensorHolder(listItem);
//            return viewHolder;
            return new SensorHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull SensorHolder holder, int position) {
            final Sensor sensor = sensorList.get(position);

//            Log.d("TAG", "Sensor name: " + sensor.getVendor());
//            Log.d("TAG", "Max range: " + sensor.getMaximumRange());

            sensorNameTextView.setText(sensor.getName());
//            holder.textView.setText(listdata[position].getDescription());
//            TextView nameTask = holder.getName();


            holder.bind(sensor);
//
//            TextView nameSensor = holder.getNameTask();
        }

        @Override
        public int getItemCount() {
            return sensorList.size();
        }
    }

//    public void updateSubtitle(){
//
//
//        String subtitle = getString(R.string.sensors_count, sensorCount);
//        if (!subtitleVisible){
//            subtitle = null;
//        }
//        AppCompatActivity appCompatActivity = (AppCompatActivity) getAc;
//        appCompatActivity.getSupportActionBar().setSubtitle(subtitle);
//    }
}