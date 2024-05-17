package fr.univlyon1.mif10.dto;

public class ConstraintDTO {
    Long questionId;
    Long answerId;

    public ConstraintDTO(Long questionId, Long answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public ConstraintDTO(InitialQuestionDTO initialQuestionDTO) {
        this.questionId = initialQuestionDTO.getId();
        this.answerId = initialQuestionDTO.getIdAnswer();
    }

    public Long getQuestionId() {
        return questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }
}
