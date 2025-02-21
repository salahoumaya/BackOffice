package tn.esprit.leveltest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.leveltest.Entity.Test;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> getTopPerformers(Long testId, int limit);
}
