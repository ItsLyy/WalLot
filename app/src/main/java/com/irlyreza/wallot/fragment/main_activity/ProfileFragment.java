package com.irlyreza.wallot.fragment.main_activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irlyreza.wallot.PreferenceUtils;
import com.irlyreza.wallot.R;
import com.irlyreza.wallot.activity.Login;
import com.irlyreza.wallot.activity.WalletDetail;
import com.irlyreza.wallot.data.DataUserModel;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button editProfileName, changePassword, changeEmail, changePhoneNumber, logout;
    TextView usernameLabel, emailLabel, phoneNumberLabel, idUserLabel;

    private String idUser;
    ArrayList<DataUserModel> dataUserModels;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editProfileName = view.findViewById(R.id.btn_changeusername);
        changePassword = view.findViewById(R.id.btn_changePassword);
        changeEmail = view.findViewById(R.id.btn_changeEmail);
        changePhoneNumber = view.findViewById(R.id.btn_changePhonenumb);
        logout = view.findViewById(R.id.logout_btn);

        usernameLabel = view.findViewById(R.id.tx_username);
        emailLabel = view.findViewById(R.id.tx_email);
        phoneNumberLabel = view.findViewById(R.id.tx_phonenumb);
        idUserLabel = view.findViewById(R.id.tx_idUser);

        SharedPreferences preferences = getActivity().getSharedPreferences("LOGINAPP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        idUser = preferences.getString("idUser", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference("users");

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataUserModels = new ArrayList<>();
                for (DataSnapshot itemUser : snapshot.getChildren()) {
                    if (Objects.equals(itemUser.getKey(), idUser)) {
                        dataUserModels.add(new DataUserModel(itemUser.child("nameUser").getValue(String.class), itemUser.child("passwordUser").getValue(String.class), itemUser.child("phoneNumberUser").getValue(String.class), itemUser.child("gmailUser").getValue(String.class),itemUser.child("profileImage").getValue(Integer.class)));
                        dataUserModels.get(0).setIdUser(itemUser.getKey());
                        DataUserModel userModel = dataUserModels.get(0);
                        usernameLabel.setText(userModel.getNameUser());
                        emailLabel.setText(userModel.getGmailUser());
                        phoneNumberLabel.setText(userModel.getPhoneNumberUser());
                        idUserLabel.setText(userModel.getIdUser());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.bg_corner_gradient_blue));
        dialog.setCancelable(false);

        Button acceptBtn, rejectBtn;
        TextView taskText;

        acceptBtn = dialog.findViewById(R.id.accept_btn);
        rejectBtn = dialog.findViewById(R.id.reject_btn);

        taskText = dialog.findViewById(R.id.task_text);
        taskText.setText("Are you sure want to logout?");

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Login.class));
                editor.clear().commit();
                dialog.dismiss();
                getActivity().finish();
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        editProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View popupSelector = inflater.inflate(R.layout.activity_change_username_overlay, null);

                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                PopupWindow popupWindow = new PopupWindow(popupSelector, width, height, focusable);

                TextView changeBtn = popupSelector.findViewById(R.id.change_btn);
                EditText changeInput = popupSelector.findViewById(R.id.inputChange);

                changeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userReference.child(idUser).child("nameUser").setValue(changeInput.getText().toString());
                        popupWindow.dismiss();
                        Toast.makeText(getActivity(), "Change Successful", Toast.LENGTH_SHORT).show();
                    }
                });

                view.findViewById(R.id.main).post(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.showAtLocation(view.findViewById(R.id.main), Gravity.BOTTOM, 0, 0);
                    }
                });
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View popupSelector = inflater.inflate(R.layout.activity_change_password_overlay, null);

                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                PopupWindow popupWindow = new PopupWindow(popupSelector, width, height, focusable);

                TextView changeBtn = popupSelector.findViewById(R.id.change_btn);
                EditText oldInput = popupSelector.findViewById(R.id.oldInput);
                EditText changeInput = popupSelector.findViewById(R.id.changeInput);
                EditText confirmInput = popupSelector.findViewById(R.id.confirmInput);

                changeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child(idUser).child("passwordUser").getValue(String.class).equals(oldInput.getText().toString()) &&
                                    changeInput.getText().toString().equals(confirmInput.getText().toString()) &&
                                    changeInput.getText().toString().length() >= 8 &&
                                    changeInput.getText().toString().length() <= 12) {
                                    userReference.child(idUser).child("passwordUser").setValue(changeInput.getText().toString());
                                    popupWindow.dismiss();
                                    Toast.makeText(getActivity(), "Change Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Change Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

                view.findViewById(R.id.main).post(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.showAtLocation(view.findViewById(R.id.main), Gravity.BOTTOM, 0, 0);
                    }
                });
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View popupSelector = inflater.inflate(R.layout.activity_change_email_overlay, null);

                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                PopupWindow popupWindow = new PopupWindow(popupSelector, width, height, focusable);

                TextView changeBtn = popupSelector.findViewById(R.id.change_btn);
                EditText changeInput = popupSelector.findViewById(R.id.changeInput);
                EditText confirmInput = popupSelector.findViewById(R.id.inputConfirm);

                changeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (changeInput.getText().toString().equals(confirmInput.getText().toString())) {
                                    userReference.child(idUser).child("gmailUser").setValue(changeInput.getText().toString());
                                    popupWindow.dismiss();
                                    Toast.makeText(getActivity(), "Change Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Change Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

                view.findViewById(R.id.main).post(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.showAtLocation(view.findViewById(R.id.main), Gravity.BOTTOM, 0, 0);
                    }
                });
            }
        });

        changePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getApplicationContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View popupSelector = inflater.inflate(R.layout.activity_change_phone_number_overlay, null);

                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                PopupWindow popupWindow = new PopupWindow(popupSelector, width, height, focusable);

                TextView changeBtn = popupSelector.findViewById(R.id.change_btn);
                EditText changeInput = popupSelector.findViewById(R.id.inputChange);
                EditText confirmInput = popupSelector.findViewById(R.id.inputConfirm);

                changeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        userReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (changeInput.getText().toString().equals(confirmInput.getText().toString())) {
                                    userReference.child(idUser).child("phoneNumberUser").setValue(changeInput.getText().toString());
                                    popupWindow.dismiss();
                                    Toast.makeText(getActivity(), "Change Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Change Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

                view.findViewById(R.id.main).post(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.showAtLocation(view.findViewById(R.id.main), Gravity.BOTTOM, 0, 0);
                    }
                });
            }
        });

        return view;
    }



}


