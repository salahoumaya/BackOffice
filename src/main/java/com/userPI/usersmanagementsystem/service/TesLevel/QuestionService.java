package com.userPI.usersmanagementsystem.service.TesLevel;

import com.userPI.usersmanagementsystem.dto.levelTest.QuestionDTO;
import com.userPI.usersmanagementsystem.entity.levelTest.Question;
import com.userPI.usersmanagementsystem.entity.levelTest.Test;
import com.userPI.usersmanagementsystem.repository.levelTeset.QuestionRepository;
import com.userPI.usersmanagementsystem.repository.levelTeset.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService implements IQuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream().map(question -> new QuestionDTO(
                question.getId(),
                question.getQuestionText(),
                question.getQuestionImage(),
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD(),
                question.getCorrectAnswer()
        )).collect(Collectors.toList());
    }

    public QuestionDTO createQuestion(QuestionDTO questionDTO, Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test non trouvé"));

        if (questionDTO.getOptionA().isEmpty() || questionDTO.getOptionB().isEmpty() || questionDTO.getOptionC().isEmpty()) {
            throw new RuntimeException("Une question doit avoir au moins trois options");
        }

        if (!questionDTO.getCorrectAnswer().equals(questionDTO.getOptionA()) &&
                !questionDTO.getCorrectAnswer().equals(questionDTO.getOptionB()) &&
                !questionDTO.getCorrectAnswer().equals(questionDTO.getOptionC()) &&
                (questionDTO.getOptionD() == null || !questionDTO.getCorrectAnswer().equals(questionDTO.getOptionD()))) {
            throw new RuntimeException("La réponse correcte doit être l'une des options fournies");
        }

        Question question = new Question();
        question.setQuestionText(questionDTO.getQuestionText());
        question.setOptionA(questionDTO.getOptionA());
        question.setOptionB(questionDTO.getOptionB());
        question.setOptionC(questionDTO.getOptionC());
        question.setOptionD(questionDTO.getOptionD());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        question.setTest(test);

        if (questionDTO.getQuestionImage() != null && !questionDTO.getQuestionImage().isEmpty()) {
            question.setQuestionImage(questionDTO.getQuestionImage());  // 🟢 Enregistrer l'image
        }

        Question savedQuestion = questionRepository.save(question);
        return new QuestionDTO(
                savedQuestion.getId(),
                savedQuestion.getQuestionText(),
                savedQuestion.getQuestionImage(),  // 🟢 Retourner l'image
                savedQuestion.getOptionA(),
                savedQuestion.getOptionB(),
                savedQuestion.getOptionC(),
                savedQuestion.getOptionD(),
                savedQuestion.getCorrectAnswer()
        );
    }



}
