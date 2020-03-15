package com.cgm.answerbag.questionandanswer;

import java.util.LinkedList;
import java.util.List;

public class InMemoryQuestionAndAnswers implements QuestionAnswerService {
    @Override
    public void add(String question, List<String> answer) {

    }

    @Override
    public List<String> ask(String question) {
        return new LinkedList<>();
    }
}
