package com.irlyreza.wallot.fragment.main_activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irlyreza.wallot.R;
import com.irlyreza.wallot.activity.AddWalletMenu;
import com.irlyreza.wallot.adapter.WalletVerticallyListAdapter;
import com.irlyreza.wallot.data.DataWalletModel;

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
    ArrayList<DataWalletModel> walletData;
    LinearLayout walletAddBtn;


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
        walletAddBtn = view.findViewById(R.id.add_wallet);
        addData();
        walletList.setLayoutManager(new LinearLayoutManager(getActivity()));

        walletAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddWalletMenu.class);
                getActivity().startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    void addData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference walletReference = database.getReference("wallets");
        walletReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                walletData = new ArrayList<>();
                for (DataSnapshot walletItem : snapshot.getChildren()) {
                    DataWalletModel dataWalletModel = walletItem.getValue(DataWalletModel.class);
                    dataWalletModel.setId_wallet(walletItem.getKey());
                    walletData.add(dataWalletModel);
                }
                if(getActivity() != null) {
                    walletVerticallyListAdapter = new WalletVerticallyListAdapter(getActivity(), getActivity().getApplicationContext(), walletData);
                    walletList.setAdapter(walletVerticallyListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}