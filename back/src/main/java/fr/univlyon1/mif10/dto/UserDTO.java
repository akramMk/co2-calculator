package fr.univlyon1.mif10.dto;

import fr.univlyon1.mif10.classes.RoleEnum;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "id_role")
    private RoleEnum roleEnum;

    @Column(name = "connected")
    private boolean connected = false;

    public UserDTO(Long id, String login, String password, RoleEnum roleEnum) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roleEnum = roleEnum;
    }

    public UserDTO(String login, String password, RoleEnum roleEnum) {
        this.login = login;
        this.password = password;
        this.roleEnum = roleEnum;
    }

    public UserDTO() {

    }

    public Long getId() {
        return id;
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

    /**
     * Vérifie un password par rapport à celui stocké dans l'instance.
     * @param password Le password à vérifier
     * @return un booléen indiquant le succès de la comparaison des 2 passwords
     */
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    public void disconnect() {
        this.connected = false;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public boolean authenticate(String password) {
        if(!verifyPassword(password)) {
            return false;
        }
        this.connected = true;
        return true;
    }
    /*
    public UserDTO orElseThrow(Object userDTONotFound) {
        return null;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        return id.equals(userDTO.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
