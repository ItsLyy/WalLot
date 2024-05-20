package com.irlyreza.wallot.data;

public class RoleListData {
    String role;
    Boolean isVisible;

    public RoleListData(String role, Boolean isVisible) {
        this.role = role;
        this.isVisible = isVisible;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }
}
