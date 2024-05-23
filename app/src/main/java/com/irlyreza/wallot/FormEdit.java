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

public class FormEdit extends AppCompatActivity {

    EditText txUsername,txPassword,userEmail,userPhonenumb;
    Button commit,cancel;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_edit);

        txUsername = findViewById(R.id.txUsername);
        txPassword = findViewById(R.id.txPassword);
        userEmail = findViewById(R.id.userEmail);
        userPhonenumb = findViewById(R.id.userPhonenumb);
        commit = findViewById(R.id.btn_update);
        cancel = findViewById(R.id.btn_cancel);

        String newUsername = txUsername.getText().toString();
        String newPassword = txPassword.getText().toString();
        String newEmail = userEmail.getText().toString();
        String newPhonenumb = userPhonenumb.getText().toString();

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newUsername != null && newPassword != null && newEmail != null && newPhonenumb != null){
                    //TODO(UPDATE the values of the record by this variable : username = newUsername, password = newPassword, etc.)
                    startActivity(new Intent(getApplicationContext(), ProfileFragment.class));
                }
                else {
                    Toast.makeText(FormEdit.this, "Please fill out all of the field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
