package com.cgm.answerbag.ui;

import com.cgm.answerbag.questionandanswers.QuestionAnswerService;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AskTheQuestionUI implements ConsoleUiBlock {

    private final QuestionAnswerService service;
    private Consumer<String> messageConsumer;
    private Supplier<String> inputProvider;

    public AskTheQuestionUI(Consumer<String> messageConsumer, Supplier<String> inputProvider, QuestionAnswerService service) {
        this.messageConsumer = messageConsumer;
        this.inputProvider = inputProvider;
        this.service = service;
    }

    @Override
    public Optional<ConsoleUiBlock> next(ConsoleUiBlock lastInput) {



        return Optional.empty();
    }
}
