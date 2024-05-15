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

import java.util.ArrayList;

public class WalletSpinnerAdapter extends ArrayAdapter<WalLot_Data.Wallet_Data> {
    LayoutInflater layoutInflater;
    WalletSpinnerAdapter(Context context, ArrayList<WalLot_Data.Wallet_Data> model) {
        super(context, R.layout.spinner_wallet_item, model);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_wallet_item, null, true);
        WalLot_Data.Wallet_Data walletData = getItem(position);

        TextView category = view.findViewById(R.id.spinner_wallet_name);
        ImageView icon = view.findViewById(R.id.spinner_wallet_icon);

        category.setText(walletData.name);
        icon.setImageResource(walletData.icon);

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WalLot_Data.Wallet_Data walletData = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_wallet_item, null, false);
        }

        TextView category = convertView.findViewById(R.id.spinner_wallet_name);
        ImageView icon = convertView.findViewById(R.id.spinner_wallet_icon);

        category.setText(walletData.name);
        icon.setImageResource(walletData.icon);

        return convertView;
    }
}
