package com.irlyreza.wallot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WalletVerticallyListAdapter extends RecyclerView.Adapter<WalletVerticallyListAdapter.MyHolder> {
    ArrayList<DataWalletModel> model;
    Context context;
    Context fragmentContext;

    public WalletVerticallyListAdapter(Context fragmentContext, Context context, ArrayList<DataWalletModel> model) {
        this.context = context;
        this.model = model;
        this.fragmentContext = fragmentContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.list_wallet_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(this.model.get(position).name);
        holder.money.setText(this.model.get(position).nominal);
        holder.icon.setImageResource(this.model.get(position).icon);
        holder.icon.setBackground(ContextCompat.getDrawable(context, this.model.get(position).getBgIcon()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragmentContext, WalletDetail.class);
                intent.putExtra("idWallet", model.get(position).id_wallet);
                intent.putExtra("name", model.get(position).name);
                intent.putExtra("nominal", model.get(position).nominal);
                intent.putExtra("icon", model.get(position).icon);
                intent.putExtra("bgIcon", model.get(position).bgIcon);

                fragmentContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;
        TextView money;

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_wallet_item);
            money = itemView.findViewById(R.id.money_wallet_item);
            icon = itemView.findViewById(R.id.icon_wallet_item);
        }
    }
}
