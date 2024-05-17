package fr.univlyon1.mif10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.univlyon1.mif10.dto.*;
import fr.univlyon1.mif10.dto.associations.UserToAnswersToQuestionDTO;
import fr.univlyon1.mif10.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class QuestionController implements ControllerBase {
    private final QuestionService questionService;
    private final QuestionToAnswersService questionToAnswersService;
    private final ObjectMapper objectMapper;

    private final UserService userService;

    private final AnswerService answerService;

    private final UserToAnswersToQuestionService userToAnswersToQuestionService;

    @Autowired
    public QuestionController(QuestionService questionService, QuestionToAnswersService questionToAnswersService,
                              ObjectMapper objectMapper, UserService userService,
                              AnswerService answerService, UserToAnswersToQuestionService userToAnswersToQuestionService) {
        this.questionService = questionService;
        this.questionToAnswersService = questionToAnswersService;
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.answerService = answerService;
        this.userToAnswersToQuestionService = userToAnswersToQuestionService;
    }

    @GetMapping(path = "/questions", produces = "application/json")
    public ResponseEntity<List<Integer>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getQuestionsList());
    }

    @GetMapping(path = "/questions/{id}", produces = "application/json")
    public ResponseEntity<QuestionResponseDTO> getQuestionById(@PathVariable Long id) {
        QuestionDTO question = questionService.getQuestionById(id);
        List<AnswerDTO> answers = questionToAnswersService.getAnswersByQuestionId(id);
        InitialQuestionDTO initialQuestion = questionService.getConstraint(id);
        ConstraintDTO constraint = null;
        if (initialQuestion != null && !id.equals(initialQuestion.getId())) {
            Long idQuestion = initialQuestion.getId();
            Long idAnswer = initialQuestion.getIdAnswer();
            constraint = new ConstraintDTO(idQuestion, idAnswer);
        }
        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO(question, answers, constraint);
        return ResponseEntity.ok(questionResponseDTO);
    }

    @GetMapping(path = "/questions/{id}/description", produces = "application/json")
    public ResponseEntity<String> getQuestionDescriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionDescriptionById(id));
    }

    @GetMapping(path = "/questions/{id}/answers", produces = "application/json")
    public ResponseEntity<List<AnswerDTO>> getAnswersByQuestionId(@PathVariable Long id) {
        return ResponseEntity.ok(questionToAnswersService.getAnswersByQuestionId(id));
    }

    @PostMapping(path = "/questions/{id}/userAnswers", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> getUserAnswersByQuestionId(@PathVariable Long id, @RequestBody UserAnswersToQuestionDTO userAnswersToQuestionDTO) {
        // check if the question exists, else return 404
        QuestionDTO currentQuestion = questionService.getQuestionById(id);
        if (currentQuestion == null) {
            return ResponseEntity.notFound().build();
        }

        // for each answer from the user, check if the answer is valid
        List<UserToAnswersToQuestionDTO> userToAnswersToQuestionList = new ArrayList<>();
        for (Integer idAnswer : userAnswersToQuestionDTO.getIdAnswers()) {
            // check if the user exists, else return 404
            UserDTO currentUser = userService.findByLogin(userAnswersToQuestionDTO.getLogin());
            if (currentUser == null) {
                return ResponseEntity.notFound().build();
            }

            // check if the answer exists, else return 404
            AnswerDTO currentAnswer = answerService.getAnswerById(idAnswer.longValue());
            if (currentAnswer == null) {
                return ResponseEntity.notFound().build();
            }

            // check if the answer is valid for the question, else return 400
            if (!questionToAnswersService.questionHasAnswer(id, idAnswer.longValue())) {
                return ResponseEntity.badRequest().build();
            }

            userToAnswersToQuestionList.add(new UserToAnswersToQuestionDTO(currentUser, currentAnswer, currentQuestion));
        }

        // save the answers, return 200
        userToAnswersToQuestionService.saveAll(userToAnswersToQuestionList);
        return ResponseEntity.ok().build();
    }
}
