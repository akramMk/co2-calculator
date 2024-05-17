package fr.univlyon1.mif10.dto.associations.ids;

import fr.univlyon1.mif10.dto.AnswerDTO;

import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.UserDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;

public class UserToAnswersToQuestionId implements Serializable{
@Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserDTO idUser;

    @ManyToOne
    @JoinColumn(name = "id_answer")
    private AnswerDTO idAnswer;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionDTO idQuestion;

    public UserToAnswersToQuestionId() {
        // default constructor
    }

    public UserToAnswersToQuestionId(UserDTO idUser, AnswerDTO idAnswer, QuestionDTO idQuestion) {
        this.idUser = idUser;
        this.idAnswer = idAnswer;
        this.idQuestion = idQuestion;
    }

    public UserDTO getIdUser() {
        return idUser;
    }

    public void setIdUser(UserDTO idUser) {
        this.idUser = idUser;
    }

    public AnswerDTO getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(AnswerDTO idAnswer) {
        this.idAnswer = idAnswer;
    }

    public QuestionDTO getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(QuestionDTO idQuestion) {
        this.idQuestion = idQuestion;
    }
}
