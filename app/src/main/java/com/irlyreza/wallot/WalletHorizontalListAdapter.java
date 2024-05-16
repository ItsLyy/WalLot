package com.irlyreza.wallot;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WalletHorizontalListAdapter extends RecyclerView.Adapter<WalletHorizontalListAdapter.MyHolder> {
    ArrayList<WalLot_Data.Wallet_Data> model;
    Context context, fragmentContext;

    public WalletHorizontalListAdapter(Context fragmentContext, Context context, ArrayList<WalLot_Data.Wallet_Data> model) {
        this.context = context;
        this.model = model;
        this.fragmentContext = fragmentContext;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragmentContext, WalletDetail.class);
                intent.putExtra("name", model.get(position).name);
                intent.putExtra("money", model.get(position).money);
                intent.putExtra("date", model.get(position).date);
                intent.putExtra("icon", model.get(position).icon);

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

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.wallet_name);
            icon = itemView.findViewById(R.id.wallet_icon);

        }
    }
}
