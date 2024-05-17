package fr.univlyon1.mif10.repository;

import fr.univlyon1.mif10.dto.ConsumptionValuesDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionValuesRepository extends CrudRepository<ConsumptionValuesDTO, Long> {
}
