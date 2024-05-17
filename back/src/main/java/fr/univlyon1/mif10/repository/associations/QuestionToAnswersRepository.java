package fr.univlyon1.mif10.repository.associations;

import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.associations.QuestionToAnswersDTO;
import fr.univlyon1.mif10.dto.associations.ids.QuestionToAnswersId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionToAnswersRepository extends CrudRepository<QuestionToAnswersDTO, Long> {
    QuestionToAnswersDTO findByPk(QuestionToAnswersId pk);

    List<QuestionToAnswersDTO> findByPkIdQuestion(QuestionDTO idQuestion);
    List<QuestionToAnswersDTO> findByPkIdAnswer(AnswerDTO idAnswer);

    Optional<Object> findByPkIdQuestionAndPkIdAnswer(QuestionDTO question, AnswerDTO answer);
}