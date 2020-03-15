package com.cgm.answerbag.questionandanswers;

import java.util.*;

public class InMemoryQuestionAndAnswers implements QuestionAnswerService {

    private Map<String, List<String>> questionAnswersMap = new HashMap<>();

    private InMemoryQuestionAndAnswers() {}

    public static QuestionAnswerService instance() {
        return InstanceHolder.HOLD;
    }

    @Override
    public void add(QuestionAndAnswerDTO dto) {
        questionAnswersMap.put(dto.getQuestion(), dto.getAnswers());
    }

    @Override
    public List<String> ask(String question) {
        return questionAnswersMap.getOrDefault(question, new ArrayList<>(0));
    }

    private static class InstanceHolder {

        private static InMemoryQuestionAndAnswers HOLD = new InMemoryQuestionAndAnswers();
    }

}
