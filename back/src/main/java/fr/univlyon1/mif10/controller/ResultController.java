package fr.univlyon1.mif10.controller;

import fr.univlyon1.mif10.dto.ResultResponseDTO;
import fr.univlyon1.mif10.service.HabitCalculatorService;
import fr.univlyon1.mif10.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController implements ControllerBase {
    private final UserService userService;
    private final HabitCalculatorService habitCalculatorService;

    @Autowired
    public ResultController(UserService userService,
                            HabitCalculatorService habitCalculatorService) {
        this.userService = userService;
        this.habitCalculatorService = habitCalculatorService;
    }

    @GetMapping("/result/{login}")
    public ResponseEntity<ResultResponseDTO> getResult(@PathVariable String login) {
        double transport = habitCalculatorService.calculateTransportScore(userService.findByLogin(login));
        double alimentation = habitCalculatorService.calculateAlimentationScore(userService.findByLogin(login));
        double logement = habitCalculatorService.calculateLogementScore(userService.findByLogin(login));
        double divers = habitCalculatorService.calculateDiversScore(userService.findByLogin(login));
        //double scoreFinale = habitCalculatorService.calculateScoreForUser(userService.findByLogin(login));

        return ResponseEntity.ok(new ResultResponseDTO(transport, logement, alimentation, 1.6D, divers));
    }
}