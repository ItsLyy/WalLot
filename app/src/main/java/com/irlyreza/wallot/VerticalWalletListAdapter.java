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

public class VerticalWalletListAdapter extends ArrayAdapter<WalLot_Data.Wallet_Data> {
    VerticalWalletListAdapter(Context context, ArrayList<WalLot_Data.Wallet_Data> model) {
        super(context, R.layout.list_wallet_item, model);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        WalLot_Data.Wallet_Data walletData = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_wallet_item, parent, false);
        }

        TextView name = view.findViewById(R.id.name_wallet_item);
        TextView money = view.findViewById(R.id.money_wallet_item);
        ImageView icon = view.findViewById(R.id.icon_wallet_item);

        name.setText(walletData.name);
        money.setText(walletData.money);
        icon.setImageResource(walletData.icon);

        return view;
    }
}
