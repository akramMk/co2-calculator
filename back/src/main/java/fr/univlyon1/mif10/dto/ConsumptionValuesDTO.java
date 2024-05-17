package fr.univlyon1.mif10.dto;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "consumption_values")
public class ConsumptionValuesDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_value")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private double value;

    public ConsumptionValuesDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConsumptionValuesDTO that = (ConsumptionValuesDTO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}