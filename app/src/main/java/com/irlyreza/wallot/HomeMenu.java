package com.irlyreza.wallot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    String[] categoryTransaction = {"Cash", "Wallet", "Cash", "Cash"};
    String[] dateTransaction = {"01-12-2024", "02-12-2024", "04-12-2024", "20-12-2024"};
    String[] moneyTransaction = { "20.000", "10.000", "120.000", "400.000" };
    int[] iconTransaction = { R.drawable.category_cash_icon, R.drawable.category_cash_icon, R.drawable.category_cash_icon, R.drawable.category_cash_icon };

    Transaction_Data transactionData;
    TransactionListAdapter transactionListAdapter;
    ArrayList<Transaction_Data> data;


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
        addData();
        transactionListAdapter = new TransactionListAdapter(getActivity().getApplicationContext(), data);
        newestTransaction.setAdapter(transactionListAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    void addData() {
        data = new ArrayList<>();
        data.add(new Transaction_Data("Cash", "20.000", "12-12-2012", R.drawable.category_cash_icon));
        data.add(new Transaction_Data("Wallet", "20.000", "12-12-2012", R.drawable.category_cash_icon));
        data.add(new Transaction_Data("coawij", "20.000", "12-12-2012", R.drawable.category_cash_icon));
        data.add(new Transaction_Data("ejkf", "20.000", "12-12-2012", R.drawable.category_cash_icon));
    }
}