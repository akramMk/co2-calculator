package fr.univlyon1.mif10.database;

import fr.univlyon1.mif10.dto.UserDTO;
import fr.univlyon1.mif10.repository.UserRepository;
import fr.univlyon1.mif10.classes.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// verifying if the dto of user is correct, loading data from the database

@SpringBootTest
class UsersTest {

    @Autowired
    private UserRepository userRepository;

    private UserDTO savedUser;

    @Test
    void allTests() {
        testSave();
        findById();
        testDelete();
    }

    void testSave() {
        // Create a new user
        UserDTO user = new UserDTO();
        user.setLogin("test");
        user.setPassword("test Password");
        user.setRole(RoleEnum.USER);

        // Save the user to the database and store the saved instance
        savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getLogin()).isEqualTo("test");
        assertThat(savedUser.getPassword()).isEqualTo("test Password");
        assertThat(savedUser.getRole()).isEqualTo(RoleEnum.USER);
    }

    void findById() {
        // Get the user from the database
        UserDTO foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        // Verify the user was saved and retrieved
        assert foundUser != null;
        assertThat(foundUser.getLogin()).isEqualTo("test");
        assertThat(foundUser.getPassword()).isEqualTo("test Password");
        assertThat(foundUser.getRole()).isEqualTo(RoleEnum.USER);
    }

    void testDelete() {
        int nbUsers = (int) userRepository.count();

        // Delete the saved user from the database
        userRepository.delete(savedUser);

        // verify if nb users decreased by 1
        assertThat(userRepository.count()).isEqualTo(
                Math.max(nbUsers - 1, 0)
        );
    }
}