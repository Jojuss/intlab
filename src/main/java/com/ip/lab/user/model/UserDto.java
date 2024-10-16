package com.ip.lab.user.model;

public class UserDto {
    private long id;
    private String login;
    private UserRole role;
    private String password;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.role = user.getRole();
        this.password = user.getPassword();
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public UserDto() {
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
