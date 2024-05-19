package com.irlyreza.wallot.fragment.wallet_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irlyreza.wallot.R;
import com.irlyreza.wallot.adapter.DebtVerticalListAdapter;
import com.irlyreza.wallot.data.DataDebtModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletDetailDebt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletDetailDebt extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<DataDebtModel> debtArray;
    DebtVerticalListAdapter debtVerticalListAdapter;
    RecyclerView debtVerticalList;
    String idWallet;

    public WalletDetailDebt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletDetailDebt.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletDetailDebt newInstance(String param1, String param2) {
        WalletDetailDebt fragment = new WalletDetailDebt();
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
        View view = inflater.inflate(R.layout.fragment_wallet_detail_debt, container, false);
        debtVerticalList = view.findViewById(R.id.debt_vertical_list);
        addData();
        debtVerticalList.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }
    void addData() {
        idWallet = this.getArguments().getString("idWallet");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference debtReference = database.getReference("debts");

        debtReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                debtArray = new ArrayList<>();

                for (DataSnapshot debtItem : snapshot.getChildren()) {
                    if (debtItem.child("id_wallet").getValue(String.class).equals(idWallet)) {
                        DataDebtModel dataDebtModel = debtItem.getValue(DataDebtModel.class);
                        dataDebtModel.setId_debt(debtItem.getKey());
                        debtArray.add(dataDebtModel);
                    }
                }
                if (!getActivity().equals(null)) {
                    debtVerticalListAdapter = new DebtVerticalListAdapter(getActivity(), getActivity().getApplicationContext(), debtArray);
                    debtVerticalList.setAdapter(debtVerticalListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}