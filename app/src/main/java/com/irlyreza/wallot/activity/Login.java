package com.irlyreza.wallot.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.CoreComponentFactory;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irlyreza.wallot.Data;
import com.irlyreza.wallot.R;
import com.irlyreza.wallot.data.DataUserModel;

import java.util.ArrayList;
import java.util.Objects;


public class Login extends AppCompatActivity {

    EditText username, password;
    Button login,signup;
    CheckBox rememberMe;
    ArrayList<DataUserModel> userArray;
    DataUserModel dataUserModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Intent mainPage = new Intent(getApplicationContext(), MainActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences("LOGINAPP", MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();

        if (!Objects.equals(sharedPreferences.getString("idUser", null), null) || Objects.equals(sharedPreferences.getString("isRemember", null), true)) {
            startActivity(mainPage);
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.btn_register);
        rememberMe = findViewById(R.id.checkBox);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference("users");

        userArray = new ArrayList<>();
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userItem : snapshot.getChildren()) {
                    dataUserModel = new DataUserModel();
                    dataUserModel.setIdUser(userItem.getKey());
                    dataUserModel.setNameUser(userItem.child("nameUser").getValue(String.class));
                    dataUserModel.setPasswordUser(userItem.child("passwordUser").getValue(String.class));
                    dataUserModel.setGmailUser(userItem.child("gmailUser").getValue(String.class));
                    dataUserModel.setPhoneNumberUser(userItem.child("phoneNumberUser").getValue(String.class));
                    dataUserModel.setProfileImage(userItem.child("profileImage").getValue(Integer.class));
                    userArray.add(dataUserModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < userArray.size(); i++) {
                    if (userArray.get(i).getNameUser().equals(username.getText().toString()) && userArray.get(i).getPasswordUser().equals(password.getText().toString())) {
                        if (rememberMe.isChecked()) {
                            sharedPreferenceEditor.putString("idUser", userArray.get(i).getIdUser());
                            sharedPreferenceEditor.putBoolean("isRemember", true);
                            sharedPreferenceEditor.apply();
                            startActivity(mainPage);
                        } else {
                            sharedPreferenceEditor.putString("idUser", userArray.get(i).getIdUser());
                            sharedPreferenceEditor.apply();
                            startActivity(mainPage);
                        }
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });

    }
}
