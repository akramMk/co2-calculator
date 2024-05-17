package fr.univlyon1.mif10.repository.associations;

import fr.univlyon1.mif10.dto.AnswerDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.UserDTO;
import fr.univlyon1.mif10.dto.associations.UserToAnswersToQuestionDTO;
import fr.univlyon1.mif10.dto.associations.ids.UserToAnswersToQuestionId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserToAnswersToQuestionRepository extends CrudRepository<UserToAnswersToQuestionDTO, Long> {
    UserToAnswersToQuestionDTO findByPk(UserToAnswersToQuestionId pk);

    List<UserToAnswersToQuestionDTO> findByPkIdUser(UserDTO idUser);
    List<UserToAnswersToQuestionDTO> findByPkIdAnswer(AnswerDTO idAnswer);
    List<UserToAnswersToQuestionDTO> findByPkIdQuestion(QuestionDTO idUser);
    List<UserToAnswersToQuestionDTO> findByPkIdUserAndPkIdQuestion(UserDTO idUser, QuestionDTO idQuestion);

    boolean existsByPkIdUserAndPkIdQuestionAndPkIdAnswer(UserDTO user, QuestionDTO question, AnswerDTO answer);
}