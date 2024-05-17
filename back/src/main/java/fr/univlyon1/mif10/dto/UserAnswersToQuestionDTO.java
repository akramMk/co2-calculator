package fr.univlyon1.mif10.dto;

import java.util.List;

public class UserAnswersToQuestionDTO {
    private String login;
    private List<Integer> idAnswers;

    public UserAnswersToQuestionDTO() {
    }

    public UserAnswersToQuestionDTO(List<Integer> idAnswers) {
        this.idAnswers = idAnswers;
    }

    public List<Integer> getIdAnswers() {
        return idAnswers;
    }

    public void setIdAnswers(List<Integer> idAnswers) {
        this.idAnswers = idAnswers;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


}
