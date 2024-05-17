package fr.univlyon1.mif10.dto.associations;
import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.associations.ids.QuestionToAnswersId;
import jakarta.persistence.*;

@Entity
@Table(name = "question_to_answers")
public class QuestionToAnswersDTO {
    @EmbeddedId
    QuestionToAnswersId pk;

    public QuestionToAnswersDTO() {
        // default constructor
    }

    public QuestionToAnswersDTO(QuestionDTO question, AnswerDTO answer) {
        pk = new QuestionToAnswersId(question, answer);
    }

    public QuestionToAnswersDTO(QuestionToAnswersId pk) {
        this.pk = pk;
    }

    public QuestionToAnswersId getPk() {
        return pk;
    }

    public void setPk(QuestionToAnswersId pk) {
        this.pk = pk;
    }

    public QuestionDTO getQuestion() {
        return pk.getIdQuestion();
    }

    public void setQuestion(QuestionDTO question) {
        pk.setIdQuestion(question);
    }

    public AnswerDTO getAnswer() {
        return pk.getIdAnswer();
    }

    public void setAnswer(AnswerDTO answer) {
        pk.setIdAnswer(answer);
    }
}
