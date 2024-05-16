package com.irlyreza.wallot;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditTransactionMenu extends AppCompatActivity {

    Button datePickerTransaction, updateBtn;
    ImageView deleteBtn;
    EditText transactionNominal, transactionDescription;
    TextView transactionDescriptionLength;
    Spinner spinnerCategory;
    int years, months, days;
    int selectedMode = 1;
    // Income = 1 || Outcome = 2
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_transaction_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        datePickerTransaction = findViewById(R.id.datePickerTransaction);
        spinnerCategory = findViewById(R.id.spinner_category);
        updateBtn = findViewById(R.id.update_btn);
        deleteBtn = findViewById(R.id.delete_btn);
        transactionNominal = findViewById(R.id.transaction_nominal);
        transactionDescription = findViewById(R.id.transaction_description);
        transactionDescriptionLength = findViewById(R.id.transaction_description_length);

        transactionNominal.setText(bundle.getString("nominal"));
        transactionDescription.setText(bundle.getString("description"));
        datePickerTransaction.setText(bundle.getString("date"));


        updateBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_true_btn));
        updateBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_white));
        deleteBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_false_btn));
        deleteBtn.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

        transactionNominal.addTextChangedListener(new TextWatcher() {
            private  String setEditText = transactionNominal.getText().toString().trim();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals(setEditText)) {
                    transactionNominal.removeTextChangedListener(this);
                    String replace = charSequence.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()) {
                        setEditText = formatRupiah(Double.parseDouble(replace));
                    } else {
                        setEditText = "";
                    }
                    transactionNominal.setText(setEditText);
                    transactionNominal.setSelection(setEditText.length());
                    transactionNominal.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        transactionDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                transactionDescriptionLength.setText(transactionDescription.length() + "/50");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        ArrayList<WalLot_Data.Wallet_Data> categoryList = new ArrayList<>();
        categoryList.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        categoryList.add(new WalLot_Data.Wallet_Data("DKA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        categoryList.add(new WalLot_Data.Wallet_Data("CBA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        WalletSpinnerAdapter walletSpinnerAdapter = new WalletSpinnerAdapter(getApplicationContext(), categoryList);
        spinnerCategory.setAdapter(walletSpinnerAdapter);


        datePickerTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                years = calendar.get(Calendar.YEAR);
                months = calendar.get(Calendar.MONTH);
                days = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(EditTransactionMenu.this, new DatePickerDialog.OnDateSetListener() {
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

    }

    private String formatRupiah(Double number) {
        Locale localeId = new Locale("IND", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeId);
        String formatrupiah = numberFormat.format(number);
        String[] split = formatrupiah.split(",");
        int length = split[0].length();
        return  split[0].substring(2,length);

    }
}