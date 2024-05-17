package fr.univlyon1.mif10.service;

import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.UserDTO;
import fr.univlyon1.mif10.dto.associations.UserToAnswersToQuestionDTO;
import fr.univlyon1.mif10.repository.AnswerRepository;
import fr.univlyon1.mif10.repository.QuestionRepository;
import fr.univlyon1.mif10.repository.UserRepository;
import fr.univlyon1.mif10.repository.associations.UserToAnswersToQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserToAnswersToQuestionService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserToAnswersToQuestionRepository userToAnswersToQuestionRepository;
    private final HabitToQuestionsService habitToQuestionsService;

    @Autowired
    public UserToAnswersToQuestionService(UserRepository userRepository,
                                          QuestionRepository questionRepository,
                                          AnswerRepository answerRepository,
                                          UserToAnswersToQuestionRepository userToAnswersToQuestionRepository,
                                          HabitToQuestionsService habitToQuestionsService) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userToAnswersToQuestionRepository = userToAnswersToQuestionRepository;
        this.habitToQuestionsService = habitToQuestionsService;
    }

    public List<AnswerDTO> getAnswersByUserId(Long idUser) {
        UserDTO user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<UserToAnswersToQuestionDTO> userToAnswersToQuestion = userToAnswersToQuestionRepository.findByPkIdUser(user);
        List<AnswerDTO> answers = new ArrayList<>();
        for (UserToAnswersToQuestionDTO uta : userToAnswersToQuestion) {
            answerRepository.findById(uta.getAnswer().getId()).ifPresent(answers::add);
        }
        return answers;
    }
    
    public List<Integer> getQuestionsIdAnsweredByUserId(Long id) {
        UserDTO user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<UserToAnswersToQuestionDTO> userToAnswersToQuestion = userToAnswersToQuestionRepository.findByPkIdUser(user);
        List<Integer> questionsId = new ArrayList<>();
        for (UserToAnswersToQuestionDTO uta : userToAnswersToQuestion) {
            // if not already in the list
            if (!questionsId.contains(Math.toIntExact(uta.getQuestion().getId()))) {
                questionsId.add(Math.toIntExact(uta.getQuestion().getId()));
            }
        }
        return questionsId;
    }

    public List<Pair<QuestionDTO, List<AnswerDTO>>> getQuestionsAndAnswersByUserId(Long idUser) {
        // veryfing if the user exists
        UserDTO user = userRepository.findById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // regrouping the answers of the questions with the same id
        Map<QuestionDTO,List<AnswerDTO>> answersByQuestionId = new HashMap<>();
        List<UserToAnswersToQuestionDTO> userToAnswersToQuestion = userToAnswersToQuestionRepository.findByPkIdUser(user);
        for (UserToAnswersToQuestionDTO uta : userToAnswersToQuestion) {
            QuestionDTO question = questionRepository.findById(uta.getQuestion().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Question not found"));

            // if the question is not here :
            answersByQuestionId.computeIfAbsent(question, k -> new ArrayList<>());

            answersByQuestionId.get(question).add(uta.getAnswer());
        }

        List<Pair<QuestionDTO, List<AnswerDTO>>> form = new ArrayList<>();
        for (Map.Entry<QuestionDTO, List<AnswerDTO>> entry : answersByQuestionId.entrySet()) {
            form.add(new Pair<>(entry.getKey(), entry.getValue()));
        }

        // sort by question id
        form.sort((o1, o2) -> (int) (o1.getFirst().getId() - o2.getFirst().getId()));

        return form;
    }

    public List<AnswerDTO> getAnswersToSpecificQuestionFromUser(Long idUser, Long idQuestion) {
        UserDTO user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("User not found"));
        QuestionDTO question = questionRepository.findById(idQuestion).orElseThrow(() -> new IllegalArgumentException("Question not found"));
        List<UserToAnswersToQuestionDTO> userToAnswersToQuestion = userToAnswersToQuestionRepository.findByPkIdUserAndPkIdQuestion(user, question);
        List<AnswerDTO> answers = new ArrayList<>();
        for (UserToAnswersToQuestionDTO uta : userToAnswersToQuestion) {
            answerRepository.findById(uta.getAnswer().getId()).ifPresent(answers::add);
        }
        return answers;
    }

    public void saveAll(List<UserToAnswersToQuestionDTO> userToAnswersToQuestionList) {
        userToAnswersToQuestionRepository.saveAll(userToAnswersToQuestionList);
    }

    public boolean containsAnswerToQuestionFromUser(Long idUser, Long idQuestion, Long idAnswer) {
        UserDTO user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("User not found"));
        QuestionDTO question = questionRepository.findById(idQuestion).orElseThrow(() -> new IllegalArgumentException("Question not found"));
        AnswerDTO answer = answerRepository.findById(idAnswer).orElseThrow(() -> new IllegalArgumentException("Answer not found"));
        return userToAnswersToQuestionRepository.existsByPkIdUserAndPkIdQuestionAndPkIdAnswer(user, question, answer);
    }

    public List<Pair<QuestionDTO, List<AnswerDTO>>> getAnswerByUserForHabit(Long idUser, Long idHabit){
        List<Pair<QuestionDTO, List<AnswerDTO>>> result = new ArrayList<>();
        Optional<UserDTO> user = userRepository.findById(idUser);
        if (user.isPresent()){
            List<QuestionDTO> habitsQuestion = habitToQuestionsService.getQuestionsByHabitId(idHabit);
            Iterable<QuestionDTO> questions = questionRepository.findAll();
            for (QuestionDTO question : questions){
                if (habitsQuestion.contains(question)){
                    result.add(new Pair<>(question, getAnswersToSpecificQuestionFromUser(idUser, question.getId())));
                }
            }
        }
        return result;
    }
}
