package com.irlyreza.wallot;

public class Transaction_Data {
    String category = "", date = "", money = "";
    int icon = 0;

    Transaction_Data(String category, String money, String date, int icon) {
        this.category = category;
        this.money = money;
        this.date = date;
        this.icon = icon;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getMoney() {
        return money;
    }

    public int getIcon() {
        return icon;
    }
}
