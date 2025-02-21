package tn.esprit.leveltest.service;

import tn.esprit.leveltest.Dto.QuestionDTO;

import java.util.List;

public interface IQuestionService {
    public List<QuestionDTO> getAllQuestions();
    public QuestionDTO createQuestion(QuestionDTO questionDTO, Long testId);


    List<QuestionDTO> getQuestionsByCategory(String category);







}
