package com.irlyreza.wallot.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.irlyreza.wallot.data.DataUserWalletModel;
import com.irlyreza.wallot.data.DataWalletModel;

import java.util.ArrayList;

public class EditWalletMenu extends AppCompatActivity {
    EditText nameWallet;
    TextView nameWalletLength;
    RelativeLayout background1, background2, background3, background4, backgroundDisplay1, backgroundDisplay2, backgroundDisplay3, backgroundDisplay4;
    ImageView icon1, icon2, icon3, icon4, deleteBtn;
    Button updateWalletBtn;
    int iconWallet, backgroundIconWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_wallet_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("LOGINAPP", MODE_PRIVATE);

        nameWallet = findViewById(R.id.name_wallet);
        nameWalletLength = findViewById(R.id.name_wallet_length);

        nameWallet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameWalletLength.setText(i2 + "/10");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        background1 = findViewById(R.id.background_icon_1);
        background2 = findViewById(R.id.background_icon_2);
        background3 = findViewById(R.id.background_icon_3);
        background4 = findViewById(R.id.background_icon_4);

        backgroundDisplay1 = findViewById(R.id.background_icon_display_1);
        backgroundDisplay2 = findViewById(R.id.background_icon_display_2);
        backgroundDisplay3 = findViewById(R.id.background_icon_display_3);
        backgroundDisplay4 = findViewById(R.id.background_icon_display_4);

        icon1 = findViewById(R.id.icon_1);
        icon2 = findViewById(R.id.icon_2);
        icon3 = findViewById(R.id.icon_3);
        icon4 = findViewById(R.id.icon_4);


        background1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
        background2.setBackground(null);
        background3.setBackground(null);
        background4.setBackground(null);

        backgroundDisplay1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
        backgroundDisplay2.setBackground(null);
        backgroundDisplay3.setBackground(null);
        backgroundDisplay4.setBackground(null);

        backgroundIconWallet = R.drawable.bg_corner_gradient_blue;
        iconWallet = R.drawable.card_icon;
        updateWalletBtn = findViewById(R.id.update_wallet_btn);
        deleteBtn = findViewById(R.id.delete_wallet_btn);

        Bundle bundle = getIntent().getExtras();
        String idWallets = bundle.getString("idWallet");
        String nameWallets = bundle.getString("name");
        String nominalWallets = bundle.getString("nominal");
        int iconWallets = bundle.getInt("icon");
        int bgIconWallets = bundle.getInt("bgIcon");

        nameWallet.setText(nameWallets);

