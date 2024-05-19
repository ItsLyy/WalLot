package com.irlyreza.wallot.data;

public class DataUserModel {
    String idUser, nameUser, passwordUser, phoneNumberUser, gmailUser;
    int profileImage;

    public DataUserModel() {}
    public DataUserModel(String nameUser, String passwordUser, String phoneNumberUser, String gmailUser, int profileImage) {
        this.nameUser = nameUser;
        this.passwordUser = passwordUser;
        this.phoneNumberUser = phoneNumberUser;
        this.gmailUser = gmailUser;
        this.profileImage = profileImage;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getPhoneNumberUser() {
        return phoneNumberUser;
    }

    public void setPhoneNumberUser(String phoneNumberUser) {
        this.phoneNumberUser = phoneNumberUser;
    }

    public String getGmailUser() {
        return gmailUser;
    }

    public void setGmailUser(String gmailUser) {
        this.gmailUser = gmailUser;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }
}
