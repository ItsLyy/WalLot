package com.irlyreza.wallot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TransactionListAdapter extends ArrayAdapter<Transaction_Data> {
    TransactionListAdapter(Context context, ArrayList<Transaction_Data> model) {
        super(context, R.layout.list_transaction_item, model);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Transaction_Data transactionData = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_transaction_item, parent, false);
        }

        TextView category = view.findViewById(R.id.category_transaction_item);
        TextView date = view.findViewById(R.id.date_transaction_item);
        TextView money = view.findViewById(R.id.money_transaction_item);
        ImageView icon = view.findViewById(R.id.icon_transaction_item);

        category.setText(transactionData.category);
        date.setText(transactionData.date);
        money.setText(transactionData.money);
        icon.setImageResource(transactionData.icon);

        return view;
    }
}
