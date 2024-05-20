package com.irlyreza.wallot.fragment.wallet_detail;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irlyreza.wallot.R;
import com.irlyreza.wallot.adapter.MemberListAdapter;
import com.irlyreza.wallot.data.DataUserWalletModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletDetailMember#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletDetailMember extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String idWallet, idUser;
    RecyclerView listMember;

    Button addMemberBtn;
    ArrayList<DataUserWalletModel> userWalletArray;
    public WalletDetailMember() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletDetailMember.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletDetailMember newInstance(String param1, String param2) {
        WalletDetailMember fragment = new WalletDetailMember();
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
        View view = inflater.inflate(R.layout.fragment_wallet_detail_member, container, false);
        View viewWalletDetail = inflater.inflate(R.layout.activity_wallet_detail, container, false);

        SharedPreferences preferences = getActivity().getSharedPreferences("LOGINAPP", Context.MODE_PRIVATE);
        idUser = preferences.getString("idUser", null);
        idWallet = this.getArguments().getString("idWallet");

        addMemberBtn = view.findViewById(R.id.wallet_add_member_btn);
        listMember = view.findViewById(R.id.wallet_member_list);

        listMember.setLayoutManager(new LinearLayoutManager(getActivity()));

        // TODO tampilkan data
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userWalletReference = database.getReference("user_wallets");

        userWalletReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotUserWallet) {
                userWalletArray = new ArrayList<>();
                for (DataSnapshot userWalletItem : snapshotUserWallet.getChildren()) {
                    if(Objects.equals(userWalletItem.child("id_wallet").getValue(String.class), idWallet)) {
                        DataUserWalletModel dataUserWalletModel = userWalletItem.getValue(DataUserWalletModel.class);
                        dataUserWalletModel.setId_user_wallet(userWalletItem.getKey());
                        userWalletArray.add(dataUserWalletModel);
                    }
                }
                if (getActivity() != null) {
                    MemberListAdapter memberListAdapter = new MemberListAdapter(getActivity(), getActivity().getApplicationContext(), userWalletArray);
                    listMember.setAdapter(memberListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View views) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupSelector = inflater.inflate(R.layout.activity_add_friend_overlay, null);

                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                EditText idFriend = popupSelector.findViewById(R.id.idFriend);
                boolean focusable = true;
                PopupWindow popupWindow = new PopupWindow(popupSelector, width, height, focusable);
                view.findViewById(R.id.main).post(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.showAtLocation(viewWalletDetail.findViewById(R.id.main), Gravity.BOTTOM, 0, 0);
                    }
                });
                popupSelector.findViewById(R.id.main).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupSelector.findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference userWalletReference = database.getReference("user_wallets");
                        DataUserWalletModel dataUserWalletModel = new DataUserWalletModel(idUser, idFriend.getText().toString(), "member");

                        String idUserWallet = userWalletReference.push().getKey();
                        userWalletReference.child(idUserWallet).setValue(dataUserWalletModel);

                        popupWindow.dismiss();
                    }
                });
            }
        });

        return view;
    }
}