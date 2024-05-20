package com.irlyreza.wallot.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irlyreza.wallot.R;
import com.irlyreza.wallot.activity.WalletDetail;
import com.irlyreza.wallot.data.DataUserWalletModel;
import com.irlyreza.wallot.data.DataWalletModel;
import com.irlyreza.wallot.data.RoleListData;

import java.util.ArrayList;
import java.util.Objects;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MyHolder> {
    ArrayList<DataUserWalletModel> model;
    ArrayList<RoleListData> roleList = new ArrayList<>();
    Context context, fragmentContext;
    String idUser;

    public MemberListAdapter(Context fragmentContext, Context context, ArrayList<DataUserWalletModel> model, String idUser) {
        this.context = context;
        this.model = model;
        this.fragmentContext = fragmentContext;
        this.idUser = idUser;

        roleList.add(new RoleListData("Member", true));
        roleList.add(new RoleListData("Moderator", true));
        roleList.add(new RoleListData("Admin", false));
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.recycle_view_member_wallet_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        RoleSpinnerAdapter roleSpinnerAdapter = new RoleSpinnerAdapter(this.context, roleList);

        holder.role.setAdapter(roleSpinnerAdapter);

        DataUserWalletModel dataUserWalletModel = this.model.get(position);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReferencer = database.getReference("users");
        DatabaseReference userWalletReference = database.getReference("user_wallets");

        try {
            java.lang.reflect.Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(holder.role);

            popupWindow.setHeight(210);
        } catch (Exception e) {
            e.printStackTrace();
        }

        userReferencer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.name.setText(snapshot.child(dataUserWalletModel.getId_user()).child("nameUser").getValue(String.class));
                holder.icon.setImageResource(snapshot.child(dataUserWalletModel.getId_user()).child("profileImage").getValue(Integer.class));
                if (dataUserWalletModel.getRole().equals("member")) {
                    holder.kickBtn.setVisibility(View.VISIBLE);
                    holder.role.setVisibility(View.VISIBLE);
                    holder.role.setSelection(0);
                } else if (dataUserWalletModel.getRole().equals("moderator")) {
                    holder.role.setVisibility(View.VISIBLE);
                    holder.role.setSelection(1);
                } else if (dataUserWalletModel.getRole().equals("admin")) {
                    holder.kickBtn.setVisibility(View.GONE);
                    holder.role.setVisibility(View.VISIBLE);
                    holder.role.setEnabled(false);
                    holder.role.setSelection(2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RoleListData roleListData = (RoleListData) adapterView.getItemAtPosition(i);
                if (roleListData.getRole().toString().equals("Member")) {
                } else if (roleListData.getRole().toString().equals("Moderator")) {
                } else if (roleListData.getRole().toString().equals("Admin")) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        holder.kickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userWalletReference.child(dataUserWalletModel.getId_user_wallet()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon, kickBtn;
        Spinner role;



        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_member);
            icon = itemView.findViewById(R.id.icon_member);
            kickBtn = itemView.findViewById(R.id.kickBtn);
            role = itemView.findViewById(R.id.role_member);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userWalletReference = database.getReference("user_wallets");

            userWalletReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userWalletItem : snapshot.getChildren()) {
                        if (userWalletItem.child("role").getValue(String.class).toString().equals("Member")) {
                            userWalletReference.child(userWalletItem.getKey()).child("role").setValue("member");
                            role.setSelection(0);
                        } else if (userWalletItem.child("role").getValue(String.class).toString().equals("Moderator")) {
                            userWalletReference.child(userWalletItem.getKey()).child("role").setValue("moderator");
                            role.setSelection(1);
                        } else if (userWalletItem.child("role").getValue(String.class).toString().equals("Admin")) {
                            userWalletReference.child(userWalletItem.getKey()).child("role").setValue("admin");
                            role.setSelection(2);
                        }
                        if (Objects.equals(userWalletItem.child("role").getValue(String.class), "member") && Objects.equals(userWalletItem.child("id_user").getValue(String.class), idUser)) {
                            role.setEnabled(false);
                            kickBtn.setVisibility(View.GONE);
                        } else if (Objects.equals(userWalletItem.child("role").getValue(String.class), "moderator") && Objects.equals(userWalletItem.child("id_user").getValue(String.class), idUser)) {
                            role.setVisibility(View.VISIBLE);
                        } else if (Objects.equals(userWalletItem.child("role").getValue(String.class), "admin") && Objects.equals(userWalletItem.child("id_user").getValue(String.class), idUser)) {
                            role.setVisibility(View.VISIBLE);
                            kickBtn.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
