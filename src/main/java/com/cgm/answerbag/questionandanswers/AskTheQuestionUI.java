package com.cgm.answerbag.questionandanswers;

import com.cgm.answerbag.ConsoleUiBlock;
import com.cgm.answerbag.entrypoint.MainMenu;

import java.util.List;
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
        messageConsumer.accept("please ask the question : ");

        String input = inputProvider.get();

        List<String> answers = service.ask(input.replaceAll("\\?",""));

        if(answers.isEmpty()) {
            messageConsumer.accept("the answer to life, universe and everything is 42 according to \"The hitchhikers guide to the Galaxy\"");
        } else{
            answers.forEach(answer -> {
                messageConsumer.accept("\t\t* "+answer);
            });

        }

        return Optional.of(new MainMenu(messageConsumer,inputProvider, service));
    }
}
