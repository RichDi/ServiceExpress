package com.example.serviceexpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class landing extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){
            profileActivity();
        }

    }

    private void profileActivity(){
        Intent intent = new Intent(this, user_profile.class);
        startActivity(intent);
        finish();
    }

    private void updateUI() {
        Intent intent = new Intent(this, landing.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        //Button functions

        Button buttonLogin = (Button) findViewById(R.id.button2);
        buttonLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                initLogin();
            }
        });

        Button buttonRegister = (Button) findViewById(R.id.button3);
        buttonRegister .setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                initRegister();
            }
        });


    }




    public void initLogin(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }

    public void initRegister(){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
        finish();
    }

}
