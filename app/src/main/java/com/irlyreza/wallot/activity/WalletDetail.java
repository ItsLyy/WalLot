package com.irlyreza.wallot.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.irlyreza.wallot.databinding.ActivityEditWalletMenuBinding;
import com.irlyreza.wallot.fragment.wallet_detail.WalletDetailDebt;
import com.irlyreza.wallot.fragment.wallet_detail.WalletDetailMember;
import com.irlyreza.wallot.fragment.wallet_detail.WalletDetailTransaction;

import java.util.Objects;

public class WalletDetail extends AppCompatActivity {
    TextView nameWallet, nominalWallet;
    ImageView iconWallet, iconMember, iconTransaction, iconDebt, walletEdit, walletLeave, walletNominalAdd;
    ConstraintLayout backgroundContainer;

    LinearLayout memberBtn, transactionBtn, debtBtn;
    Boolean isDisplay = false;

    String idWallet;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wallet_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nameWallet = findViewById(R.id.wallet_name);
        nominalWallet = findViewById(R.id.wallet_nominal);
        iconWallet = findViewById(R.id.wallet_icon);
        backgroundContainer = findViewById(R.id.background_container);
        walletLeave = findViewById(R.id.leave_wallet);
        walletNominalAdd = findViewById(R.id.nominal_display);

        Bundle bundle = getIntent().getExtras();
        nameWallet.setText(bundle.getString("name"));
        String displayNominal = bundle.getString("nominal");
        nominalWallet.setText(displayNominal);
        iconWallet.setImageResource(bundle.getInt("icon"));
        idWallet = bundle.getString("idWallet");

        SharedPreferences preferences = getSharedPreferences("LOGINAPP", Context.MODE_PRIVATE);
        idUser = preferences.getString("idUser", null);

