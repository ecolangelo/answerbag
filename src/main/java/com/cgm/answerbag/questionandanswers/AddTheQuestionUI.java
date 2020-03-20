package com.cgm.answerbag.questionandanswers;

import com.cgm.answerbag.ConsoleUiBlock;
import com.cgm.answerbag.entrypoint.MainMenu;
import com.cgm.answerbag.entrypoint.ShowErrorMessageUI;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddTheQuestionUI implements ConsoleUiBlock {

    protected final static String REGEX = "^([^\\?]{1,255})\\?\\s((\"[^\"]{1,255}\"\\s?)+)";
    private Consumer<String> messageConsumer;
    private Supplier<String> inputProvider;
    private QuestionAnswerService questionAnswerService;

    public AddTheQuestionUI(Consumer<String> messageConsumer, Supplier<String> inputProvider, QuestionAnswerService questionAnswerService) {
        this.messageConsumer = messageConsumer;
        this.inputProvider = inputProvider;
        this.questionAnswerService = questionAnswerService;
    }

    @Override
    public Optional<ConsoleUiBlock> next(ConsoleUiBlock lastInput) {
        messageConsumer.accept("please type the question with the answer in the following format : <question>? \"<answer1>\" \"<answer2>\" \"<answerX>\"");
        String input = inputProvider.get();

        Optional<QuestionAndAnswerDTO> eventuallyParsedInput = parse(input);

        if(eventuallyParsedInput.isPresent()) {

            QuestionAndAnswerDTO parsedInput = eventuallyParsedInput.get();

            questionAnswerService.add(parsedInput);

            return Optional.of(new MainMenu(messageConsumer, inputProvider, questionAnswerService));

        }


        return Optional.of(new ShowErrorMessageUI(messageConsumer, "you haven't inserted the question and the answers in the" +
                " right format (beware of the spaces): \n" +
                "format     : <question>? \"<answer1>\" \"<answer2>\" \"<answerX>\"\n"+
                "your input : "+input));
    }


    protected Optional<QuestionAndAnswerDTO> parse(String input) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(input);

        if(matcher.find()) {

            String question = matcher.group(1);
            List<String> answers = Arrays.stream(matcher.group(2).split(" ")).
                    map(s -> s.replaceAll("\"", "")).collect(Collectors.toList());

            return Optional.of(new QuestionAndAnswerDTO(question, answers));
        }

        return Optional.empty();
    }

}
