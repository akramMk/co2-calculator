package fr.univlyon1.mif10.dto;

import java.util.List;

public class QuestionResponseDTO {
    QuestionDTO question;
    List<AnswerDTO> answers;

    ConstraintDTO constraint;

    public QuestionResponseDTO(QuestionDTO question, List<AnswerDTO> answers, ConstraintDTO constraint) {
        this.question = question;
        this.answers = answers;
        this.constraint = constraint;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public ConstraintDTO getConstraint() {
        return constraint;
    }
}
