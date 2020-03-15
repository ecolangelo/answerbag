package com.cgm.answerbag.questionandanswers;

import java.util.List;

public interface QuestionAnswerService {

    void add(QuestionAndAnswerDTO questionAndAnswerDTO);

    List<String> ask(String question);

}
