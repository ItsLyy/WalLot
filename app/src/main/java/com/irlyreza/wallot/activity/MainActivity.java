package com.irlyreza.wallot.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irlyreza.wallot.fragment.main_activity.HomeMenu;
import com.irlyreza.wallot.R;
import com.irlyreza.wallot.fragment.main_activity.WalletMenu;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ImageView homeIcon;
    ImageView reportIcon;
    ImageView groupIcon;
    ImageView profileIcon;

    TextView homeLabel;
    TextView reportLabel;
    TextView groupLabel;
    TextView profileLabel;


    LinearLayout homeBtn;
    LinearLayout reportBtn;
    LinearLayout groupBtn;
    LinearLayout profileBtn;
    LinearLayout plusTransactionBtn;

    RelativeLayout bodyContainer;
    ListView listTest;
    static public int totalBalance;
    String displayBalance;

    private int selectedTab = 1;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.body_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadData();

        // Bottom Navigatior == Start

        homeBtn = findViewById(R.id.btn_home);
        reportBtn = findViewById(R.id.btn_report);
        groupBtn = findViewById(R.id.btn_group);
        profileBtn = findViewById(R.id.btn_profile);
        plusTransactionBtn = findViewById(R.id.btn_plus_transaction);



        homeIcon = findViewById(R.id.icon_home);
        reportIcon = findViewById(R.id.icon_report);
        groupIcon = findViewById(R.id.icon_group);
        profileIcon = findViewById(R.id.icon_profile);

        homeLabel = findViewById(R.id.label_home);
        reportLabel = findViewById(R.id.label_report);
        groupLabel = findViewById(R.id.label_group);
        profileLabel = findViewById(R.id.label_profile);

        homeLabel.setVisibility(View.VISIBLE);
        homeIcon.setColorFilter(getResources().getColor(R.color.cyan));
        homeLabel.setTextColor(getResources().getColor(R.color.cyan));

        reportIcon.setColorFilter(Color.BLACK);
        groupIcon.setColorFilter(Color.BLACK);
        profileIcon.setColorFilter(Color.BLACK);

        reportLabel.setTextColor(Color.BLACK);
        groupLabel.setTextColor(Color.BLACK);
        profileLabel.setTextColor(Color.BLACK);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_PARENT, -1f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);

        bodyContainer = findViewById(R.id.body_container);
        getSupportFragmentManager().beginTransaction().
                setReorderingAllowed(true).
                replace(R.id.fragment_container, HomeMenu.class, null).
                commit();

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 1) {
                    getSupportFragmentManager().beginTransaction().
                            setReorderingAllowed(true).
                            replace(R.id.fragment_container, HomeMenu.class, null).
                            commit();

                    homeLabel.setVisibility(View.VISIBLE);
                    reportLabel.setVisibility(View.GONE);
                    groupLabel.setVisibility(View.GONE);
                    profileLabel.setVisibility(View.GONE);

                    homeLabel.setAnimation(scaleAnimation);
                    homeIcon.setColorFilter(getResources().getColor(R.color.cyan));
                    homeLabel.setTextColor(getResources().getColor(R.color.cyan));

                    reportIcon.setColorFilter(Color.BLACK);
                    groupIcon.setColorFilter(Color.BLACK);
                    profileIcon.setColorFilter(Color.BLACK);

                    reportLabel.setTextColor(Color.BLACK);
                    groupLabel.setTextColor(Color.BLACK);
                    profileLabel.setTextColor(Color.BLACK);

                    selectedTab = 1;
                }
            }
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 2) {
                    getSupportFragmentManager().beginTransaction().
                            setReorderingAllowed(true).
                            replace(R.id.fragment_container, WalletMenu.class, null).
                            commit();

                    homeLabel.setVisibility(View.GONE);
                    reportLabel.setVisibility(View.VISIBLE);
                    groupLabel.setVisibility(View.GONE);
                    profileLabel.setVisibility(View.GONE);

                    reportLabel.setAnimation(scaleAnimation);
                    reportIcon.setColorFilter(getResources().getColor(R.color.cyan));
                    reportLabel.setTextColor(getResources().getColor(R.color.cyan));

                    homeIcon.setColorFilter(Color.BLACK);
                    groupIcon.setColorFilter(Color.BLACK);
                    profileIcon.setColorFilter(Color.BLACK);

                    homeLabel.setTextColor(Color.BLACK);
                    groupLabel.setTextColor(Color.BLACK);
                    profileLabel.setTextColor(Color.BLACK);

                    selectedTab = 2;
                }
            }
        });

        groupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 3) {
                    getSupportFragmentManager().beginTransaction().
                            setReorderingAllowed(true).
                            replace(R.id.fragment_container, WalletMenu.class, null).
                            commit();

                    homeLabel.setVisibility(View.GONE);
                    reportLabel.setVisibility(View.GONE);
                    groupLabel.setVisibility(View.VISIBLE);
                    profileLabel.setVisibility(View.GONE);

                    groupLabel.setAnimation(scaleAnimation);
                    groupIcon.setColorFilter(getResources().getColor(R.color.cyan));
                    groupLabel.setTextColor(getResources().getColor(R.color.cyan));

                    homeIcon.setColorFilter(Color.BLACK);
                    reportIcon.setColorFilter(Color.BLACK);
                    profileIcon.setColorFilter(Color.BLACK);

                    homeLabel.setTextColor(Color.BLACK);
                    reportLabel.setTextColor(Color.BLACK);
                    profileLabel.setTextColor(Color.BLACK);


                    selectedTab = 3;
                }
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 4) {
                    getSupportFragmentManager().beginTransaction().
                            setReorderingAllowed(true).
                            replace(R.id.fragment_container, WalletMenu.class, null).
                            commit();

                    homeLabel.setVisibility(View.GONE);
                    reportLabel.setVisibility(View.GONE);
                    groupLabel.setVisibility(View.GONE);
                    profileLabel.setVisibility(View.VISIBLE);

                    profileLabel.setAnimation(scaleAnimation);
                    profileIcon.setColorFilter(getResources().getColor(R.color.cyan));
                    profileLabel.setTextColor(getResources().getColor(R.color.cyan));

                    homeIcon.setColorFilter(Color.BLACK);
                    reportIcon.setColorFilter(Color.BLACK);
                    groupIcon.setColorFilter(Color.BLACK);

                    homeLabel.setTextColor(Color.BLACK);
                    reportLabel.setTextColor(Color.BLACK);
                    groupLabel.setTextColor(Color.BLACK);

                    selectedTab = 4;
                }
            }
        });

        plusTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupSelector = inflater.inflate(R.layout.activity_transaction, null);

                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                PopupWindow popupWindow = new PopupWindow(popupSelector, width, height, focusable);
                bodyContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.showAtLocation(bodyContainer, Gravity.BOTTOM, 0, 0);
                    }
                });

                LinearLayout debtBtn, transactionBtn;
                RelativeLayout backgroundTransactionSelector;
                backgroundTransactionSelector = popupSelector.findViewById(R.id.background_transaction_selector);
                debtBtn = popupSelector.findViewById(R.id.debt_btn);
                transactionBtn = popupSelector.findViewById(R.id.transaction_btn);

                backgroundTransactionSelector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                transactionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), TransactionMenu.class);
                        startActivity(intent);
                    }
                });

                debtBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), DebtMenu.class);
                        startActivity(intent);
                    }
                });
            }
        });

        // Bottom Navigator == End


    }

    public void loadData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference walletReference = database.getReference("wallets");
        DatabaseReference transactionReference = database.getReference("transactions");

        walletReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotWallet) {
                transactionReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotTransaction) {
                        for (DataSnapshot walletItem : snapshotWallet.getChildren()) {
                            totalBalance = 0;
                            displayBalance = "";
                            for (DataSnapshot transactionItem : snapshotTransaction.getChildren()) {
                                if (Objects.equals(transactionItem.child("id_wallet").getValue(String.class), walletItem.getKey())) {
                                    if(Objects.equals(transactionItem.child("type").getValue(String.class), "income")) {
                                        totalBalance += Integer.parseInt(unformatRupiah(transactionItem.child("nominal").getValue(String.class)));
                                    } else if (Objects.equals(transactionItem.child("type").getValue(String.class), "outcome")) {
                                        totalBalance -= Integer.parseInt(unformatRupiah(transactionItem.child("nominal").getValue(String.class)));
                                    }
                                }
                            }
                            displayBalance = String.valueOf(totalBalance);
                            String replace = displayBalance.toString().replaceAll("[Rp. ]", "");
                            walletReference.child(walletItem.getKey()).child("nominal").setValue(formatRupiah(Double.parseDouble(replace)));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String formatRupiah(Double number) {
        Locale localeId = new Locale("IND", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeId);
        String formatrupiah = numberFormat.format(number);
        String[] split = formatrupiah.split(",");
        int length = split[0].length();
        return  split[0].substring(2,length).replace("p", "-");
    }

    private String unformatRupiah(String number) {
        String split = number.replace(".", "");
        return  split;
    }
}