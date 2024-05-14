package com.irlyreza.wallot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WalletDetail extends AppCompatActivity {
    TextView nameWallet, nominalWallet;
    ImageView iconWallet;
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

    }
}