package com.example.demo.dto.user;

import javax.validation.constraints.NotNull;

public class CreateUserDTO {
    @NotNull(message = "Введите логин")
    private String username;
    @NotNull(message = "Введите пароль")
    private String password;

    //region Getter and Setter
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
    //endregion
}
