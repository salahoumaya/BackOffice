package com.userPI.usersmanagementsystem.controller;

import com.userPI.usersmanagementsystem.dto.SubmitExamDTO;
import com.userPI.usersmanagementsystem.dto.UserAnswerDTO;
import com.userPI.usersmanagementsystem.entity.AnswerEx;
import com.userPI.usersmanagementsystem.entity.Examen;
import com.userPI.usersmanagementsystem.entity.ExamenParticipant;
import com.userPI.usersmanagementsystem.entity.QuestionEx;
import com.userPI.usersmanagementsystem.entity.user.OurUsers;
import com.userPI.usersmanagementsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatbot/answers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnswerExController {

    private final AnswerRepository answerRepo;
    private final QuestionExRepository questionRepo;
    private final UsersRepo usersRepo;
    private final ExamenParticipantRepository examenParticipationRepo;
    @PostMapping("/{questionId}")
    public ResponseEntity<AnswerEx> addAnswerToQuestion(
            @PathVariable Long questionId, @RequestBody AnswerEx answer) {
        QuestionEx question = questionRepo.findById(questionId).orElseThrow();
        answer.setQuestion(question);
        return ResponseEntity.ok(answerRepo.save(answer));
    }

    private final ExamenRepository examRepo;

@PostMapping("/submit")
public ResponseEntity<Double> submitExam(@RequestBody SubmitExamDTO dto,@RequestParam  Integer userId,@RequestParam Long ExamenId) {
    Examen exam = examRepo.findById(dto.getExamId()).orElseThrow();
    int correctCount = 0;
    for (UserAnswerDTO ua : dto.getAnswers()) {
        AnswerEx answer = answerRepo.findById(ua.getSelectedAnswerId()).orElseThrow();
        if (answer.isCorrect()) {
            correctCount++;
        }
    }
    double score = correctCount;
    OurUsers ourUsers= usersRepo.findById(userId).get();
    Examen examen= examRepo.findById(ExamenId).get();
   ExamenParticipant participant =    new ExamenParticipant();
   participant.setUser(ourUsers);
   participant.setExamen(examen);
    participant.setNote(score);
    participant.setMoyenne(0.0);
    examenParticipationRepo.save(participant);
    return ResponseEntity.ok(score);
}


}
