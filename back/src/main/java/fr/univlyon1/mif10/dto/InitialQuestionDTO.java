package fr.univlyon1.mif10.dto;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name ="initiales_questions")
@PrimaryKeyJoinColumn(name = "id_question")
public class InitialQuestionDTO extends QuestionDTO{
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "questions_launches_habit",
            joinColumns = {@JoinColumn(name = "id_question")},
            inverseJoinColumns = @JoinColumn(name = "id_habit")
    )
    private Set<HabitDTO> launches = new HashSet<>();

    public InitialQuestionDTO() {
    }


    @Column(name = "id_answer")
    private Long idAnswer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InitialQuestionDTO that = (InitialQuestionDTO) o;
        return Objects.equals(launches, that.launches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), launches);
    }

    public InitialQuestionDTO(Set<HabitDTO> launches) {
        this.launches = launches;
    }

    public Set<HabitDTO> getLaunches() {
        return launches;
    }

    public void setLaunches(Set<HabitDTO> launches) {
        this.launches = launches;
    }

    public Long getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(Long idAnswer) {
        this.idAnswer = idAnswer;
    }
}
