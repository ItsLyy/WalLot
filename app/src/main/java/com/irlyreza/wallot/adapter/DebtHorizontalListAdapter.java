package com.irlyreza.wallot.adapter;

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

import com.irlyreza.wallot.data.DataDebtModel;
import com.irlyreza.wallot.activity.EditDebtMenu;
import com.irlyreza.wallot.R;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragmentContext, EditDebtMenu.class);
                intent.putExtra("idDebt", model.get(position).getId_debt());
                intent.putExtra("nominal", model.get(position).getNominal());
                intent.putExtra("name", model.get(position).getNamePerson());
                intent.putExtra("phoneNumber", model.get(position).getPhoneNumber());
                intent.putExtra("description", model.get(position).getDescription());
                intent.putExtra("idWallet", model.get(position).getId_wallet());
                intent.putExtra("date", model.get(position).getDate());
                intent.putExtra("type", model.get(position).getType());

                fragmentContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView namePerson, nominal, phone_number;

        public MyHolder(View itemView) {
            super(itemView);
            namePerson = itemView.findViewById(R.id.debt_name_person);
            nominal = itemView.findViewById(R.id.debt_nominal);
            phone_number = itemView.findViewById(R.id.debt_number_phone);
        }
    }
}
