package com.userPI.usersmanagementsystem.service.TesLevel;


import com.userPI.usersmanagementsystem.dto.levelTest.QuestionDTO;
import com.userPI.usersmanagementsystem.dto.levelTest.TestSubmissionDTO;

import java.util.List;

public interface IQuestionService {
    public List<QuestionDTO> getAllQuestions();
    public QuestionDTO createQuestion(QuestionDTO questionDTO, Long testId);








}
