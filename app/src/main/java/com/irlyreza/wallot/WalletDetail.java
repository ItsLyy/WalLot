package com.irlyreza.wallot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WalletDetail extends AppCompatActivity {
    TextView nameWallet, nominalWallet;
    ImageView iconWallet, iconMember, iconTransaction, iconDebt;

    LinearLayout memberBtn, transactionBtn, debtBtn;
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
        Bundle bundle = getIntent().getExtras();
        nameWallet.setText(bundle.getString("name"));
        nominalWallet.setText(bundle.getString("money"));
        iconWallet.setImageResource(bundle.getInt("icon"));

        memberBtn = findViewById(R.id.member_btn);
        transactionBtn = findViewById(R.id.transaction_btn);
        debtBtn = findViewById(R.id.debt_btn);

        iconMember = findViewById(R.id.member_icon);
        iconTransaction = findViewById(R.id.transaction_icon);
        iconDebt = findViewById(R.id.debt_icon);

        getSupportFragmentManager().beginTransaction().
                setReorderingAllowed(true).
                replace(R.id.fragment_wallet_detail_container, WalletDetailMember.class, null).
                commit();

        memberBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_left1_btn));
        iconMember.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.light_white));

        memberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_left1_btn));
                iconMember.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.light_white));

                transactionBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                iconTransaction.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

                debtBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_right_btn));
                iconDebt.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

                getSupportFragmentManager().beginTransaction().
                        setReorderingAllowed(true).
                        replace(R.id.fragment_wallet_detail_container, WalletDetailMember.class, null).
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

                getSupportFragmentManager().beginTransaction().
                        setReorderingAllowed(true).
                        replace(R.id.fragment_wallet_detail_container, WalletDetailTransaction.class, null).
                        commit();
            }
        });

        debtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().
                        setReorderingAllowed(true).
                        replace(R.id.fragment_wallet_detail_container, WalletDetailDebt.class, null).
                        commit();

                memberBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_left_btn));
                iconMember.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

                transactionBtn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                iconTransaction.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan));

                debtBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.wallet_tab_background_right1_btn));
                iconDebt.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.light_white));
            }
        });

    }
}