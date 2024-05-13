package com.irlyreza.wallot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WalletVerticallyListAdapter extends RecyclerView.Adapter<WalletVerticallyListAdapter.MyHolder> {
    ArrayList<WalLot_Data.Wallet_Data> model;
    Context context;

    public WalletVerticallyListAdapter(Context context, ArrayList<WalLot_Data.Wallet_Data> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.list_wallet_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(this.model.get(position).name);
        holder.money.setText(this.model.get(position).money);
        holder.icon.setImageResource(this.model.get(position).icon);
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
