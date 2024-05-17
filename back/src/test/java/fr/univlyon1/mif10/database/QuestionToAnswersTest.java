package fr.univlyon1.mif10.database;

import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.associations.QuestionToAnswersDTO;
import fr.univlyon1.mif10.repository.AnswerRepository;
import fr.univlyon1.mif10.repository.QuestionRepository;
import fr.univlyon1.mif10.repository.associations.QuestionToAnswersRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import fr.univlyon1.mif10.dto.associations.ids.QuestionToAnswersId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class QuestionToAnswersTest {
    @Autowired
    private QuestionRepository questionRepository;
    private QuestionDTO savedQuestion;

    @Autowired
    private AnswerRepository answerRepository;
    private AnswerDTO savedAnswer;

    @Autowired
    private QuestionToAnswersRepository questionToAnswersRepository;
    private QuestionToAnswersDTO savedQuestionToAnswers;

    @BeforeEach
    void setUp() {
        // Create a new question
        QuestionDTO question = new QuestionDTO();
        question.setDescription("What is your favorite color?");

        // Save the question to the database and store the saved instance
        savedQuestion = questionRepository.save(question);
        assertThat(savedQuestion.getId()).isNotNull();
        assertThat(savedQuestion.getDescription()).isEqualTo("What is your favorite color?");

        // Create a new answer
        AnswerDTO answer = new AnswerDTO();
        answer.setDescription("Blue");

        // Save the answer to the database and store the saved instance
        savedAnswer = answerRepository.save(answer);
        assertThat(savedAnswer.getId()).isNotNull();
        assertThat(savedAnswer.getDescription()).isEqualTo("Blue");
    }

    @Test
    void allTests() {
        testSave();
        findById();
        testDelete();
    }

    void testSave() {
        // Create a new question to answer association
        QuestionToAnswersDTO questionToAnswers = new QuestionToAnswersDTO(savedQuestion, savedAnswer);

        // Save the question to answer association to the database
        savedQuestionToAnswers = questionToAnswersRepository.save(questionToAnswers);
        QuestionDTO foundQuestion = savedQuestionToAnswers.getQuestion();
        AnswerDTO foundAnswer = savedQuestionToAnswers.getAnswer();

        // Verify the question to answer association was saved
        assertThat(savedQuestionToAnswers).isNotNull();
        assertThat(foundQuestion.getId()).isEqualTo(savedQuestion.getId());
        assertThat(foundQuestion.getDescription()).isEqualTo(savedQuestion.getDescription());
        assertThat(foundAnswer.getId()).isEqualTo(savedAnswer.getId());
        assertThat(foundAnswer.getDescription()).isEqualTo(savedAnswer.getDescription());
    }

    void findById() {
        // Get the habit to question association from the database
        QuestionToAnswersDTO foundQuestionToAnswer = questionToAnswersRepository.findByPk(
                new QuestionToAnswersId(savedQuestion, savedAnswer)
        );

        // Verify the habit to question association was saved and retrieved
        assert foundQuestionToAnswer != null;
        assertThat(foundQuestionToAnswer.getQuestion().getId()).isEqualTo(savedQuestion.getId());
        assertThat(foundQuestionToAnswer.getQuestion().getDescription()).isEqualTo(savedQuestion.getDescription());
        assertThat(foundQuestionToAnswer.getAnswer().getId()).isEqualTo(savedAnswer.getId());
        assertThat(foundQuestionToAnswer.getAnswer().getDescription()).isEqualTo(savedAnswer.getDescription());
    }

    void testDelete() {
        int nbHabitsToQuestions = (int) questionToAnswersRepository.count();

        questionToAnswersRepository.delete(savedQuestionToAnswers);

        // Verify the habit to question association was deleted
        assertThat(questionToAnswersRepository.count()).isEqualTo(
                Math.max(nbHabitsToQuestions - 1, 0)
        );
    }

    @AfterEach
    void tearDown() {
        // Delete the saved question and answer
        questionRepository.delete(savedQuestion);
        answerRepository.delete(savedAnswer);
    }
}
