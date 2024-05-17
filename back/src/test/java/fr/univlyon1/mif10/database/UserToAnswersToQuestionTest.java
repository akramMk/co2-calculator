package fr.univlyon1.mif10.database;

import fr.univlyon1.mif10.classes.RoleEnum;
import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.UserDTO;
import fr.univlyon1.mif10.dto.associations.UserToAnswersToQuestionDTO;
import fr.univlyon1.mif10.dto.associations.ids.UserToAnswersToQuestionId;
import fr.univlyon1.mif10.repository.AnswerRepository;
import fr.univlyon1.mif10.repository.QuestionRepository;
import fr.univlyon1.mif10.repository.UserRepository;
import fr.univlyon1.mif10.repository.associations.UserToAnswersToQuestionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserToAnswersToQuestionTest {
    @Autowired
    private UserRepository userRepository;
    private UserDTO savedUser;

    @Autowired
    private AnswerRepository answerRepository;
    private AnswerDTO savedAnswer;

    @Autowired
    private QuestionRepository questionRepository;
    private QuestionDTO savedQuestion;

    @Autowired
    private UserToAnswersToQuestionRepository userToAnswersToQuestionRepository;
    private UserToAnswersToQuestionDTO savedUserToAnswersToQuestion;

    @BeforeEach
    void setUp() {
        // Create a new user
        UserDTO user = new UserDTO();
        user.setLogin("testUser");
        user.setPassword("test Password");
        user.setRole(RoleEnum.USER);

        // Save the user to the database and store the saved instance
        savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getLogin()).isEqualTo("testUser");

        // Create a new answer
        AnswerDTO answer = new AnswerDTO();
        answer.setDescription("Blue");

        // Save the answer to the database and store the saved instance
        savedAnswer = answerRepository.save(answer);
        assertThat(savedAnswer.getId()).isNotNull();
        assertThat(savedAnswer.getDescription()).isEqualTo("Blue");

        // Create a new question
        QuestionDTO question = new QuestionDTO();
        question.setDescription("What is your favorite color?");

        // Save the question to the database and store the saved instance
        savedQuestion = questionRepository.save(question);
        assertThat(savedQuestion.getId()).isNotNull();
        assertThat(savedQuestion.getDescription()).isEqualTo("What is your favorite color?");
    }

    @Test
    void allTests() {
        testSave();
        findById();
        testDelete();
    }

    void testSave() {
        // Create a new user to answer to question association
        UserToAnswersToQuestionDTO userToAnswersToQuestion = new UserToAnswersToQuestionDTO(savedUser, savedAnswer, savedQuestion);

        // Save the user to answer to question association to the database
        savedUserToAnswersToQuestion = userToAnswersToQuestionRepository.save(userToAnswersToQuestion);
        UserDTO foundUser = savedUserToAnswersToQuestion.getUser();
        AnswerDTO foundAnswer = savedUserToAnswersToQuestion.getAnswer();
        QuestionDTO foundQuestion = savedUserToAnswersToQuestion.getQuestion();

        // Verify the user to answer to question association was saved
        assertThat(savedUserToAnswersToQuestion).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.getLogin()).isEqualTo(savedUser.getLogin());
        assertThat(foundAnswer.getId()).isEqualTo(savedAnswer.getId());
        assertThat(foundAnswer.getDescription()).isEqualTo(savedAnswer.getDescription());
        assertThat(foundQuestion.getId()).isEqualTo(savedQuestion.getId());
        assertThat(foundQuestion.getDescription()).isEqualTo(savedQuestion.getDescription());
    }

    void findById() {
        // Get the user to answer to question association from the database
        UserToAnswersToQuestionDTO foundUserToAnswersToQuestion = userToAnswersToQuestionRepository.findByPk(
                new UserToAnswersToQuestionId(savedUser, savedAnswer, savedQuestion)
        );

        // Verify the user to answer to question association was saved and retrieved
        assert foundUserToAnswersToQuestion != null;
        assertThat(foundUserToAnswersToQuestion.getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(foundUserToAnswersToQuestion.getUser().getLogin()).isEqualTo(savedUser.getLogin());
        assertThat(foundUserToAnswersToQuestion.getAnswer().getId()).isEqualTo(savedAnswer.getId());
        assertThat(foundUserToAnswersToQuestion.getAnswer().getDescription()).isEqualTo(savedAnswer.getDescription());
        assertThat(foundUserToAnswersToQuestion.getQuestion().getId()).isEqualTo(savedQuestion.getId());
        assertThat(foundUserToAnswersToQuestion.getQuestion().getDescription()).isEqualTo(savedQuestion.getDescription());
    }

    void testDelete() {
        int nbUsersToAnswersToQuestions = (int) userToAnswersToQuestionRepository.count();

        userToAnswersToQuestionRepository.delete(savedUserToAnswersToQuestion);

        // Verify the user to answer to question association was deleted
        assertThat(userToAnswersToQuestionRepository.count()).isEqualTo(
                Math.max(nbUsersToAnswersToQuestions - 1, 0)
        );
    }

    @AfterEach
    void tearDown() {
        // Delete the saved user, answer, and question
        userRepository.delete(savedUser);
        answerRepository.delete(savedAnswer);
        questionRepository.delete(savedQuestion);
    }
}
