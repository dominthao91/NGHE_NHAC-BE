package com.example.meodihia_backend.dto.request;

public class ChangePassword {
    private String oldPassword;
    private String newPassword;
    private String re_newPassword;

    public ChangePassword() {
    }

    public ChangePassword(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.re_newPassword = this.newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRe_newPassword() {
        return re_newPassword;
    }

    public void setRe_newPassword(String re_newPassword) {
        this.re_newPassword = re_newPassword;
    }
}
