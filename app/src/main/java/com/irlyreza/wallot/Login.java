package com.irlyreza.wallot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;





public class Login extends AppCompatActivity {

    EditText username, password;
    Button login,signin;
    Data data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        signin = findViewById(R.id.btn_register);
        data = new Data(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data.check(username.getText().toString(),password.getText().toString());
                Intent intent = getIntent();
                if (intent != null && intent.hasExtra("condition")) {
                    String dt = intent.getStringExtra("condition");
                    if ("True".equals(dt)){
                        Toast.makeText(Login.this, "Password is correct :  " + dt, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                }
                //startActivity(new Intent(getApplicationContext(), update_data.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
