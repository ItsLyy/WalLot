package com.irlyreza.wallot.data;

public class DataDebtModel {
    String id_debt, namePerson, nominal, description, phoneNumber, date, id_wallet, id_user, type;

    DataDebtModel() {}

    public DataDebtModel(String id_debt, String name_person, String nominal, String description, String phoneNumber, String date, String id_wallet, String id_user, String type) {
        this.id_debt = id_debt;
        this.namePerson = name_person;
        this.nominal = nominal;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.id_wallet = id_wallet;
        this.id_user = id_user;
        this.type = type;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId_debt() {
        return id_debt;
    }

    public void setId_debt(String id_debt) {
        this.id_debt = id_debt;
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

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
