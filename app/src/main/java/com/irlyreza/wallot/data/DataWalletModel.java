package com.irlyreza.wallot.data;

public class DataWalletModel {
    String id_wallet, name, nominal;
    int icon, bgIcon;

    DataWalletModel() {}

    public DataWalletModel(String name, String nominal, int icon, int bgIcon) {
        this.name = name;
        this.nominal = nominal;
        this.icon = icon;
        this.bgIcon = bgIcon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getBgIcon() {
        return bgIcon;
    }

    public void setBgIcon(int bgIcon) {
        this.bgIcon = bgIcon;
    }

    public String getId_wallet() {
        return id_wallet;
    }

    public void setId_wallet(String id_wallet) {
        this.id_wallet = id_wallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }
}
