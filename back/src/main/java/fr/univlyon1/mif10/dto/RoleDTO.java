package fr.univlyon1.mif10.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    public RoleDTO() { // default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idRole) {
        this.id = idRole;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleDTO that = (RoleDTO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
