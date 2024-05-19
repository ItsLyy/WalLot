package com.irlyreza.wallot;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        horizontalWallet.setLayoutManager(linearLayoutManager);
        addData();
        debtHorizontalListAdapter = new DebtHorizontalListAdapter(getActivity() ,getActivity().getApplicationContext(), debtArray);
        linearLayoutManager1 = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        horizontalDebt.setLayoutManager(linearLayoutManager1);
        horizontalDebt.setAdapter(debtHorizontalListAdapter);

        newestTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), EditTransactionMenu.class);
//                intent.putExtra("nominal", transactionArray.get(i).money);
//                intent.putExtra("description", transactionArray.get(i).category);
//                intent.putExtra("date", transactionArray.get(i).date);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    void addData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference transactionReference = database.getReference("transactions");
        DatabaseReference walletReference = database.getReference("wallets");

        transactionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionArray = new ArrayList<>();
                for (DataSnapshot transactionItem: snapshot.getChildren()) {
                    DataTransactionModel dataTransactionModel = transactionItem.getValue(DataTransactionModel.class);
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

        walletReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                walletArray = new ArrayList<>();
                for (DataSnapshot walletItem : snapshot.getChildren()) {
                    DataWalletModel dataWalletModel = walletItem.getValue(DataWalletModel.class);
                    dataWalletModel.setId_wallet(walletItem.getKey());
                    walletArray.add(dataWalletModel);
                }
                if(getActivity() != null) {
                    walletHorizontalListAdapter = new WalletHorizontalListAdapter(getActivity(), getActivity().getApplicationContext(), walletArray);
                }
                horizontalWallet.setAdapter(walletHorizontalListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        debtArray = new ArrayList<>();

    }
}