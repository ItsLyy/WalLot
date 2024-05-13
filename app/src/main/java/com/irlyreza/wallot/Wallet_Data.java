package com.irlyreza.wallot;

public class Wallet_Data {
    String name = "", date = "", money = "";
    int icon = 0;

    Wallet_Data(String name, String money, String date, int icon) {
        this.name = name;
        this.money = money;
        this.date = date;
        this.icon = icon;
    }
}
