package com.example.serviceexpress;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mAuth = FirebaseAuth.getInstance();

        TextView buttonLogin = (TextView) findViewById(R.id.textView5);
        final EditText emailET = (EditText) findViewById(R.id.editText7);
        final EditText passET = (EditText) findViewById(R.id.editText4);
        final EditText confirmPassET = (EditText) findViewById(R.id.editText6);
        final Button registerBtn = (Button) findViewById(R.id.button4);

        //Register

        //Validate Email and Password
        String email = emailET.getText().toString();
        String pass = passET.getText().toString();
        String confirmpass = confirmPassET.getText().toString();


        registerBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String em = emailET.getText().toString();
                String pass = passET.getText().toString();
                String confirm_pass = confirmPassET.getText().toString();

                if (isEmailValid(em)){
                    if(!pass.equals(confirm_pass)){
                        sendToastMessage("Las contraseñas no coinciden");
                    }else{
                        registerFunction(em,pass);
                    }
                }else{
                    sendToastMessage("No es un email valido");
                }

            }
        });


        //Change to Login Activity
        buttonLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                initLogin();
            }
        });
    }

    public void sendToastMessage(CharSequence text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    public void initLogin(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }

    public void initProfile(){
        Intent intent = new Intent(this, user_profile.class);
        startActivity(intent);
        finish();
    }

    public void registerFunction(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("FireBaseMSG", "createUserWithEmail:success");
                        initProfile();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("FireBaseMSG", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(register.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return true;
        }else{
            Toast.makeText(register.this, "No es un email valido", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}
