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
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WalletListAdapter {
    String mode;
    WalletListAdapter(Context context, ArrayList<Wallet_Data> model, String mode) {
        this.mode = mode;
        if (this.mode == "LIST_HORIZONTAL") {
            WalletHorizontalListAdapter walletHorizontalListAdapter = new WalletHorizontalListAdapter(context, model);
        } else if (this.mode == "LIST_VERTICAL") {
            WalletVerticalListAdapter walletVerticalListAdapter = new WalletVerticalListAdapter(context, model);
        }
    }
}

class WalletVerticalListAdapter extends ArrayAdapter<Wallet_Data> {
    WalletVerticalListAdapter(Context context, ArrayList<Wallet_Data> model) {
        super(context, R.layout.recycle_view_horizontal_wallet_item, model);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Wallet_Data walletData = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_transaction_item, parent, false);
        }

        TextView name = view.findViewById(R.id.wallet_name);
        ImageView icon = view.findViewById(R.id.wallet_icon);

        name.setText(walletData.name);
        icon.setImageResource(walletData.icon);

        return view;
    }
}

