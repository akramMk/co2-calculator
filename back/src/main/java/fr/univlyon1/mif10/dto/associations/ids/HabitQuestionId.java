package fr.univlyon1.mif10.dto.associations.ids;

import fr.univlyon1.mif10.dto.HabitDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;

public class HabitQuestionId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionDTO idQuestion;

    @ManyToOne
    @JoinColumn(name = "id_habit")
    private HabitDTO idHabit;

    public HabitQuestionId() {
        // default constructor
    }

    public HabitQuestionId(QuestionDTO idQuestion, HabitDTO idHabit) {
        this.idQuestion = idQuestion;
        this.idHabit = idHabit;
    }

    public QuestionDTO getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(QuestionDTO idQuestion) {
        this.idQuestion = idQuestion;
    }

    public HabitDTO getIdHabit() {
        return idHabit;
    }

    public void setIdHabit(HabitDTO idHabit) {
        this.idHabit = idHabit;
    }
}
