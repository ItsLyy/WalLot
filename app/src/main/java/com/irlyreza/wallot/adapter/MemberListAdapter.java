package com.irlyreza.wallot.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Objects;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MyHolder> {
    ArrayList<DataUserWalletModel> model;
    ArrayList<String> roleList = new ArrayList<>();
    Context context, fragmentContext;

    public MemberListAdapter(Context fragmentContext, Context context, ArrayList<DataUserWalletModel> model) {
        this.context = context;
        this.model = model;
        this.fragmentContext = fragmentContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.recycle_view_member_wallet_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        roleList.add("Member");
        roleList.add("Moderator");
        roleList.add("Admin");

        RoleSpinnerAdapter roleSpinnerAdapter = new RoleSpinnerAdapter(this.context, roleList);

        holder.role.setAdapter(roleSpinnerAdapter);

        DataUserWalletModel dataUserWalletModel = this.model.get(position);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReferencer = database.getReference("users");

        userReferencer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.name.setText(snapshot.child(dataUserWalletModel.getId_user()).child("nameUser").getValue(String.class));
                holder.icon.setImageResource(snapshot.child(dataUserWalletModel.getId_user()).child("profileImage").getValue(Integer.class));
                if (dataUserWalletModel.getRole().equals("member")) {
                    holder.role.setVisibility(View.INVISIBLE);
                    holder.kickBtn.setVisibility(View.INVISIBLE);
                } else if (dataUserWalletModel.getRole().equals("moderator")) {
                    holder.role.setVisibility(View.VISIBLE);
                    holder.kickBtn.setVisibility(View.VISIBLE);
                    holder.role.setSelection(1);
                } else if (dataUserWalletModel.getRole().equals("admin")) {
                    holder.kickBtn.setVisibility(View.VISIBLE);
                    holder.role.setVisibility(View.VISIBLE);
                    holder.role.setSelection(2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        }
    }
}
