package fr.univlyon1.mif10.service;

import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.ConsumptionValuesDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.UserDTO;
import jakarta.servlet.http.PushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HabitCalculatorService {
    private final UserToAnswersToQuestionService userToAnswersToQuestionService;
    private final HabitToQuestionsService habitToQuestionsService;

    private final ConsumptionValuesService consumptionValuesService;

    @Autowired
    public HabitCalculatorService(UserToAnswersToQuestionService userToAnswersToQuestionService, HabitToQuestionsService habitToQuestionsService, ConsumptionValuesService consumptionValuesService) {
        this.userToAnswersToQuestionService = userToAnswersToQuestionService;
        this.habitToQuestionsService = habitToQuestionsService;
        this.consumptionValuesService = consumptionValuesService;
    }


    public double calculateTransportScore(UserDTO user) {
//        List<QuestionDTO> questionTransport = habitToQuestionsService.getQuestionsByHabitId(1L);
//        List<AnswerDTO> answers = userToAnswersToQuestionService.getAnswersByUserId(user.getId());
//        List<AnswerDTO> answerToQuestion2 = userToAnswersToQuestionService.getAnswersToSpecificQuestionFromUser(user.getId(), 2L);
//        double moteur = 0.0;
//        if (!answerToQuestion2.isEmpty() ) {
//            Long idAnswer = answerToQuestion2.get(0).getId();
//            moteur = consumptionValuesService.getConsumptionValueById(idAnswer).getValue();
//        }
        double moteur = getValueOfResponseForUser(user,2L);
        double genre = getValueOfResponseForUser(user,3L);
        double distance = getValueOfResponseForUser(user,4L);

        return moteur*genre*distance;
    }

    public double calculateAlimentationScore(UserDTO user) {
//        List<AnswerDTO> answerToQuestion5 = userToAnswersToQuestionService.getAnswersToSpecificQuestionFromUser(user.getId(), 5L);
//        double freq5 = 0.0;
//        if (!answerToQuestion5.isEmpty()) {
//            Long idAnswer5 = answerToQuestion5.get(0).getId();
//            freq5 = consumptionValuesService.getConsumptionValueById(idAnswer5).getValue();
//        }
        double freq5 = getValueOfResponseForUser(user,5L);
        double freq6 = getValueOfResponseForUser(user,6L);
        double freq7 = getValueOfResponseForUser(user,7L);
        double freq8 = getValueOfResponseForUser(user,8L);
        double freq9 = getValueOfResponseForUser(user,9L);
        double freq10 = getValueOfResponseForUser(user,10L);
        double freq11 = getValueOfResponseForUser(user,11L);
        double freq12 = getValueOfResponseForUser(user,12L);

        return (freq5*1.29)+(freq6*15)+(freq7*6)+(freq8*5)+(freq9*10)+(freq10*4)+(freq11*5)+(freq12*4);
    }

    public double calculateLogementScore(UserDTO user) {
//        List<AnswerDTO> answerToQuestion5 = userToAnswersToQuestionService.getAnswersToSpecificQuestionFromUser(user.getId(), 5L);
//        double freq5 = 0.0;
//        if (!answerToQuestion5.isEmpty()) {
//            Long idAnswer5 = answerToQuestion5.get(0).getId();
//            freq5 = consumptionValuesService.getConsumptionValueById(idAnswer5).getValue();
//        }
        double chouffer = getValueOfResponseForUser(user,13L);
        double nbrPiece = getValueOfResponseForUser(user,14L);
        double moiChouffer = getValueOfResponseForUser(user,15L);

        return (chouffer + nbrPiece*3 ) * moiChouffer ;
    }
    public double calculateDiversScore(UserDTO user) {
        double utiliseOrdi = getValueOfResponseForUser(user,16L);
        double genre = getValueOfResponseForUser(user,17L);
        double tempsUtil = getValueOfResponseForUser(user,18L);

        return utiliseOrdi*(genre + tempsUtil);
    }

    public double calculateScoreForUser(UserDTO user) {

        return calculateTransportScore(user) + calculateLogementScore(user) + calculateAlimentationScore(user) + calculateDiversScore(user);
    }

    //fonction utiliser pour ce service
    public double getValueOfResponseForUser(UserDTO user, Long idQuestion){
        List<AnswerDTO> answerToQuestion = userToAnswersToQuestionService.getAnswersToSpecificQuestionFromUser(user.getId(), idQuestion);
        double X = 0.0;
        if (!answerToQuestion.isEmpty()) {
            Long idAnswer = answerToQuestion.get(0).getId();
            X = consumptionValuesService.getConsumptionValueById(idAnswer).getValue();
        }
        return X;
    }

//    public List<Pair<QuestionDTO, List<AnswerDTO>>> getUserAnswerForHabit(Long habitID, List<Pair<QuestionDTO, List<AnswerDTO>>> userAnswer) {
//
//        List<Pair<QuestionDTO, List<AnswerDTO>>> result = new ArrayList<>();
//        List<QuestionDTO> questions = habitToQuestionsService.getQuestionsByHabitId(habitID);
//        for (QuestionDTO question : questions) {
//            if (userAnswer.stream().anyMatch(pair -> pair.getFirst().getId().equals(question.getId()))) {
//                Optional<Pair<QuestionDTO, List<AnswerDTO>>> pair = userAnswer.stream().
//                        filter(p -> p.getFirst().getId().equals(question.getId())).findFirst();
//                if (pair.isPresent()){
//                    result.add(pair.get());
//                }
//            }
//        }
//        return result;
//    }
}
