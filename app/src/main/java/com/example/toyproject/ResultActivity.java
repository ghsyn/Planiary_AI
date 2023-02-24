package com.example.toyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    ImageView imageview;
    TextView name,data,info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ai_camera);
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        imageview = findViewById(R.id.plantai_image);
        name = findViewById(R.id.plantai_name);
        data = findViewById(R.id.plantai_data);
        info = findViewById(R.id.plantai_info);
        name.setText(result);
    }
}