package com.userPI.usersmanagementsystem.service;


import com.userPI.usersmanagementsystem.dto.QuestionDTO;

import java.util.List;

public interface IQuestionService {
    public List<QuestionDTO> getAllQuestions();
    public QuestionDTO createQuestion(QuestionDTO questionDTO, Long testId);


    List<QuestionDTO> getQuestionsByCategory(String category);







}
