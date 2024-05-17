package fr.univlyon1.mif10.service;

import fr.univlyon1.mif10.dto.HabitDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.associations.HabitToQuestionsDTO;
import fr.univlyon1.mif10.repository.QuestionRepository;
import fr.univlyon1.mif10.repository.HabitRepository;
import fr.univlyon1.mif10.repository.associations.HabitToQuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabitToQuestionsService {
    private final HabitToQuestionsRepository habitToQuestionsRepository;
    private final HabitRepository habitRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public HabitToQuestionsService(HabitToQuestionsRepository habitToQuestionsRepository, HabitRepository habitRepository, QuestionRepository questionRepository) {
        this.habitToQuestionsRepository = habitToQuestionsRepository;
        this.habitRepository = habitRepository;
        this.questionRepository = questionRepository;
    }

    public List<QuestionDTO> getQuestionsByHabitId(Long idHabit) {
        HabitDTO habit = habitRepository.findById(idHabit).orElseThrow(() -> new IllegalArgumentException("Habit not found"));
        List<HabitToQuestionsDTO> habitToQuestions = habitToQuestionsRepository.findByPkIdHabit(habit);
        List<QuestionDTO> questions = new ArrayList<>();
        for (HabitToQuestionsDTO hta : habitToQuestions) {
            questionRepository.findById(hta.getQuestion().getId()).ifPresent(questions::add);
        }
        return questions;
    }

    public List<HabitDTO> getHabitsByQuestionId(Long idQuestion) {
        QuestionDTO question = questionRepository.findById(idQuestion).orElseThrow(() -> new IllegalArgumentException("Question not found"));
        List<HabitToQuestionsDTO> habitToQuestions = habitToQuestionsRepository.findByPkIdQuestion(question);
        List<HabitDTO> habits = new ArrayList<>();
        for (HabitToQuestionsDTO hta : habitToQuestions) {
            habitRepository.findById(hta.getHabit().getId()).ifPresent(habits::add);
        }
        return habits;
    }
}
