package fr.univlyon1.mif10.dto;

import fr.univlyon1.mif10.classes.RoleEnum;

public class UserResponseDto {

    private String login;

    private RoleEnum roleEnum;

    public UserResponseDto(String login,RoleEnum roleEnum) {
        this.login = login;
        this.roleEnum = roleEnum;
    }

    public UserResponseDto(UserDTO userDTO){
        this.login = userDTO.getLogin();
        this.roleEnum = userDTO.getRole();
    }

    public UserResponseDto() {
    }


    public String getLogin() {
        return login;
    }

    public RoleEnum getRole() {
        return roleEnum;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setRole(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }
}
