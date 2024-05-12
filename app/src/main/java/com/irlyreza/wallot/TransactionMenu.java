package com.irlyreza.wallot;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class TransactionMenu extends AppCompatActivity {
    Button datePickerTransaction, saveTransaction, incomeBtn, outcomeBtn;
    Spinner spinnerCategory;
    int years, months, days;
    int selectedMode = 1;
    // Income = 1 || Outcome = 2
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        datePickerTransaction = findViewById(R.id.datePickerTransaction);
        saveTransaction = findViewById(R.id.save_transaction);
        spinnerCategory = findViewById(R.id.spinner_category);
        incomeBtn = findViewById(R.id.income_btn);
        outcomeBtn = findViewById(R.id.outcome_btn);

        incomeBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_true_btn));
        outcomeBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_false_btn));
        outcomeBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

        incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_white));
                incomeBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_true_btn));
                outcomeBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_false_btn));
                outcomeBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));
            }
        });

        outcomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outcomeBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_true_btn));
                outcomeBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_white));
                incomeBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_false_btn));
                incomeBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = parent.getItemAtPosition(0).toString();
            }
        });

        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("Wallet1");
        categoryList.add("Wallet2");
        categoryList.add("Wallet3");
        categoryList.add("Wallet4");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerCategory.setAdapter(adapter);


        datePickerTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                years = calendar.get(Calendar.YEAR);
                months = calendar.get(Calendar.MONTH);
                days = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(TransactionMenu.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        years = year;
                        months = month;
                        days = dayOfMonth;

                        datePickerTransaction.setText(years + "-" + months + "-" + days);
                    }
                }, years, months, days);
                dialog.show();
            }
        });

        saveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}