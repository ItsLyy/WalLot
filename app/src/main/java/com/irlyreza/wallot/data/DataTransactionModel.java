package com.irlyreza.wallot.data;

public class DataTransactionModel {
    String id_transaction, nominal, description, date, id_wallet, id_user, type;

    public DataTransactionModel() {}

    public DataTransactionModel(String nominal, String description, String date, String id_wallet, String id_user, String type) {
        this.nominal = nominal;
        this.description = description;
        this.date = date;
        this.id_wallet = id_wallet;
        this.id_user = id_user;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(String id_transaction) {
        this.id_transaction = id_transaction;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_wallet() {
        return id_wallet;
    }

    public void setId_wallet(String id_wallet) {
        this.id_wallet = id_wallet;
    }
}
