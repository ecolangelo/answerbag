package com.cgm.answerbag;

import com.cgm.answerbag.questionandanswers.InMemoryQuestionAndAnswers;
import com.cgm.answerbag.entrypoint.MainMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Application {

    public static void main(String[] args)  {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Consumer<String> messageConsumer = System.out::println;
        Supplier<String> inputProvider = () -> {
            try {
                return reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        };
        run(messageConsumer, inputProvider);
    }

    protected static void run(Consumer<String> messageConsumer, Supplier<String> inputProvider) {
        ConsoleUiBlock previousBlock = new MainMenu(messageConsumer, inputProvider, InMemoryQuestionAndAnswers.instance());

        Optional<ConsoleUiBlock> optionallyTheNext = previousBlock.next(null);

        while (optionallyTheNext.isPresent()) {
            ConsoleUiBlock nextBlock = optionallyTheNext.get();
            optionallyTheNext = nextBlock.next(previousBlock);
            previousBlock = nextBlock;
        }
    }
}
