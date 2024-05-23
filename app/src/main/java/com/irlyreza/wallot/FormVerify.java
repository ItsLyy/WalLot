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

public class FormVerify extends AppCompatActivity {

    EditText verify;
    Button next,back;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_verify);

        verify = findViewById(R.id.verify);
        next = findViewById(R.id.btn_next);
        back = findViewById(R.id.btn_back);

        String txVerify = verify.getText().toString();
        String txPassword = "12345678"; //TODO (get the password of this account and drop to this variable)

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txPassword != null){
                    if (txVerify == txPassword){
                        startActivity(new Intent(getApplicationContext(), FormEdit.class));
                    }
                    else {
                        Toast.makeText(FormVerify.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                        verify.setText("");
                    }
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileFragment.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ProfileFragment.class));
    }
}
