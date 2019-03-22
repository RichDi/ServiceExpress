package com.example.serviceexpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Objects;

public class service_detail extends AppCompatActivity {

    Servicios service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        service = (Servicios) getIntent().getSerializableExtra("Service");

    }
}
