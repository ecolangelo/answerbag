package com.cgm.answerbag.questionandanswers;

import com.cgm.answerbag.ConsoleUiBlock;
import com.cgm.answerbag.entrypoint.ShowErrorMessageUI;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddTheQuestionUITest {

    @Test
    public void ifTheParseWorksQuestionMustBeAddedAndControlComesBackToTheMainMenu() {
        List<String> messageCaptured = new ArrayList<>();
        QuestionAnswerService questionAndAnswerMock = EasyMock.createMock(QuestionAnswerService.class);
        Consumer<String> messageConsumer = messageCaptured::add;
        ConsoleUiBlock previousUiBlock = EasyMock.createMock(ConsoleUiBlock.class);

        Supplier<String> stringSupplier = ()->"what do you want? \"candies\" \"chocolate\"";
        AddTheQuestionUI questionUI = new AddTheQuestionUI(messageConsumer, stringSupplier, questionAndAnswerMock){
            @Override
            protected Optional<QuestionAndAnswerDTO> parse(String input) {
                //mocking protected method
                return Optional.of(new QuestionAndAnswerDTO("what do you want", Arrays.asList("candies","chocolate")));
            }
        };

        Optional<ConsoleUiBlock> next = questionUI.next(previousUiBlock);
        assertThat(messageCaptured.size(), is(1));
        assertThat("the next block should be present", next.isPresent());

        ConsoleUiBlock consoleUiBlock = next.get();

        assertThat(consoleUiBlock.getClass().getName(), is(previousUiBlock.getClass().getName()));

    }

    @Test
    public void ifTheParseDoesNotWorkQuestionItShuldPassControlToErrorMessage() {
        List<String> messageCaptured = new ArrayList<>();
        QuestionAnswerService questionAndAnswerMock = EasyMock.createMock(QuestionAnswerService.class);
        Consumer<String> messageConsumer = messageCaptured::add;
        ConsoleUiBlock previousUiBlock = EasyMock.createMock(ConsoleUiBlock.class);

        Supplier<String> stringSupplier = ()->"what do you want? \"candies\" \"chocolate\"";
        AddTheQuestionUI questionUI = new AddTheQuestionUI(messageConsumer, stringSupplier, questionAndAnswerMock){
            @Override
            protected Optional<QuestionAndAnswerDTO> parse(String input) {
                //mocking protected method
                return Optional.empty();
            }
        };

        Optional<ConsoleUiBlock> next = questionUI.next(previousUiBlock);
        assertThat(messageCaptured.size(), is(1));
        assertThat("the next block should be present", next.isPresent());

        ConsoleUiBlock consoleUiBlock = next.get();

        assertThat(consoleUiBlock.getClass().getName(), is(ShowErrorMessageUI.class.getName()));

    }


    @Test
    public void parsingAProperlyFormattedQuestionShouldReturnProperResults() {

        AddTheQuestionUI ui = new AddTheQuestionUI(null,null,null);

        Optional<QuestionAndAnswerDTO> parse = ui.parse("what are the biggest cities in Italy? \"Rome\" \"Milan\" \"Torino\"");

        assertThat("should be there", parse.isPresent());

        QuestionAndAnswerDTO questionAndAnswerDTO = parse.get();

        assertThat(questionAndAnswerDTO.getQuestion(), is("what are the biggest cities in Italy"));
        assertThat(questionAndAnswerDTO.getAnswers().size(), is (3));
        assertThat(questionAndAnswerDTO.getAnswers().get(0), is ("Rome"));
        assertThat(questionAndAnswerDTO.getAnswers().get(1), is ("Milan"));
        assertThat(questionAndAnswerDTO.getAnswers().get(2), is ("Torino"));
    }

    @Test
    public void whenNotUsingTheProperFormatItWontGetResults() {

        AddTheQuestionUI ui = new AddTheQuestionUI(null,null,null);

        Optional<QuestionAndAnswerDTO> parse = ui.parse("what are the biggest cities in Italy? \"\" \"Rome\" \"Torino\"");

        assertFalse("shouldnt be there", parse.isPresent());

    }


    @Test
    public void itDoesMatchWhenTheStandardRequirementIsSatisfied() {
        //<question>? "<answer1>" "<answer2>" "<answerX>"
        String input = "what are the biggest cities in Italy? \"Rome\" \"Milan\" \"Torino\"";

        Pattern compile = Pattern.compile(AddTheQuestionUI.REGEX);

        Matcher matcher = compile.matcher(input);
        boolean hasMatch = matcher.find();




        assertThat("it should match", hasMatch);

        int count = matcher.groupCount();
        assertThat(count, is(3));

        assertThat(matcher.group(2), is("\"Rome\" \"Milan\" \"Torino\""));

    }

    @Test
    public void itDosntMatchWhenQuestionIsEmpty() {
        String input = "?\"*as*dfas*\" \"asdf\"";

        Pattern compile = Pattern.compile(AddTheQuestionUI.REGEX);

        Matcher matcher = compile.matcher(input);

        assertFalse(matcher.find());
    }

    @Test
    public void itDoesntMatchWhenAnswerDoesNotContainChars() {
        String input = "what are the biggest cities in Italy? \"\" \"asdf\"";

        Pattern compile = Pattern.compile(AddTheQuestionUI.REGEX);

        Matcher matcher = compile.matcher(input);

        assertFalse("it shouldnt match", matcher.find());

    }

    @Test
    public void itMatchesWhenAnswerContainsSpecialChar() {
        String input = "what are the biggest cities in Italy? \"*as*dfas*\" \"asdf\"";

        Pattern compile = Pattern.compile(AddTheQuestionUI.REGEX);

        Matcher matcher = compile.matcher(input);

        boolean condition = matcher.find();
        String output = condition ? getOutput(matcher) : "";
        assertTrue(condition);

        assertThat(matcher.group(1), is("what are the biggest cities in Italy"));
        assertThat(matcher.group(2), is("\"*as*dfas*\" \"asdf\""));
    }

    @Test
    public void itDoesNotMatchIfQuestionLengthIsOver255Chars() {
        String generatedQuestion = IntStream.iterate(0, i -> i++).mapToObj(i -> "a").limit(260).collect(Collectors.joining(""));
        String input = generatedQuestion + "? \"*as*dfas*\" \"asdf\"";

        Pattern compile = Pattern.compile(AddTheQuestionUI.REGEX);

        Matcher matcher = compile.matcher(input);

        boolean hasMatch = matcher.find();
        String output = hasMatch ? getOutput(matcher) : "";
        assertFalse(output,hasMatch);
    }

    @Test
    public void itDoesNotMatchIfAnswerLengthIsOver255Chars() {
        String generatedAnswer = IntStream.iterate(0, i -> i++).mapToObj(i -> "a").limit(260).collect(Collectors.joining(""));

        String input = "what are the biggest cities" + "? \""+generatedAnswer+"\" \"asdfas\"";

        Pattern compile = Pattern.compile(AddTheQuestionUI.REGEX);

        Matcher matcher = compile.matcher(input);

        boolean hasMatch = matcher.find();


        String output = hasMatch ? getOutput(matcher) : "";


        assertFalse(output,hasMatch);
    }

    private String getOutput(Matcher matcher) {
        StringBuilder builder = new StringBuilder();
        int groupCount = matcher.groupCount();
        for(int i = 0; i<= groupCount; i++) {
            builder.append(matcher.group(i)+"\n");
        }
        return builder.toString();
    }


}