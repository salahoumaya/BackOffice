package com.userPI.usersmanagementsystem.repository.levelTeset;

import com.userPI.usersmanagementsystem.entity.levelTest.Test;
import com.userPI.usersmanagementsystem.entity.levelTest.TestSubmission;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TestSubmissionRepository extends JpaRepository<TestSubmission, Long> {
    //pour durée du test
    Optional<TestSubmission> findByUserAndTest(OurUsers user, Test test);
    //pour recupere tous les test
    List<TestSubmission> findByTest(Test test);

    //Trouver toutes les soumissions par ID de test
    List<TestSubmission> findByTestId(Long testId);

    @Query("SELECT AVG(ts.score) FROM TestSubmission ts WHERE ts.test.id = :testId")
    double findAverageScoreByTestId(@Param("testId") Long testId);


    long countByTestId(Long testId);


    long countByTestIdAndScoreGreaterThanEqual(Long testId, double scoreThreshold);


    @Query("SELECT q.id, q.questionText, COUNT(a) as correctCount FROM UserAnswer a " +
            "JOIN a.question q WHERE a.isCorrect = true AND q.test.id = :testId " +
            "GROUP BY q.id ORDER BY correctCount ASC LIMIT 5")
    List<Object[]> findDifficultQuestionsByTestId(@Param("testId") Long testId);


    @Query("SELECT ts FROM TestSubmission ts WHERE ts.user = :user AND ts.test = :test AND ts.submittedAt IS NOT NULL ORDER BY ts.submittedAt DESC")
    Optional<TestSubmission> findLatestSubmittedByUserAndTest(@Param("user") OurUsers user, @Param("test") Test test);


    Optional<TestSubmission> findTopByUserOrderBySubmittedAtDesc(OurUsers user);




}
