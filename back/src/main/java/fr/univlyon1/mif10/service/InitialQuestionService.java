package fr.univlyon1.mif10.service;

import fr.univlyon1.mif10.dto.InitialQuestionDTO;
import fr.univlyon1.mif10.repository.InitialQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitialQuestionService {
    private final InitialQuestionRepository initialQuestionRepository;

    @Autowired
    public InitialQuestionService(InitialQuestionRepository initialQuestionRepository) {
        this.initialQuestionRepository = initialQuestionRepository;
    }

    public List<InitialQuestionDTO> findAll() {
        List<InitialQuestionDTO> listQuestion =  new ArrayList<>();
        for (InitialQuestionDTO initialQuestionDTO:  initialQuestionRepository.findAll()) {
            listQuestion.add(initialQuestionDTO);
        }
        return listQuestion;
    }

    public List<Long> getAllIds(){
        List<Long> listIds = new ArrayList<>();
        for (InitialQuestionDTO initialQuestionDTO:  initialQuestionRepository.findAll()) {
            listIds.add(initialQuestionDTO.getId());
        }
        return listIds;
    }
}
