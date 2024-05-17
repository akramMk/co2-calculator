package fr.univlyon1.mif10.service;

import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.associations.QuestionToAnswersDTO;
import fr.univlyon1.mif10.repository.AnswerRepository;
import fr.univlyon1.mif10.repository.QuestionRepository;
import fr.univlyon1.mif10.repository.associations.QuestionToAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionToAnswersService {
    private final QuestionToAnswersRepository questionToAnswersRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionToAnswersService(QuestionToAnswersRepository questionToAnswersRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionToAnswersRepository = questionToAnswersRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public List<AnswerDTO> getAnswersByQuestionId(Long idQuestion) {
        QuestionDTO question = questionRepository.findById(idQuestion).orElseThrow(() -> new IllegalArgumentException("Question not found"));
        List<QuestionToAnswersDTO> questionToAnswers = questionToAnswersRepository.findByPkIdQuestion(question);
        List<AnswerDTO> answers = new ArrayList<>();
        for (QuestionToAnswersDTO qta : questionToAnswers) {
            answerRepository.findById(qta.getAnswer().getId()).ifPresent(answers::add);
        }
        return answers;
    }

    public boolean questionHasAnswer(Long idQuestion, Long idAnswer) {
        QuestionDTO question = questionRepository.findById(idQuestion).orElseThrow(() -> new IllegalArgumentException("Question not found"));
        AnswerDTO answer = answerRepository.findById(idAnswer).orElseThrow(() -> new IllegalArgumentException("Answer not found"));
        return questionToAnswersRepository.findByPkIdQuestionAndPkIdAnswer(question, answer).isPresent();
    }
}
