package fr.univlyon1.mif10.repository;

import fr.univlyon1.mif10.dto.HabitDTO;
import org.springframework.data.repository.CrudRepository;

public interface HabitRepository extends CrudRepository<HabitDTO, Long> {
}