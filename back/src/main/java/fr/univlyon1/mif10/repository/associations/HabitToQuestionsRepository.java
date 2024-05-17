package fr.univlyon1.mif10.repository.associations;

import fr.univlyon1.mif10.dto.HabitDTO;
import fr.univlyon1.mif10.dto.QuestionDTO;
import fr.univlyon1.mif10.dto.associations.ids.HabitQuestionId;
import fr.univlyon1.mif10.dto.associations.HabitToQuestionsDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitToQuestionsRepository extends CrudRepository<HabitToQuestionsDTO, Long> {
    HabitToQuestionsDTO findByPk(HabitQuestionId pk);

    List<HabitToQuestionsDTO> findByPkIdHabit(HabitDTO idHabit);
    List<HabitToQuestionsDTO> findByPkIdQuestion(QuestionDTO idQuestion);
}