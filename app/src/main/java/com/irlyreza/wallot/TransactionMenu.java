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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TransactionMenu extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference transactionReference = database.getReference("transactions");
    DatabaseReference walletReference = database.getReference("wallets");


    Button datePickerTransaction, saveTransaction, incomeBtn, outcomeBtn;
    EditText transactionNominal, transactionDescription;
    TextView transactionDescriptionLength;
    Spinner spinnerCategory;
    ArrayList<DataWalletModel> categoryList;
    WalletSpinnerAdapter walletSpinnerAdapter;

    int years, months, days;
    int selectedMode = 1;
    // Income = 1 || Outcome = 2
    String category, idWallet;

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
        transactionNominal = findViewById(R.id.transaction_nominal);
        transactionDescription = findViewById(R.id.transaction_description);
        transactionDescriptionLength = findViewById(R.id.transaction_description_length);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());
        datePickerTransaction.setText(currentDateandTime);

        incomeBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_true_btn));
        outcomeBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.transaction_selected_false_btn));
        outcomeBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

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
                idWallet = dataWalletModel.getId_wallet();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                DataWalletModel dataWalletModel = (DataWalletModel) adapterView.getItemAtPosition(0);
                idWallet = dataWalletModel.getId_wallet();
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
                saveData(transactionNominal.getText().toString(), transactionDescription.getText().toString(), datePickerTransaction.getText().toString());
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

    private void saveData(String nominal, String description, String date) {
        DataTransactionModel dataTransactionModel = new DataTransactionModel(nominal, description, date, idWallet);
        String id_transaction = transactionReference.push().getKey();
        transactionReference.child(id_transaction).setValue(dataTransactionModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}