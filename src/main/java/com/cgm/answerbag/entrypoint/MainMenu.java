package com.cgm.answerbag.ui;

import com.cgm.answerbag.questionandanswers.QuestionAnswerService;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MainMenu implements ConsoleUiBlock {

    private Consumer<String> messageConsumer;
    private Supplier<String> inputProvider;
    private QuestionAnswerService service;

    public MainMenu(Consumer<String> messageConsumer, Supplier<String> inputProvider, QuestionAnswerService service) {
        this.messageConsumer = messageConsumer;
        this.inputProvider = inputProvider;
        this.service = service;
    }

    @Override
    public Optional<ConsoleUiBlock> next(ConsoleUiBlock lastInput) {

        messageConsumer.accept("ask a question within the 3 options\n");
        messageConsumer.accept("==== QUESTION/ANSWER PROGRAM, OPTIONS: ====\n");
        messageConsumer.accept("\t\t1) ask a question.\n");
        messageConsumer.accept("\t\t2) add a new question in the format : <question>? \"<answer1>\" \"<answer2>\" \"<answerX>\"\n");
        messageConsumer.accept("\t\t3) exit.\n");

        String choice = inputProvider.get();

        switch (choice){
            case "1":
                return Optional.of(new AskTheQuestionUI(messageConsumer, inputProvider, service));
            case "2":
                return Optional.of(new AddTheQuestionUI(messageConsumer, inputProvider, service));
            case "3":
                return Optional.empty();

        }

        return Optional.of(new ShowErrorMessageUI(messageConsumer,"no valid input inserted : "+choice+", please retry. "));
    }
}
