package com.irlyreza.wallot.activity;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
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

public class EditDebtMenu extends AppCompatActivity {
    Button datePickerTransaction, updateBtn;
    EditText transactionNominal, transactionDescription, debtUsername, phoneNumber;
    TextView transactionDescriptionLength, debtUsernameLength;
    ImageView doneBtn;
    DataDebtModel dataDebtModel;
    int years, months, days;
    int selectedMode = 1;
    // Income = 1 || Outcome = 2
    String category, type = "giver", idUser;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_debt_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences preferences = getSharedPreferences("LOGINAPP", Context.MODE_PRIVATE);
        idUser = preferences.getString("idUser", null);

        datePickerTransaction = findViewById(R.id.datePickerTransaction);
        transactionNominal = findViewById(R.id.transaction_nominal);
        transactionDescription = findViewById(R.id.transaction_description);
        transactionDescriptionLength = findViewById(R.id.transaction_description_length);
        debtUsername = findViewById(R.id.debt_username);
        debtUsernameLength = findViewById(R.id.debt_username_length);
        phoneNumber = findViewById(R.id.debt_number_phone);
        updateBtn = findViewById(R.id.update_btn);
        doneBtn = findViewById(R.id.done_btn);

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

        datePickerTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                years = calendar.get(Calendar.YEAR);
                months = calendar.get(Calendar.MONTH);
                days = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(EditDebtMenu.this, new DatePickerDialog.OnDateSetListener() {
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



        Bundle bundle = getIntent().getExtras();
        transactionNominal.setText(bundle.getString("nominal"));
        debtUsername.setText(bundle.getString("name"));
        phoneNumber.setText(bundle.getString("phoneNumber"));
        transactionDescription.setText(bundle.getString("description"));
        datePickerTransaction.setText(bundle.getString("date"));

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference debtReference = database.getReference("debts");

                dataDebtModel = new DataDebtModel(debtUsername.getText().toString(), transactionNominal.getText().toString(), transactionDescription.getText().toString(), phoneNumber.getText().toString(), datePickerTransaction.getText().toString(), bundle.getString("idWallet"), idUser, bundle.getString("type"));

                debtReference.child(bundle.getString("idDebt")).setValue(dataDebtModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference debtReference = database.getReference("debts");

                debtReference.child(bundle.getString("idDebt")).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
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