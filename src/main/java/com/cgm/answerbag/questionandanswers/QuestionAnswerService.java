package com.cgm.answerbag.questionandanswer;

import java.util.List;
import java.util.Optional;

public interface QuestionAnswerService {

    void add(String question, List<String> answer);

    List<String> ask(String question);

}
