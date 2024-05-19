package com.irlyreza.wallot.data;

public class DataUserWalletModel {
    String id_user_wallet, id_user, id_wallet, role;

    DataUserWalletModel() {}

    public DataUserWalletModel(String id_user, String id_wallet, String role) {
        this.id_user = id_user;
        this.id_wallet = id_wallet;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId_user_wallet() {
        return id_user_wallet;
    }

    public void setId_user_wallet(String id_user_wallet) {
        this.id_user_wallet = id_user_wallet;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_wallet() {
        return id_wallet;
    }

    public void setId_wallet(String id_wallet) {
        this.id_wallet = id_wallet;
    }
}
