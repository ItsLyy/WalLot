package com.irlyreza.wallot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
    ArrayList<WalLot_Data.Transaction_Data> transactionArray;
    ArrayList<WalLot_Data.Debt_Data> debtArray;

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
        transactionListAdapter = new TransactionListAdapter(getActivity().getApplicationContext(), transactionArray);
        walletTransaction = view.findViewById(R.id.wallet_transaction_list);
        walletTransaction.setAdapter(transactionListAdapter);
        return view;
    }

    void addData() {
        transactionArray = new ArrayList<>();
        transactionArray.add(new WalLot_Data.Transaction_Data("Cash", "20.000", "12-12-2012", R.drawable.category_cash_icon));
        transactionArray.add(new WalLot_Data.Transaction_Data("Wallet", "20.000", "12-12-2012", R.drawable.category_cash_icon));
        transactionArray.add(new WalLot_Data.Transaction_Data("coawij", "20.000", "12-12-2012", R.drawable.category_cash_icon));
        transactionArray.add(new WalLot_Data.Transaction_Data("ejkf", "20.000", "12-12-2012", R.drawable.category_cash_icon));

        debtArray = new ArrayList<>();
        debtArray.add(new WalLot_Data.Debt_Data("SJA", "20.000", "12-20-2022", R.drawable.category_cash_icon));
        debtArray.add(new WalLot_Data.Debt_Data("SJA", "20.000", "12-20-2022", R.drawable.category_cash_icon));
        debtArray.add(new WalLot_Data.Debt_Data("SJA", "20.000", "12-20-2022", R.drawable.category_cash_icon));
        debtArray.add(new WalLot_Data.Debt_Data("SJA", "20.000", "12-20-2022", R.drawable.category_cash_icon));
    }
}