        if (bgIconWallets == R.drawable.bg_corner_gradient_blue) {
            background1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
            background2.setBackground(null);
            background3.setBackground(null);
            background4.setBackground(null);

            icon1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_blue));
            icon2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_blue));
            icon3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_blue));
            icon4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_blue));

            backgroundIconWallet = R.drawable.bg_corner_gradient_blue;
        } else if (bgIconWallets == R.drawable.bg_corner_gradient_green) {
            background1.setBackground(null);
            background2.setBackground(null);
            background3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
            background4.setBackground(null);

            icon1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_green));
            icon2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_green));
            icon3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_green));
            icon4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_green));

            backgroundIconWallet = R.drawable.bg_corner_gradient_green;
        } else if (bgIconWallets == R.drawable.bg_corner_gradient_red) {
            background1.setBackground(null);
            background2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
            background3.setBackground(null);
            background4.setBackground(null);

            icon1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_red));
            icon2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_red));
            icon3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_red));
            icon4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_red));

            backgroundIconWallet = R.drawable.bg_corner_gradient_red;
        } else if (bgIconWallets == R.drawable.bg_corner_gradient_purple) {
            background1.setBackground(null);
            background2.setBackground(null);
            background3.setBackground(null);
            background4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));

            icon1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_purple));
            icon2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_purple));
            icon3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_purple));
            icon4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_purple));

            backgroundIconWallet = R.drawable.bg_corner_gradient_purple;
        }

        if (iconWallets == R.drawable.card_icon) {
            backgroundDisplay1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
            backgroundDisplay2.setBackground(null);
            backgroundDisplay3.setBackground(null);
            backgroundDisplay4.setBackground(null);

            iconWallet = R.drawable.card_icon;
        } else if (iconWallets == R.drawable.coin_icon) {
            backgroundDisplay1.setBackground(null);
            backgroundDisplay2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
            backgroundDisplay3.setBackground(null);
            backgroundDisplay4.setBackground(null);

            iconWallet = R.drawable.card_icon;
        } else if (iconWallets == R.drawable.stars_icon) {
            backgroundDisplay1.setBackground(null);
            backgroundDisplay2.setBackground(null);
            backgroundDisplay3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
            backgroundDisplay4.setBackground(null);

            iconWallet = R.drawable.card_icon;
        } else if (iconWallets == R.drawable.bn_wallet_icon) {
            backgroundDisplay1.setBackground(null);
            backgroundDisplay2.setBackground(null);
            backgroundDisplay3.setBackground(null);
            backgroundDisplay4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));

            iconWallet = R.drawable.card_icon;
        }

        background1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
                background2.setBackground(null);
                background3.setBackground(null);
                background4.setBackground(null);

                icon1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_blue));
                icon2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_blue));
                icon3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_blue));
                icon4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_blue));

                backgroundIconWallet = R.drawable.bg_corner_gradient_blue;
            }
        });
        background2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background1.setBackground(null);
                background2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
                background3.setBackground(null);
                background4.setBackground(null);

                icon1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_red));
                icon2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_red));
                icon3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_red));
                icon4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_red));

                backgroundIconWallet = R.drawable.bg_corner_gradient_red;
            }
        });
        background3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background1.setBackground(null);
                background2.setBackground(null);
                background3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
                background4.setBackground(null);

                icon1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_green));
                icon2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_green));
                icon3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_green));
                icon4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_green));

                backgroundIconWallet = R.drawable.bg_corner_gradient_green;
            }
        });
        background4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                background1.setBackground(null);
                background2.setBackground(null);
                background3.setBackground(null);
                background4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));

                icon1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_purple));
                icon2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_purple));
                icon3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_purple));
                icon4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_purple));

                backgroundIconWallet = R.drawable.bg_corner_gradient_purple;
            }
        });

        backgroundDisplay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundDisplay1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
                backgroundDisplay2.setBackground(null);
                backgroundDisplay3.setBackground(null);
                backgroundDisplay4.setBackground(null);

                iconWallet = R.drawable.card_icon;
            }
        });
        backgroundDisplay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundDisplay1.setBackground(null);
                backgroundDisplay2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
                backgroundDisplay3.setBackground(null);
                backgroundDisplay4.setBackground(null);

                iconWallet = R.drawable.coin_icon;
            }
        });
        backgroundDisplay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundDisplay1.setBackground(null);
                backgroundDisplay2.setBackground(null);
                backgroundDisplay3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));
                backgroundDisplay4.setBackground(null);

                iconWallet = R.drawable.stars_icon;
            }
        });
        backgroundDisplay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundDisplay1.setBackground(null);
                backgroundDisplay2.setBackground(null);
                backgroundDisplay3.setBackground(null);
                backgroundDisplay4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cyan_stroke_selected));

                iconWallet = R.drawable.bn_wallet_icon;
            }
        });

        updateWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataWalletModel dataWalletModel = new DataWalletModel(nameWallet.getText().toString(), nominalWallets, iconWallet, backgroundIconWallet);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference walletReference = database.getReference("wallets");

                walletReference.child(idWallets).setValue(dataWalletModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        Dialog dialog = new Dialog(EditWalletMenu.this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_blue));
        dialog.setCancelable(false);

        Button acceptBtn, rejectBtn;
        TextView taskText;

        acceptBtn = dialog.findViewById(R.id.accept_btn);
        rejectBtn = dialog.findViewById(R.id.reject_btn);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userWalletReference = database.getReference("user_wallets");
        DatabaseReference walletReference = database.getReference("wallets");
        DatabaseReference transactionReference = database.getReference("transactions");

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userWalletReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotUserWallet) {
                        for (DataSnapshot itemUserWallet : snapshotUserWallet.getChildren()) {
                            if (itemUserWallet.child("id_wallet").getValue(String.class).equals(idWallets)) {
                                userWalletReference.child(itemUserWallet.getKey()).removeValue();
                                walletReference.child(idWallets).removeValue();
                                transactionReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshotTransaction) {
                                        for (DataSnapshot itemTransaction : snapshotTransaction.getChildren()) {
                                            if(itemTransaction.child("id_wallets").getValue(String.class).equals(idWallets)) {
                                                transactionReference.child(itemTransaction.getKey()).removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                userWalletReference.child(idWallets).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
            }
        });

        taskText = dialog.findViewById(R.id.task_text);
        taskText.setText("Are you sure want to add wallet's balance to total balance?");

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }
}

