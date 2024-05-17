package fr.univlyon1.mif10.dto;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "habits")
public class HabitDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habit")
    private Long id;

    @Column(name = "description")
    private String description;


    public HabitDTO() { // default constructor
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HabitDTO that = (HabitDTO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
