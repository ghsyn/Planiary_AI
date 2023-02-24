package com.example.toyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class PlantWaterCountActivity extends AppCompatActivity {
    ListView lv_plantWaterCount;
    WaterCountAdapter adapter = new WaterCountAdapter();
    Button btn_waterCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homewater);
        lv_plantWaterCount = (ListView) findViewById(R.id.waterplantlist);
        btn_waterCheck = (Button)findViewById(R.id.btn_waterCheck);
        adapter.addItemToList();

        lv_plantWaterCount.setAdapter(adapter);

        btn_waterCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getWater();

            }
        });

    }

}