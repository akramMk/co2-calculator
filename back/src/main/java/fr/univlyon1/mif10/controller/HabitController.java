package fr.univlyon1.mif10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.mif10.dto.HabitDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HabitController implements ControllerBase {
    private final HabitService habitService;
    private final HabitToQuestionsService habitToQuestionsService;
    private final ObjectMapper objectMapper;

    @Autowired
    public HabitController(HabitService habitService, HabitToQuestionsService habitToQuestionsService, ObjectMapper objectMapper) {
        this.habitService = habitService;
        this.habitToQuestionsService = habitToQuestionsService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(path = "/habits", produces = "application/json")
    public ResponseEntity<List<HabitDTO>> getAllHabits() {
        return ResponseEntity.ok(habitService.getHabitsList());
    }

    @GetMapping(path = "/habits/{id}", produces = "application/json")
    public ResponseEntity<HabitDTO> getHabitById(@PathVariable Long id) {
        return ResponseEntity.ok(habitService.getHabitById(id));
    }

    @GetMapping(path = "/habits/{id}/description", produces = "application/json")
    public ResponseEntity<String> getHabitDescriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(habitService.getHabitDescriptionById(id));
    }

    @GetMapping(path = "/habits/{id}/questions", produces = "application/json")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByHabitId(@PathVariable Long id) {
        return ResponseEntity.ok(habitToQuestionsService.getQuestionsByHabitId(id));
    }
}
