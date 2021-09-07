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

public class RegUser extends AppCompatActivity implements View.OnClickListener {


    public EditText regName, regEmail, regPassword;
    public Button regReg, regBack;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_user);

        mAuth = FirebaseAuth.getInstance();

        regName = findViewById(R.id.RegName);
        regEmail = findViewById(R.id.RegEmail);
        regPassword = findViewById(R.id.RegPassword);

        regReg = findViewById(R.id.RegReg);
        regReg.setOnClickListener(this);
        regBack = findViewById(R.id.RegBack);
        regBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.RegBack:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.RegReg:
                regUser();
                break;
        }
    }

    private void regUser(){
        String name = regName.getText().toString().trim();
        String email = regEmail.getText().toString().trim();
        String password = regPassword.getText().toString().trim();

        if(name.isEmpty()){
            regName.setError("Поле Имя пустое!");
            regName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            regEmail.setError("Поле Email пустое");
            regEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regEmail.setError("Укажите корректный Email адресс!");
            regEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            regPassword.setError("Поле Пароль пустое!");
            regPassword.requestFocus();
            return;
        }
        if(password.length() < 6) {
            regPassword.setError("Пароль должен состоять минимум из 6 символов");
            regPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegUser.this, "Complete", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegUser.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}