package com.irlyreza.wallot;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletDetailTransaction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletDetailTransaction extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView walletTransaction;
    TransactionListAdapter transactionListAdapter;
    ArrayList<DataTransactionModel> transactionArray;
    ArrayList<WalLot_Data.Debt_Data> debtArray;
    String idWallet;

    public WalletDetailTransaction() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletDetailTransaction.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletDetailTransaction newInstance(String param1, String param2) {
        WalletDetailTransaction fragment = new WalletDetailTransaction();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_detail_transaction, container, false);
        addData();

        walletTransaction = view.findViewById(R.id.wallet_transaction_list);


        walletTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), EditTransactionMenu.class);
//                intent.putExtra("nominal", transactionArray.get(i).money);
//                intent.putExtra("description", transactionArray.get(i).category);
//                intent.putExtra("date", transactionArray.get(i).date);
                startActivity(intent);
            }
        });



        return view;
    }

    void addData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference transactionReference = database.getReference("transactions");
        idWallet = this.getArguments().getString("idWallet");

        transactionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionArray = new ArrayList<>();
                for (DataSnapshot transactionItem: snapshot.getChildren()) {
                    DataTransactionModel dataTransactionModel = new DataTransactionModel();
                    if (transactionItem.child("id_wallet").getValue(String.class).equals(idWallet)) {
                        dataTransactionModel.setId_transaction(transactionItem.getKey());
                        dataTransactionModel.setDate(transactionItem.child("date").getValue(String.class));
                        dataTransactionModel.setDescription(transactionItem.child("description").getValue(String.class));
                        dataTransactionModel.setNominal(transactionItem.child("nominal").getValue(String.class));
                        transactionArray.add(dataTransactionModel);
                    }
                }
                if (getActivity() != null) {
                    transactionListAdapter = new TransactionListAdapter(getActivity().getApplicationContext(), transactionArray);
                    walletTransaction.setAdapter(transactionListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        debtArray = new ArrayList<>();
        debtArray.add(new WalLot_Data.Debt_Data("SJA", "20.000", "12-20-2022", R.drawable.category_cash_icon));
        debtArray.add(new WalLot_Data.Debt_Data("SJA", "20.000", "12-20-2022", R.drawable.category_cash_icon));
        debtArray.add(new WalLot_Data.Debt_Data("SJA", "20.000", "12-20-2022", R.drawable.category_cash_icon));
        debtArray.add(new WalLot_Data.Debt_Data("SJA", "20.000", "12-20-2022", R.drawable.category_cash_icon));
    }
}