package com.irlyreza.wallot;

import android.annotation.SuppressLint;
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

public class DebtHorizontalListAdapter extends RecyclerView.Adapter<DebtHorizontalListAdapter.MyHolder> {
    ArrayList<DataDebtModel> model;
    Context context, fragmentContext;

    public DebtHorizontalListAdapter(Context fragmentContext, Context context, ArrayList<DataDebtModel> model) {
        this.context = context;
        this.model = model;
        this.fragmentContext = fragmentContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.recycle_view_debt_wallet_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        DataDebtModel dataDebtModel = this.model.get(position);
        holder.namePerson.setText(dataDebtModel.getNamePerson());
        holder.nominal.setText(dataDebtModel.getNominal());
        holder.phone_number.setText(dataDebtModel.getPhoneNumber());
        holder.description.setText(dataDebtModel.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragmentContext, EditDebtMenu.class);
//                intent.putExtra("name", model.get(position).name);
//                intent.putExtra("money", model.get(position).money);
//                intent.putExtra("date", model.get(position).date);

                fragmentContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView namePerson, nominal, description, phone_number;
        ImageView icon;

        public MyHolder(View itemView) {
            super(itemView);
            namePerson = itemView.findViewById(R.id.debt_name_person);
            icon = itemView.findViewById(R.id.debt_icon);
            nominal = itemView.findViewById(R.id.debt_nominal);
            description = itemView.findViewById(R.id.debt_description);
            phone_number = itemView.findViewById(R.id.debt_number_phone);
        }
    }
}
