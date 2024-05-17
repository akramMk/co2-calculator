package fr.univlyon1.mif10.service;

import fr.univlyon1.mif10.dto.HabitDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabitService {
    private final HabitRepository habitRepository;
    private final HabitToQuestionsService habitToQuestionsService;

    public HabitService(HabitRepository habitRepository , HabitToQuestionsService habitToQuestionsService) {
        this.habitRepository = habitRepository;
        this.habitToQuestionsService = habitToQuestionsService;
    }

    public List<HabitDTO> getHabitsList() {
        Iterable<HabitDTO> qr = habitRepository.findAll();
        List<HabitDTO> habits = new ArrayList<>();
        qr.forEach(habits::add);
        return habits;
    }

    public HabitDTO getHabitById(Long id) {
        return habitRepository.findById(id).orElse(null);
    }

    public String getHabitDescriptionById(Long id) {
        return habitRepository.findById(id).map(HabitDTO::getDescription).orElse(null);
    }

    public HabitDTO getHabitByQuestionId(Long id) {
        List<HabitDTO> habits = getHabitsList();
        for (HabitDTO habit : habits) {
            List<QuestionDTO> questions = habitToQuestionsService.getQuestionsByHabitId(habit.getId());
            for (QuestionDTO question : questions) {
                if (question.getId().equals(id)) {
                    return habit;
                }
            }
        }
        return null;
    }
}
