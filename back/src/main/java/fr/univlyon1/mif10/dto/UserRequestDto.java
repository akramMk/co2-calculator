package fr.univlyon1.mif10.dto;

import fr.univlyon1.mif10.classes.RoleEnum;

public class UserRequestDto {

    private String login;

    private String password;

    private RoleEnum roleEnum;

    public UserRequestDto(Long id, String login, String password, RoleEnum roleEnum) {
        this.login = login;
        this.password = password;
        this.roleEnum = roleEnum;
    }

    public UserRequestDto() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public RoleEnum getRole() {
        return roleEnum;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }
}
