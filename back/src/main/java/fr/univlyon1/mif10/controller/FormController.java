package fr.univlyon1.mif10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FormController implements ControllerBase {
    private final UserToAnswersToQuestionService userToAnswersToQuestionService;
    private final ObjectMapper objectMapper;

    @Autowired
    public FormController(UserToAnswersToQuestionService userToAnswersToQuestionService, ObjectMapper objectMapper) {
        this.userToAnswersToQuestionService = userToAnswersToQuestionService;
        this.objectMapper = objectMapper;
    }

    // return questions + answers from this idUser
    @GetMapping(path = "/form/{id}", produces = "application/json")
    public ResponseEntity<String> getFormByUserId(@PathVariable Long id) {
        List<Pair<QuestionDTO, List<AnswerDTO>>> questionsAndAnswers = userToAnswersToQuestionService.getQuestionsAndAnswersByUserId(id);
        ArrayNode questionsAndAnswersArrayNode = objectMapper.createArrayNode();
        for (Pair<QuestionDTO, List<AnswerDTO>> qa : questionsAndAnswers) {
            ObjectNode questionAndAnswersNode = objectMapper.createObjectNode();

            // Question
            ObjectNode questionNode = objectMapper.createObjectNode();
            questionNode.put("id", qa.getFirst().getId());
            questionNode.put("description", qa.getFirst().getDescription());
            questionAndAnswersNode.set("question", questionNode);

            // Answers
            ArrayNode answersArrayNode = objectMapper.createArrayNode();
            for (AnswerDTO a : qa.getSecond()) {
                ObjectNode answerNode = objectMapper.createObjectNode();
                answerNode.put("id", a.getId());
                answerNode.put("description", a.getDescription());
                answersArrayNode.add(answerNode);
            }
            questionAndAnswersNode.set("answers", answersArrayNode);

            questionsAndAnswersArrayNode.add(questionAndAnswersNode);
        }

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.set("form", questionsAndAnswersArrayNode);

        return ResponseEntity.ok(rootNode.toString());
    }

    @GetMapping(path = "/form/{id}/answered", produces = "application/json")
    public ResponseEntity<List<Integer>> getFormQuestionsAnsweredByUserId(@PathVariable Long id) {
        List<Integer> questionsId = userToAnswersToQuestionService.getQuestionsIdAnsweredByUserId(id);
        return ResponseEntity.ok(questionsId);
    }
}
