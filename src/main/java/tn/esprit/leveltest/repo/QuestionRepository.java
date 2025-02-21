package tn.esprit.leveltest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.leveltest.Entity.Question;

public interface QuestionRepository  extends JpaRepository<Question, Long> {
}
