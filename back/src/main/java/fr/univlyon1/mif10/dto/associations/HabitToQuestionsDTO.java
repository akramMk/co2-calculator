package fr.univlyon1.mif10.dto.associations;

import fr.univlyon1.mif10.dto.HabitDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.associations.ids.HabitQuestionId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "habit_to_questions")
public class HabitToQuestionsDTO {
    @EmbeddedId
    HabitQuestionId pk;
    public HabitToQuestionsDTO() {
        // default constructor
    }

    public HabitToQuestionsDTO(QuestionDTO question, HabitDTO habit) {
        pk = new HabitQuestionId(question, habit);
    }

    public HabitToQuestionsDTO(HabitQuestionId pk) {
        this.pk = pk;
    }

    public HabitQuestionId getPk() {
        return pk;
    }

    public void setPk(HabitQuestionId pk) {
        this.pk = pk;
    }

    public QuestionDTO getQuestion() {
        return pk.getIdQuestion();
    }

    public void setQuestion(QuestionDTO question) {
        pk.setIdQuestion(question);
    }

    public HabitDTO getHabit() {
        return pk.getIdHabit();
    }

    public void setHabit(HabitDTO habit) {
        pk.setIdHabit(habit);
    }
}
