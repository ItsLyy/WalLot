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

public class DebtHorizontalListAdapter extends RecyclerView.Adapter<DebtHorizontalListAdapter.MyHolder> {
    ArrayList<WalLot_Data.Debt_Data> model;
    Context context;

    public DebtHorizontalListAdapter(Context context, ArrayList<WalLot_Data.Debt_Data> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.recycle_view_debt_wallet_item, parent, false);
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
        TextView name, money;
        ImageView icon;

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.debt_name);
            icon = itemView.findViewById(R.id.debt_icon);
            money = itemView.findViewById(R.id.debt_nominal);
        }
    }
}
