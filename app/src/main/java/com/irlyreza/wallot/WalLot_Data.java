package com.irlyreza.wallot;

public class WalLot_Data {
    public static class Transaction_Data {
        String category = "", date = "", money = "";
        int icon = 0;

        Transaction_Data(String category, String money, String date, int icon) {
            this.category = category;
            this.money = money;
            this.date = date;
            this.icon = icon;
        }
    }

    public static class Wallet_Data {
        String name = "", date = "", money = "";
        int icon = 0;

        Wallet_Data(String name, String money, String date, int icon) {
            this.name = name;
            this.money = money;
            this.date = date;
            this.icon = icon;
        }
    }

    public static  class Debt_Data {
        String name = "", date = "", money = "";
        int icon = 0;

        Debt_Data(String name, String money, String date, int icon) {
            this.name = name;
            this.money = money;
            this.date = date;
            this.icon = icon;
        }
    }
}
