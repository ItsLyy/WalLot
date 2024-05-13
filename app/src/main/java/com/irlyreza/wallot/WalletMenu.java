package com.irlyreza.wallot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    WalletVerticallyListAdapter walletVerticallyListAdapter;
    RecyclerView walletList;
    ArrayList<WalLot_Data.Wallet_Data> walletData;


    public WalletMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletMenu newInstance(String param1, String param2) {
        WalletMenu fragment = new WalletMenu();
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
        View view = inflater.inflate(R.layout.fragment_wallet_menu, container, false);
        walletList = view.findViewById(R.id.vertical_wallet_list);
        addData();
        walletVerticallyListAdapter = new WalletVerticallyListAdapter(getActivity().getApplicationContext(), walletData);
        walletList.setLayoutManager(new LinearLayoutManager(getActivity()));
        walletList.setAdapter(walletVerticallyListAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    void addData() {
        walletData = new ArrayList<>();
        walletData.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletData.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletData.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletData.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletData.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletData.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletData.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletData.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletData.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
        walletData.add(new WalLot_Data.Wallet_Data("MSA", "20.0000", "12-20-2012", R.drawable.category_cash_icon));
    }
}