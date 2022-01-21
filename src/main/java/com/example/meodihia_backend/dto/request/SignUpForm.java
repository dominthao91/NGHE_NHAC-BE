package com.example.meodihia_backend.dto.request;

import java.util.Set;

public class SignUpForm {
    private String username;
    private String password;
    private String re_password;
    private String phoneNumber;
    private String avatar;
    private Set<String> roles;

    public SignUpForm() {
    }

    public SignUpForm(String username, String password, String phoneNumber, String avatar, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.re_password = this.password;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
