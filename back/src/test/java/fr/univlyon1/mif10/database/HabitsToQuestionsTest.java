package fr.univlyon1.mif10.database;

import fr.univlyon1.mif10.dto.HabitDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.associations.HabitToQuestionsDTO;
import fr.univlyon1.mif10.repository.HabitRepository;
import fr.univlyon1.mif10.repository.QuestionRepository;
import fr.univlyon1.mif10.repository.associations.HabitToQuestionsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import fr.univlyon1.mif10.dto.associations.ids.HabitQuestionId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class HabitsToQuestionsTest {

    @Autowired
    private HabitRepository habitRepository;
    private HabitDTO savedHabit;

    @Autowired
    private QuestionRepository questionRepository;
    private QuestionDTO savedQuestion;

    @Autowired
    private HabitToQuestionsRepository habitToQuestionsRepository;
    private HabitToQuestionsDTO savedHabitToQuestions;

    @BeforeEach
    void setUp() {
        // Create a new habit
        HabitDTO habit = new HabitDTO();
        habit.setDescription("Drink water");

        // Save the habit to the database and store the saved instance
        savedHabit = habitRepository.save(habit);
        assertThat(savedHabit.getId()).isNotNull();
        assertThat(savedHabit.getDescription()).isEqualTo("Drink water");


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
        // Create a new habit to question association
        HabitToQuestionsDTO habitToQuestions = new HabitToQuestionsDTO(savedQuestion, savedHabit);

        // Save the habit to question association to the database
        savedHabitToQuestions = habitToQuestionsRepository.save(habitToQuestions);
        QuestionDTO foundQuestion = savedHabitToQuestions.getQuestion();
        HabitDTO foundHabit = savedHabitToQuestions.getHabit();

        // Verify the habit to question association was saved
        assertThat(savedHabitToQuestions).isNotNull();
        assertThat(foundQuestion.getId()).isEqualTo(savedQuestion.getId());
        assertThat(foundQuestion.getDescription()).isEqualTo(savedQuestion.getDescription());
        assertThat(foundHabit.getId()).isEqualTo(savedHabit.getId());
        assertThat(foundHabit.getDescription()).isEqualTo(savedHabit.getDescription());
    }

    void findById() {
        // Get the habit to question association from the database
        HabitToQuestionsDTO foundHabitToQuestions = habitToQuestionsRepository.findByPk(
                new HabitQuestionId(savedQuestion, savedHabit)
        );

        // Verify the habit to question association was saved and retrieved
        assert foundHabitToQuestions != null;
        assertThat(foundHabitToQuestions.getQuestion().getId()).isEqualTo(savedQuestion.getId());
        assertThat(foundHabitToQuestions.getQuestion().getDescription()).isEqualTo(savedQuestion.getDescription());
        assertThat(foundHabitToQuestions.getHabit().getId()).isEqualTo(savedHabit.getId());
        assertThat(foundHabitToQuestions.getHabit().getDescription()).isEqualTo(savedHabit.getDescription());
    }

    void testDelete() {
        int nbHabitsToQuestions = (int) habitToQuestionsRepository.count();

        // Delete the saved habit to question association from the database
        habitToQuestionsRepository.delete(savedHabitToQuestions);

        // Verify the habit to question association was deleted
        assertThat(habitToQuestionsRepository.count()).isEqualTo(
                Math.max(nbHabitsToQuestions - 1, 0)
        );
    }

    @AfterEach
    void tearDown() {
        // Delete the saved habit from the database
        habitRepository.delete(savedHabit);

        // Delete the saved question from the database
        questionRepository.delete(savedQuestion);
    }
}
