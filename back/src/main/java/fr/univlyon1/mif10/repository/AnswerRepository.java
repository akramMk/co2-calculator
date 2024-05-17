package fr.univlyon1.mif10.repository;

import fr.univlyon1.mif10.dto.AnswerDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<AnswerDTO, Long> {
}
