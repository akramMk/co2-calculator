package fr.univlyon1.mif10.dto.associations;
import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.UserDTO;
import fr.univlyon1.mif10.dto.associations.ids.UserToAnswersToQuestionId;
import jakarta.persistence.*;

@Entity
@Table(name = "user_to_answers_to_question")
public class UserToAnswersToQuestionDTO {
    @EmbeddedId
    UserToAnswersToQuestionId pk;

    public UserToAnswersToQuestionDTO() {
        // default constructor
    }

    public UserToAnswersToQuestionDTO(UserDTO user, AnswerDTO answer, QuestionDTO question) {
        pk = new UserToAnswersToQuestionId(user, answer, question);
    }

    public UserToAnswersToQuestionDTO(UserToAnswersToQuestionId pk) {
        this.pk = pk;
    }

    public UserToAnswersToQuestionId getPk() {
        return pk;
    }

    public void setPk(UserToAnswersToQuestionId pk) {
        this.pk = pk;
    }

    public UserDTO getUser() {
        return pk.getIdUser();
    }

    public void setUser(UserDTO user) {
        pk.setIdUser(user);
    }

    public AnswerDTO getAnswer() {
        return pk.getIdAnswer();
    }

    public void setAnswer(AnswerDTO answer) {
        pk.setIdAnswer(answer);
    }

    public QuestionDTO getQuestion() {
        return pk.getIdQuestion();
    }

    public void setQuestion(QuestionDTO question) {
        pk.setIdQuestion(question);
    }
}
