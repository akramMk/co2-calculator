package fr.univlyon1.mif10.service;

import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.repository.AnswerRepository;
import fr.univlyon1.mif10.repository.associations.QuestionToAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionToAnswersRepository questionToAnswersRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionToAnswersRepository questionToAnswersRepository) {
        this.answerRepository = answerRepository;
        this.questionToAnswersRepository = questionToAnswersRepository;
    }

    public List<AnswerDTO> getAnswersList() {
        Iterable<AnswerDTO> qr = answerRepository.findAll();
        List<AnswerDTO> answers = new ArrayList<>();
        qr.forEach(answers::add);
        return answers;
    }

    public AnswerDTO getAnswerById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    public String getAnswerDescriptionById(Long id) {
        return answerRepository.findById(id).map(AnswerDTO::getDescription).orElse(null);
    }
}
