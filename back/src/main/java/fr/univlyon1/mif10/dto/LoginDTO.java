package fr.univlyon1.mif10.dto;

public class LoginDTO {
    private String login;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginDTO that = (LoginDTO) o;

        return login.equals(that.login) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return login.hashCode() + password.hashCode();
    }
}
