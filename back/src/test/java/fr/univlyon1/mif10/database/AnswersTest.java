package fr.univlyon1.mif10.database;

import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.repository.AnswerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AnswersTest {

    @Autowired
    private AnswerRepository answerRepository;

    private AnswerDTO savedAnswer;

    @Test
    void allTests() {
        testSave();
        findById();
        testDelete();
    }

    void testSave() {
        // Create a new answer
        AnswerDTO answer = new AnswerDTO();
        answer.setDescription("Blue");

        // Save the answer to the database and store the saved instance
        savedAnswer = answerRepository.save(answer);
        assertThat(savedAnswer.getId()).isNotNull();
        assertThat(savedAnswer.getDescription()).isEqualTo("Blue");
    }

    void findById() {
        // Get the answer from the database
        AnswerDTO foundAnswer = answerRepository.findById(savedAnswer.getId()).orElse(null);

        // Verify the answer was saved and retrieved
        assert foundAnswer != null;
        assertThat(foundAnswer.getDescription()).isEqualTo("Blue");
    }

    void testDelete() {
        int nbAnswers = (int) answerRepository.count();

        // Delete the saved answer from the database
        answerRepository.delete(savedAnswer);

        // verify if nb answers decreased by 1
        assertThat(answerRepository.count()).isEqualTo(
                Math.max(nbAnswers - 1, 0)
        );
    }
}