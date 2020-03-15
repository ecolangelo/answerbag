package com.cgm.answerbag.entrypoint;

import com.cgm.answerbag.ConsoleUiBlock;

import java.util.Optional;
import java.util.function.Consumer;

public class ShowErrorMessageUI implements ConsoleUiBlock {

    private Consumer<String> messageConsumer;

    private String errorMessage;

    public ShowErrorMessageUI(Consumer<String> messageConsumer, String errorMessage) {
        this.messageConsumer = messageConsumer;
        this.errorMessage = errorMessage;
    }

    @Override
    public Optional<ConsoleUiBlock> next(ConsoleUiBlock lastBlock) {

        messageConsumer.accept(errorMessage);

        return Optional.of(lastBlock);
    }
}