        if (bundle.getInt("bgIcon") == R.drawable.bg_corner_gradient_blue) {
            backgroundContainer.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_gradient_blue));
            iconWallet.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_icon_gradient_blue));
        } else if (bundle.getInt("bgIcon") == R.drawable.bg_corner_gradient_green) {
            backgroundContainer.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_gradient_green));
            iconWallet.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_icon_gradient_green));
        } else if (bundle.getInt("bgIcon") == R.drawable.bg_corner_gradient_red) {
            backgroundContainer.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_gradient_red));
            iconWallet.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_icon_gradient_red));
        } else if (bundle.getInt("bgIcon") == R.drawable.bg_corner_gradient_purple) {
            backgroundContainer.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_gradient_purple));
            iconWallet.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_icon_gradient_purple));
        }

        memberBtn = findViewById(R.id.member_btn);
        transactionBtn = findViewById(R.id.transaction_btn);
        debtBtn = findViewById(R.id.debt_btn);

        iconMember = findViewById(R.id.member_icon);
        iconTransaction = findViewById(R.id.transaction_icon);
        iconDebt = findViewById(R.id.debt_icon);

        walletEdit = findViewById(R.id.edit_wallet);

        Bundle memberBundle = new Bundle();
        memberBundle.putString("idWallet", idWallet);
        WalletDetailMember walletDetailMember = new WalletDetailMember();
        walletDetailMember.setArguments(memberBundle);

        getSupportFragmentManager().beginTransaction().
                setReorderingAllowed(true).
                replace(R.id.fragment_wallet_detail_container, walletDetailMember).
                commit();

        memberBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_left1_btn));
        iconMember.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.light_white));

        walletEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditWalletMenu.class);
                intent.putExtra("idWallet", idWallet);
                intent.putExtra("name", bundle.getString("name"));
                intent.putExtra("nominal", bundle.getString("nominal"));
                intent.putExtra("icon", bundle.getInt("icon"));
                intent.putExtra("bgIcon", bundle.getInt("bgIcon"));
                startActivity(intent);
            }
        });
        memberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle memberBundle = new Bundle();
                memberBundle.putString("idWallet", idWallet);
                WalletDetailMember walletDetailMember = new WalletDetailMember();
                walletDetailMember.setArguments(memberBundle);

                memberBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_left1_btn));
                iconMember.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.light_white));

                transactionBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                iconTransaction.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

                debtBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_right_btn));
                iconDebt.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

                getSupportFragmentManager().beginTransaction().
                        setReorderingAllowed(true).
                        replace(R.id.fragment_wallet_detail_container, walletDetailMember).
                        commit();
            }
        });

        transactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_left_btn));
                iconMember.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

                transactionBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.cyan));
                iconTransaction.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.light_white));

                debtBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_right_btn));
                iconDebt.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

                Bundle transactionBundle = new Bundle();
                transactionBundle.putString("idWallet", idWallet);
                WalletDetailTransaction walletDetailTransaction = new WalletDetailTransaction();
                walletDetailTransaction.setArguments(transactionBundle);

                getSupportFragmentManager().beginTransaction().
                        setReorderingAllowed(true).
                        replace(R.id.fragment_wallet_detail_container, walletDetailTransaction).
                        commit();
            }
        });

        debtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle debtBundle = new Bundle();
                debtBundle.putString("idWallet", idWallet);
                WalletDetailDebt walletDetailTransaction = new WalletDetailDebt();
                walletDetailTransaction.setArguments(debtBundle);

                getSupportFragmentManager().beginTransaction().
                        setReorderingAllowed(true).
                        replace(R.id.fragment_wallet_detail_container, walletDetailTransaction).
                        commit();

                memberBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_left_btn));
                iconMember.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

                transactionBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                iconTransaction.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

                debtBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_right1_btn));
                iconDebt.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.light_white));
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userWalletReferencer = database.getReference("user_wallets");
        DatabaseReference walletReference = database.getReference("wallets");

        userWalletReferencer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userWalletItem : snapshot.getChildren()) {
                    if (Objects.equals(userWalletItem.child("role").getValue(String.class), "member") && Objects.equals(userWalletItem.child("id_user").getValue(String.class), idUser)) {
                        walletEdit.setVisibility(View.GONE);
                        walletLeave.setVisibility(View.VISIBLE);
                        walletLeave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (userWalletItem.child("id_user").getValue(String.class).equals(idUser) && userWalletItem.child("id_wallet").getValue(String.class).equals(idWallet)) {
                                    userWalletReferencer.child(userWalletItem.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });
                                }
                            }
                        });
                    } else if (Objects.equals(userWalletItem.child("role").getValue(String.class), "moderator") && Objects.equals(userWalletItem.child("id_user").getValue(String.class), idUser)) {
                        walletEdit.setVisibility(View.VISIBLE);
                    } else if (Objects.equals(userWalletItem.child("role").getValue(String.class), "admin") && Objects.equals(userWalletItem.child("id_user").getValue(String.class), idUser)) {
                        walletEdit.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userWalletReferencer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isDisplay = snapshot.child(idWallet).child("display").getValue(Boolean.class);
                if (isDisplay) {
                    walletNominalAdd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_nominal_display2_container));
                    walletNominalAdd.setColorFilter(getColor(R.color.white));
                } else {
                    walletNominalAdd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_nominal_display1_container));
                    walletNominalAdd.setColorFilter(getColor(R.color.cyan));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // NOTE dialog

        Dialog dialog = new Dialog(WalletDetail.this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_corner_gradient_blue));
        dialog.setCancelable(false);

        Button acceptBtn, rejectBtn;
        TextView taskText;

        acceptBtn = dialog.findViewById(R.id.accept_btn);
        rejectBtn = dialog.findViewById(R.id.reject_btn);

        taskText = dialog.findViewById(R.id.task_text);
        taskText.setText("Are you sure want to add wallet's balance to total balance?");

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDisplay) {
                    walletReference.child(idWallet).child("display").setValue(true);
                    walletNominalAdd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_nominal_display2_container));
                    walletNominalAdd.setColorFilter(getColor(R.color.white));
                }
                dialog.dismiss();
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        walletNominalAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!isDisplay) {
                    dialog.show();
                } else {
                    walletReference.child(idWallet).child("display").setValue(false);
                    walletNominalAdd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_nominal_display1_container));
                    walletNominalAdd.setColorFilter(getColor(R.color.cyan));
                }
            }
        });
    }
}
