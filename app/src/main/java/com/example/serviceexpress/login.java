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

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        initLoginValidator();

        //Button Register function

        TextView buttonRegister = (TextView) findViewById(R.id.textView3);
        buttonRegister.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                initRegister();
            }
        });
    }

    private void initLoginValidator() {
        //Check if email is empty
        final EditText emailET = (EditText) findViewById(R.id.editText);
        final EditText passwordET = (EditText) findViewById(R.id.editText2);

        Button loginBtn = (Button) findViewById(R.id.button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String pass =  passwordET.getText().toString();
                boolean emailEmpty = isEmailEmpty(email);
                boolean validEmail = isEmailValid(email);
                boolean passwordEmpty = isPasswordEmpty(pass);

                if(!emailEmpty && !passwordEmpty && validEmail){
                    loginUser(email,pass);
                }

            }
        });
    }

    public void initRegister(){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
        finish();
    }

    public void loginUser(String email, String password){
        //Log User in Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FB-MSG", "signInWithEmail:success");
                            Toast.makeText(login.this, "Usuario Loggeado", Toast.LENGTH_SHORT).show();
                            initProfile();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FB-MSG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "La cuenta no esta registrada.",Toast.LENGTH_SHORT).show();
                        }
                        Log.d("TASK",task.toString());
                    }
                });
    }

    public boolean isEmailEmpty(String email){
        if (email.matches("")){
            Toast.makeText(login.this, "Introduzca su correo", Toast.LENGTH_SHORT).show();
            return true;
        }else return false;
    }

    public boolean isPasswordEmpty(String password){
        if (password.matches("")){
            Toast.makeText(login.this, "Introduzca su contraseña", Toast.LENGTH_SHORT).show();
            return true;
        }else return false;
    }


    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return true;
        }else{
            Toast.makeText(login.this, "No es un email valido", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void initProfile(){
        Intent intent = new Intent(this, user_profile.class);
        startActivity(intent);
        finish();
    }
}
