package fr.univlyon1.mif10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnswerController implements ControllerBase {
    private final AnswerService answerService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AnswerController(AnswerService answerService, ObjectMapper objectMapper) {
        this.answerService = answerService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(path = "/answers", produces = "application/json")
    public ResponseEntity<List<AnswerDTO>> getAllAnswers() {
        return ResponseEntity.ok(answerService.getAnswersList());
    }

    @GetMapping(path = "/answers/{id}", produces = "application/json")
    public ResponseEntity<AnswerDTO> getAnswerById(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.getAnswerById(id));
    }

    @GetMapping(path = "/answers/{id}/description", produces = "application/json")
    public ResponseEntity<String> getAnswerDescriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.getAnswerDescriptionById(id));
    }
}