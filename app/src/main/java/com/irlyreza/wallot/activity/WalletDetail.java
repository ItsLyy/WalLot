package com.irlyreza.wallot.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.irlyreza.wallot.R;
import com.irlyreza.wallot.fragment.wallet_detail.WalletDetailDebt;
import com.irlyreza.wallot.fragment.wallet_detail.WalletDetailMember;
import com.irlyreza.wallot.fragment.wallet_detail.WalletDetailTransaction;

public class WalletDetail extends AppCompatActivity {
    TextView nameWallet, nominalWallet;
    ImageView iconWallet, iconMember, iconTransaction, iconDebt;
    RelativeLayout backgroundContainer;

    LinearLayout memberBtn, transactionBtn, debtBtn;

    String idWallet;
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

        Bundle bundle = getIntent().getExtras();
        nameWallet.setText(bundle.getString("name"));
        nominalWallet.setText(bundle.getString("nominal"));
        iconWallet.setImageResource(bundle.getInt("icon"));
        idWallet = bundle.getString("idWallet");

        if(bundle.getInt("bgIcon") == R.drawable.bg_corner_gradient_blue) {
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
    }
}