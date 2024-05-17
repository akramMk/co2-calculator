package fr.univlyon1.mif10.dto.associations.ids;

import fr.univlyon1.mif10.dto.AnswerDTO;

import fr.univlyon1.mif10.dto.QuestionDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;

public class QuestionToAnswersId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "id_question")
    private QuestionDTO idQuestion;

    @ManyToOne
    @JoinColumn(name = "id_answer")
    private AnswerDTO idAnswer;

    public QuestionToAnswersId() {
        // default constructor
    }

    public QuestionToAnswersId(QuestionDTO idQuestion, AnswerDTO idAnswer) {
        this.idQuestion = idQuestion;
        this.idAnswer = idAnswer;
    }

    public QuestionDTO getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(QuestionDTO idQuestion) {
        this.idQuestion = idQuestion;
    }

    public AnswerDTO getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(AnswerDTO idAnswer) {
        this.idAnswer = idAnswer;
    }
}
