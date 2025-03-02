package com.userPI.usersmanagementsystem.repository.levelTeset;

import com.userPI.usersmanagementsystem.entity.levelTest.Test;
import com.userPI.usersmanagementsystem.entity.levelTest.TestSubmission;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TestSubmissionRepository extends JpaRepository<TestSubmission, Long> {
   //pour durée du test
        Optional<TestSubmission> findByUserAndTest(OurUsers user, Test test);
        //pour recupere tous les test
         List<TestSubmission> findByTest(Test test);



}
