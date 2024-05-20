package com.irlyreza.wallot.fragment.main_activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irlyreza.wallot.R;
import com.irlyreza.wallot.activity.EditTransactionMenu;
import com.irlyreza.wallot.adapter.DebtHorizontalListAdapter;
import com.irlyreza.wallot.adapter.TransactionListAdapter;
import com.irlyreza.wallot.adapter.WalletHorizontalListAdapter;
import com.irlyreza.wallot.data.DataDebtModel;
import com.irlyreza.wallot.data.DataTransactionModel;
import com.irlyreza.wallot.data.DataUserWalletModel;
import com.irlyreza.wallot.data.DataWalletModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView newestTransaction;
    RecyclerView horizontalWallet;
    RecyclerView horizontalDebt;
    TextView username;
    String idUser;
    LinearLayoutManager linearLayoutManager, linearLayoutManager1;

    TransactionListAdapter transactionListAdapter;
    WalletHorizontalListAdapter walletHorizontalListAdapter;
    DebtHorizontalListAdapter debtHorizontalListAdapter;
    ArrayList<DataTransactionModel> transactionArray;
    ArrayList<DataWalletModel> walletArray;
    ArrayList<DataDebtModel> debtArray;


    public HomeMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeMenus.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeMenu newInstance(String param1, String param2) {
        HomeMenu fragment = new HomeMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu, container, false);
        newestTransaction = view.findViewById(R.id.newest_transaction);
        horizontalWallet = view.findViewById(R.id.horizontal_wallet_list);
        horizontalDebt = view.findViewById(R.id.horizontal_debt_list);
        username = view.findViewById(R.id.username);

        SharedPreferences preferences = getActivity().getSharedPreferences("LOGINAPP", Context.MODE_PRIVATE);
        idUser = preferences.getString("idUser", null);

        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        horizontalWallet.setLayoutManager(linearLayoutManager);
        addData();

        linearLayoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        horizontalDebt.setLayoutManager(linearLayoutManager1);

        newestTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), EditTransactionMenu.class);
                intent.putExtra("id_transaction", transactionArray.get(adapterView.getCount() - i - 1).getId_transaction());
                intent.putExtra("nominal", transactionArray.get(adapterView.getCount() - i - 1).getNominal());
                intent.putExtra("description", transactionArray.get(adapterView.getCount() - i - 1).getDescription());
                intent.putExtra("id_wallet", transactionArray.get(adapterView.getCount() - i - 1).getId_wallet());
                intent.putExtra("date", transactionArray.get(adapterView.getCount() - i - 1).getDate());
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    void addData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference("users");
        DatabaseReference transactionReference = database.getReference("transactions");
        DatabaseReference walletReference = database.getReference("wallets");
        DatabaseReference userWalletReference = database.getReference("user_wallets");
        DatabaseReference debtReference = database.getReference("debts");

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot userItem = snapshot.child(idUser);
                username.setText(userItem.child("nameUser").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        transactionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionArray = new ArrayList<>();
                for (DataSnapshot transactionItem: snapshot.getChildren()) {
                    DataTransactionModel dataTransactionModel = transactionItem.getValue(DataTransactionModel.class);
                    dataTransactionModel.setId_transaction(transactionItem.getKey());
                    transactionArray.add(dataTransactionModel);
                }
                if (getActivity() != null) {
                    transactionListAdapter = new TransactionListAdapter(getActivity().getApplicationContext(), transactionArray);
                    newestTransaction.setAdapter(transactionListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userWalletReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotUserWallet) {
                walletReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotWallet) {
                            walletArray = new ArrayList<>();
                            for (DataSnapshot walletItem : snapshotWallet.getChildren()) {
                                for (DataSnapshot userWalletItem : snapshotUserWallet.getChildren()) {
                                if (Objects.equals(userWalletItem.child("id_user").getValue(String.class), idUser) && Objects.equals(userWalletItem.child("id_wallet").getValue(String.class), walletItem.getKey())) {
                                    DataWalletModel dataWalletModel = walletItem.getValue(DataWalletModel.class);
                                    dataWalletModel.setId_wallet(walletItem.getKey());
                                    walletArray.add(dataWalletModel);
                                }
                            }
                        if(getActivity() != null) {
                            walletHorizontalListAdapter = new WalletHorizontalListAdapter(getActivity(), getActivity().getApplicationContext(), walletArray);
                        }
                        horizontalWallet.setAdapter(walletHorizontalListAdapter);
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

        debtReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                debtArray = new ArrayList<>();
                for (DataSnapshot debtItem : snapshot.getChildren()) {
                    DataDebtModel dataDebtModel = debtItem.getValue(DataDebtModel.class);
                    dataDebtModel.setId_debt(debtItem.getKey());
                    debtArray.add(dataDebtModel);
                }

                if(getActivity() != null) {
                    debtHorizontalListAdapter = new DebtHorizontalListAdapter(getActivity() ,getActivity().getApplicationContext(), debtArray);
                    horizontalDebt.setAdapter(debtHorizontalListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}