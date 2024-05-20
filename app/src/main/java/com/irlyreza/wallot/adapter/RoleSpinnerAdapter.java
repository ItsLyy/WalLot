package com.irlyreza.wallot.adapter;

import android.annotation.SuppressLint;
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

import com.irlyreza.wallot.R;
import com.irlyreza.wallot.data.DataWalletModel;
import com.irlyreza.wallot.data.RoleListData;

import java.util.ArrayList;
import java.util.Objects;

public class RoleSpinnerAdapter extends ArrayAdapter<RoleListData> {
    LayoutInflater layoutInflater;
    public RoleSpinnerAdapter(Context context, ArrayList<RoleListData> model) {
        super(context, R.layout.spinner_role_item, model);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.spinner_role_item, null, true);
        TextView category = view.findViewById(R.id.spinner_wallet_name);
        view.setBackground(ContextCompat.getDrawable(getContext(), R.color.transparent));

        category.setText(Objects.requireNonNull(getItem(position)).getRole());

        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_role_item, null, false);
        }
        TextView category = convertView.findViewById(R.id.spinner_wallet_name);

        if(Objects.requireNonNull(getItem(position)).getVisible()) {
            convertView.setVisibility(View.VISIBLE);
        } else {
            convertView.setVisibility(View.INVISIBLE);
            convertView.setEnabled(false);
        }

        category.setText(Objects.requireNonNull(getItem(position)).getRole());
        return convertView;
    }
}
