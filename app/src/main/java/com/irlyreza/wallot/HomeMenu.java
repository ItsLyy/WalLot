package com.irlyreza.wallot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    LinearLayoutManager linearLayoutManager;

    Transaction_Data transactionData;
    Wallet_Data walletData;
    TransactionListAdapter transactionListAdapter;
    WalletHorizontalListAdapter walletHorizontalListAdapter;
    ArrayList<Transaction_Data> transactionArray;
    ArrayList<Wallet_Data> walletArray;


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
        addData();
        transactionListAdapter = new TransactionListAdapter(getActivity().getApplicationContext(), transactionArray);
        walletHorizontalListAdapter = new WalletHorizontalListAdapter(getActivity().getApplicationContext(), walletArray);
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        newestTransaction.setAdapter(transactionListAdapter);
        horizontalWallet.setLayoutManager(linearLayoutManager);
        horizontalWallet.setAdapter(walletHorizontalListAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    void addData() {
        transactionArray = new ArrayList<>();
        transactionArray.add(new Transaction_Data("Cash", "20.000", "12-12-2012", R.drawable.category_cash_icon));
        transactionArray.add(new Transaction_Data("Wallet", "20.000", "12-12-2012", R.drawable.category_cash_icon));
        transactionArray.add(new Transaction_Data("coawij", "20.000", "12-12-2012", R.drawable.category_cash_icon));
        transactionArray.add(new Transaction_Data("ejkf", "20.000", "12-12-2012", R.drawable.category_cash_icon));

        walletArray = new ArrayList<>();
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletArray.add(new Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
    }
}