package fr.univlyon1.mif10.database;

import fr.univlyon1.mif10.dto.HabitDTO;
import fr.univlyon1.mif10.repository.HabitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class HabitsTest implements TablesTest {
    @Autowired
    private HabitRepository habitRepository;

    private HabitDTO savedHabit;

    @Test
    public void allTests() {
        testSave();
        findById();
        testDelete();
    }

    @Override
    public void testSave() {
        // Create a new habit
        HabitDTO habit = new HabitDTO();
        habit.setDescription("Drink water");

        // Save the habit to the database and store the saved instance
        savedHabit = habitRepository.save(habit);
        assertThat(savedHabit.getId()).isNotNull();
        assertThat(savedHabit.getDescription()).isEqualTo("Drink water");
    }

    @Override
    public void findById() {
        // Get the habit from the database
        HabitDTO foundHabit = habitRepository.findById(savedHabit.getId()).orElse(null);

        // Verify the habit was saved and retrieved
        assert foundHabit != null;
        assertThat(foundHabit.getDescription()).isEqualTo("Drink water");
    }

    @Override
    public void testDelete() {
        int nbHabits = (int) habitRepository.count();

        // Delete the saved habit from the database
        habitRepository.delete(savedHabit);

        // verify if nb habits decreased by 1
        assertThat(habitRepository.count()).isEqualTo(
                Math.max(nbHabits - 1, 0)
        );
    }
}
