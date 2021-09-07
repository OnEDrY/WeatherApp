package com.example.authtast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText authEmail, authPassword;
    public Button authAuth, authReg;

    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        authEmail = findViewById(R.id.AuthEmail);
        authPassword = findViewById(R.id.AuthPassword);

        authAuth = findViewById(R.id.AuthAuth);
        authAuth.setOnClickListener(this);
        authReg = findViewById(R.id.AuthReg);
        authReg.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.AuthReg:
                startActivity(new Intent(this, RegUser.class));
                break;
            case R.id.AuthAuth:
                singIn();
        }
    }

    public void singIn(){
        String email = authEmail.getText().toString().trim();
        String password = authPassword.getText().toString().trim();

        if(email.isEmpty()){
            authEmail.setError("Поле Email пустое");
            authEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            authEmail.setError("Укажите корректный Email адресс!");
            authEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            authPassword.setError("Поле Пароль пустое!");
            authPassword.requestFocus();
            return;
        }
        if(password.length() < 6) {
            authPassword.setError("Пароль должен состоять минимум из 6 символов");
            authPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, Weather.class));
                        }else {
                            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}