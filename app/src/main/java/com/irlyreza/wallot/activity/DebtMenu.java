package com.irlyreza.wallot.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irlyreza.wallot.R;
import com.irlyreza.wallot.adapter.WalletSpinnerAdapter;
import com.irlyreza.wallot.data.DataDebtModel;
import com.irlyreza.wallot.data.DataWalletModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DebtMenu extends AppCompatActivity {
    Button datePickerTransaction, saveTransaction, giverBtn, recieverBtn;
    EditText transactionNominal, transactionDescription, debtUsername, phoneNumber;
    TextView transactionDescriptionLength, debtUsernameLength;
    Spinner spinnerCategory;
    ArrayList<DataWalletModel> categoryList;
    ArrayList<DataDebtModel> debtArray;
    DataDebtModel dataDebtModel;
    WalletSpinnerAdapter walletSpinnerAdapter;
    int years, months, days;
    int selectedMode = 1;
    // Income = 1 || Outcome = 2
    String category, type = "giver", idUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_debt_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences preferences = getSharedPreferences("LOGINAPP", Context.MODE_PRIVATE);
        idUser = preferences.getString("idUser", null);

        datePickerTransaction = findViewById(R.id.datePickerTransaction);
        saveTransaction = findViewById(R.id.save_transaction);
        spinnerCategory = findViewById(R.id.spinner_category);
        giverBtn = findViewById(R.id.giver_btn);
        recieverBtn = findViewById(R.id.reciever_btn);
        transactionNominal = findViewById(R.id.transaction_nominal);
        transactionDescription = findViewById(R.id.transaction_description);
        transactionDescriptionLength = findViewById(R.id.transaction_description_length);
        debtUsername = findViewById(R.id.debt_username);
        debtUsernameLength = findViewById(R.id.debt_username_length);
        phoneNumber = findViewById(R.id.debt_number_phone);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());
        datePickerTransaction.setText(currentDateandTime);

        giverBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_true_btn));
        recieverBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_false_btn));
        recieverBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

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

        debtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                debtUsernameLength.setText(debtUsername.length() + "/10");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                debtUsernameLength.setText(debtUsername.length() + "/10");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        transactionDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                transactionDescriptionLength.setText(transactionDescription.length() + "/16");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                transactionDescriptionLength.setText(transactionDescription.length() + "/16");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        giverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giverBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_white));
                giverBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_true_btn));
                recieverBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_false_btn));
                recieverBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));
                type = "giver";
            }
        });

        recieverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recieverBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_true_btn));
                recieverBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_white));
                giverBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_false_btn));
                giverBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));
                type = "receiver";
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference walletReference = database.getReference("wallets");

        walletReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList = new ArrayList<>();
                for (DataSnapshot walletItem : snapshot.getChildren()) {
                    DataWalletModel dataWalletModel = walletItem.getValue(DataWalletModel.class);
                    dataWalletModel.setId_wallet(walletItem.getKey());
                    categoryList.add(dataWalletModel);
                }
                if(getApplicationContext() != null) {
                    walletSpinnerAdapter = new WalletSpinnerAdapter(getApplicationContext(), categoryList);
                }
                spinnerCategory.setAdapter(walletSpinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DataWalletModel dataWalletModel = (DataWalletModel) adapterView.getItemAtPosition(i);
                category = dataWalletModel.getId_wallet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                DataWalletModel dataWalletModel = (DataWalletModel) adapterView.getItemAtPosition(0);
                category = dataWalletModel.getId_wallet();
            }
        });




        datePickerTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                years = calendar.get(Calendar.YEAR);
                months = calendar.get(Calendar.MONTH);
                days = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(DebtMenu.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        years = year;
                        months = month;
                        days = dayOfMonth;

                        String formattedDate = String.format(Locale.getDefault(), "%02d-%02d-%04d", days, months + 1, years);
                        datePickerTransaction.setText(formattedDate);
                    }
                }, years, months, days);
                dialog.show();
            }
        });

        saveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference debtReference = database.getReference("debts");

                dataDebtModel = new DataDebtModel(debtUsername.getText().toString(), transactionNominal.getText().toString(), transactionDescription.getText().toString(), phoneNumber.getText().toString(), datePickerTransaction.getText().toString(), category, idUser, type);
                String idDebt = debtReference.push().getKey();

                debtReference.child(idDebt).setValue(dataDebtModel);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
