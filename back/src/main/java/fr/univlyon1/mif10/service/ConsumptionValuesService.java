package fr.univlyon1.mif10.service;

import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.ConsumptionValuesDTO;
import fr.univlyon1.mif10.repository.AnswerRepository;
import fr.univlyon1.mif10.repository.ConsumptionValuesRepository;
import fr.univlyon1.mif10.repository.associations.QuestionToAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumptionValuesService {
    private final ConsumptionValuesRepository consumptionValuesRepository;

    public ConsumptionValuesService(ConsumptionValuesRepository consumptionValuesRepository) {
        this.consumptionValuesRepository = consumptionValuesRepository;
    }

    public List<ConsumptionValuesDTO> getConsumptionValueList() {
        Iterable<ConsumptionValuesDTO> cv = consumptionValuesRepository.findAll();
        List<ConsumptionValuesDTO> values = new ArrayList<>();
        cv.forEach(values::add);
        return values;
    }

    public ConsumptionValuesDTO getConsumptionValueById(Long id) {
        return consumptionValuesRepository.findById(id).orElse(null);
    }

}
