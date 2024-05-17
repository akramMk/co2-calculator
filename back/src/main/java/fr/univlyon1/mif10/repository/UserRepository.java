package fr.univlyon1.mif10.repository;

import fr.univlyon1.mif10.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDTO, Long> {
    UserDTO findByLogin(String login);

    Iterable<UserDTO> findAll();
}
