package com.irlyreza.wallot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    EditText email,username,password,phonenumb;
    Button signup,back;

    Data data;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        phonenumb = findViewById(R.id.phonenumb);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.btn_create);
        back = findViewById(R.id.btn_back);
        data = new Data(getApplicationContext());

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (password.getText().toString().length() < 8 || password.getText().toString().length() > 12 ){
                    Toast.makeText(getApplicationContext(), "Password must be 8 to 12 characters long", Toast.LENGTH_SHORT).show();
                }

                else {
                    data.insert_data(username.getText().toString(), password.getText().toString(), email.getText().toString(), phonenumb.getText().toString());
                    Toast.makeText(getApplicationContext(), "Account has been created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}
