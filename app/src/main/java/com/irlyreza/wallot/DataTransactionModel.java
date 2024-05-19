package com.irlyreza.wallot;

public class DataTransactionModel {
    String id_transaction, nominal, description, date, id_wallet;

    public DataTransactionModel() {}

    public DataTransactionModel(String nominal, String description, String date, String id_wallet) {
        this.nominal = nominal;
        this.description = description;
        this.date = date;
        this.id_wallet = id_wallet;
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
