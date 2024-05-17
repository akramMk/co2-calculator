package fr.univlyon1.mif10;

import fr.univlyon1.mif10.dto.InitialQuestionDTO;
import fr.univlyon1.mif10.repository.InitialQuestionRepository;
import fr.univlyon1.mif10.service.InitialQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private InitialQuestionService initialQuestionService;

    @Autowired
    public MainController(InitialQuestionService initialQuestionService) {
        this.initialQuestionService = initialQuestionService;
    }


    @GetMapping("/hello")
    public String test(){
        return "Hello World";
    }

    @GetMapping(value = "/db", produces = "application/json")
    public ResponseEntity<String> db(){
        return ResponseEntity.ok("{\"hello\": \"" + "Hello World" + "\"}");
    }

    @GetMapping(value = "/testInit", produces = "application/json")
    public ResponseEntity<List<InitialQuestionDTO>> testInit(){
        return ResponseEntity.ok(initialQuestionService.findAll());
    }
}
