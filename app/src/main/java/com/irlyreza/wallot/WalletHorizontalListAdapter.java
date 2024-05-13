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

class WalletHorizontalListAdapter extends RecyclerView.Adapter<WalletHorizontalListAdapter.MyHolder> {
    ArrayList<Wallet_Data> model;
    Context context;

    public WalletHorizontalListAdapter(Context context, ArrayList<Wallet_Data> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.recycle_view_horizontal_wallet_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(this.model.get(position).name);
        holder.icon.setImageResource(this.model.get(position).icon);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.wallet_name);
            icon = itemView.findViewById(R.id.wallet_icon);

        }
    }
}
