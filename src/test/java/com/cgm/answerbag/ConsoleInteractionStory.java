package com.cgm.answerbag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConsoleInteractionStory {

    private Consumer<String> messageConsumer;

    private Supplier<String> inputProvider;

    private List<String> expectedMessages;

    private List<String> outputMessages;

    private ConsoleInteractionStory(){}

    public static ConsoleDisplay start(){
        return new ConsoleInteractionBuilder();
    }



    public Consumer<String> messageConsumer() {
        return messageConsumer;
    }

    public Supplier<String> inputProvider() {
        return inputProvider;
    }

    public static void verify(ConsoleInteractionStory consoleInteractionStory) {
        for(int i = 0;i<consoleInteractionStory.expectedMessages.size();i++) {
            assertThat("problem at step "+i,
                    consoleInteractionStory.expectedMessages.get(i), is(consoleInteractionStory.outputMessages.get(i)));
        }
    }

    public static class ConsoleInteractionBuilder implements ClientWrite, ConsoleDisplay {

        private ConsoleInteractionBuilder() {}

        private List<String> inputMessages = new ArrayList<>();

        private List<String> expectedOutput = new ArrayList<>();

        private List<String> actualOutput = new ArrayList<>();

        @Override
        public ConsoleDisplay thenClientWrite(String input) {
            inputMessages.add(input);
            return this;
        }

        @Override
        public ClientWrite consoleDisplay(String output) {
            expectedOutput.add(output);
            return this;
        }

        @Override
        public ConsoleInteractionStory get() {
            ConsoleInteractionStory story = new ConsoleInteractionStory();
            int[] count = new int[]{1};
            story.inputProvider = () -> {
                String currentInput = inputMessages.get(count[0]);
                System.out.println(currentInput);

                return currentInput;
            };

            story.messageConsumer = output -> {
                System.out.println(output);
                actualOutput.add(output);
            };

            story.outputMessages = actualOutput;

            story.expectedMessages = expectedOutput;

            return story;
        }
    }


    public interface ClientWrite extends Finish{
        ConsoleDisplay thenClientWrite(String input);
    }

    public interface ConsoleDisplay extends Finish{
        ClientWrite consoleDisplay(String output);
    }

    public interface Finish {
        ConsoleInteractionStory get();
    }

}
