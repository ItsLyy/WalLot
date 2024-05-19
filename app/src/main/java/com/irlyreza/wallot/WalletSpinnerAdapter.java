package com.irlyreza.wallot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WalletSpinnerAdapter extends ArrayAdapter<DataWalletModel> {
    LayoutInflater layoutInflater;
    WalletSpinnerAdapter(Context context, ArrayList<DataWalletModel> model) {
        super(context, R.layout.spinner_wallet_item, model);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_wallet_item, null, true);
        DataWalletModel walletData = getItem(position);

        TextView category = view.findViewById(R.id.spinner_wallet_name);
        ImageView icon = view.findViewById(R.id.spinner_wallet_icon);

        category.setText(walletData.name);
        icon.setImageResource(walletData.icon);
        icon.setBackground(ContextCompat.getDrawable(getContext(), walletData.getBgIcon()));

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataWalletModel walletData = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_wallet_item, null, false);
        }

        TextView category = convertView.findViewById(R.id.spinner_wallet_name);
        ImageView icon = convertView.findViewById(R.id.spinner_wallet_icon);

        category.setText(walletData.name);
        icon.setImageResource(walletData.icon);
        icon.setBackground(ContextCompat.getDrawable(getContext(), walletData.getBgIcon()));

        return convertView;
    }
}
