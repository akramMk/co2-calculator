package fr.univlyon1.mif10.database;

import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// verifying if the dto of question is correct, loading data from the database

@SpringBootTest
class QuestionsTest {

    @Autowired
    private QuestionRepository questionRepository;

    private QuestionDTO savedQuestion;

    @Test
    void allTests() {
        testSave();
        findById();
        testDelete();
    }

    void testSave() {
        // Create a new question
        QuestionDTO question = new QuestionDTO();
        question.setDescription("What is your favorite color?");

        // Save the question to the database and store the saved instance
        savedQuestion = questionRepository.save(question);
        assertThat(savedQuestion.getId()).isNotNull();
        assertThat(savedQuestion.getDescription()).isEqualTo("What is your favorite color?");
    }

    void findById() {
        // Get the question from the database
        QuestionDTO foundQuestion = questionRepository.findById(savedQuestion.getId()).orElse(null);

        // Verify the question was saved and retrieved
        assert foundQuestion != null;
        assertThat(foundQuestion.getDescription()).isEqualTo("What is your favorite color?");
    }

    void testDelete() {
        int nbQuestions = (int) questionRepository.count();

        // Delete the saved question from the database
        questionRepository.delete(savedQuestion);

        // verify if nb questions decreased by 1
        assertThat(questionRepository.count()).isEqualTo(
                Math.max(nbQuestions - 1, 0)
        );
    }
}
