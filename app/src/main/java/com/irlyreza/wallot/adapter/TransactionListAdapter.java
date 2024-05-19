package com.irlyreza.wallot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.irlyreza.wallot.data.DataTransactionModel;
import com.irlyreza.wallot.R;

import java.util.ArrayList;

public class TransactionListAdapter extends ArrayAdapter<DataTransactionModel> {
    public TransactionListAdapter(Context context, ArrayList<DataTransactionModel> model) {
        super(context, R.layout.list_transaction_item, model);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Nullable
    @Override
    public DataTransactionModel getItem(int position) {
        return super.getItem(getCount() - position - 1);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        DataTransactionModel transactionData = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_transaction_item, parent, false);
        }

        TextView category = view.findViewById(R.id.category_transaction_item);
        TextView date = view.findViewById(R.id.date_transaction_item);
        TextView money = view.findViewById(R.id.money_transaction_item);

        category.setText(transactionData.getDescription());
        date.setText(transactionData.getDate());
        money.setText(transactionData.getNominal());

        return view;
    }
}
