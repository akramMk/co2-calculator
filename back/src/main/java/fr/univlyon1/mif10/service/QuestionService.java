package fr.univlyon1.mif10.service;

import fr.univlyon1.mif10.dto.HabitDTO;
import fr.univlyon1.mif10.dto.InitialQuestionDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.repository.HabitRepository;
import fr.univlyon1.mif10.repository.InitialQuestionRepository;
import fr.univlyon1.mif10.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final InitialQuestionService initialQuestionService;
    private final HabitService habitService;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, InitialQuestionService initialQuestionService, HabitService habitService) {
        this.questionRepository = questionRepository;
        this.initialQuestionService = initialQuestionService;
        this.habitService = habitService;
    }

    public QuestionDTO getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public String getQuestionDescriptionById(Long id) {
        return questionRepository.findById(id).map(QuestionDTO::getDescription).orElse(null);
    }

    public List<Integer> getQuestionsList(){
        Iterable<QuestionDTO> qr = questionRepository.findAll();
        List<QuestionDTO> questions = new ArrayList<>();

        // add id of each question to the list
        qr.forEach(questions::add);
        List<Integer> questionsId = new ArrayList<>();
        for (QuestionDTO q : questions) {
            questionsId.add(Math.toIntExact(q.getId()));
        }

        return questionsId;
    }

    public InitialQuestionDTO getConstraint(Long id) {
        // finding the habit containg the question from that id
        HabitDTO habit = habitService.getHabitByQuestionId(id);
        if (habit == null) {
            return null;
        }

        // find the initial question that launches the habit
        List<InitialQuestionDTO> initialQuestions = initialQuestionService.findAll();
        for (InitialQuestionDTO iq : initialQuestions) {
            for (HabitDTO habitLaunched : iq.getLaunches()) {
                if (habitLaunched.getId().equals(habit.getId())) {
                    return iq;
                }
            }
        }

        return null;
    }
}
