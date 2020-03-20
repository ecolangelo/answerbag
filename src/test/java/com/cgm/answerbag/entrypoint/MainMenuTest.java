package com.cgm.answerbag.entrypoint;

import com.cgm.answerbag.ConsoleUiBlock;
import com.cgm.answerbag.questionandanswers.AddTheQuestionUI;
import com.cgm.answerbag.questionandanswers.AskTheQuestionUI;
import com.cgm.answerbag.questionandanswers.QuestionAnswerService;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;


public class MainMenuTest {

    @Test
    public void whenExecutedIntroMessageWillBePrinted() {
        ConsoleUiBlock previusPage = EasyMock.createMock(ConsoleUiBlock.class);

        List<String> messages = new ArrayList<>();
        Consumer<String> consumer = messages::add;
        Supplier<String> supplier = ()->"1";
        MainMenu mainMenu = new MainMenu(consumer, supplier, EasyMock.createMock(QuestionAnswerService.class));

        mainMenu.next(previusPage);

        assertThat(messages.get(0), is("=================== QUESTION/ANSWER PROGRAM, OPTIONS: ======================\n" +
                "\t1) ask a question.\n" +
                "\t2) add a new question in the format : <question>? \"<answer1>\" \"<answer2>\" \"<answerX>\"\n" +
                "\t3) exit.\n"));
    }

    @Test
    public void whenSelect1YoullAskAQuestion() {

        ConsoleUiBlock previusPage = EasyMock.createMock(ConsoleUiBlock.class);

        List<String> messages = new ArrayList<>();
        Consumer<String> consumer = messages::add;
        Supplier<String> supplier = ()->"1";
        MainMenu mainMenu = new MainMenu(consumer, supplier, EasyMock.createMock(QuestionAnswerService.class));
        Optional<ConsoleUiBlock> next = mainMenu.next(previusPage);
        assertThat(next.get().getClass().getName(), is(AskTheQuestionUI.class.getName()));

    }

    @Test
    public void whenSelect2YoullAddAQuestion() {

        ConsoleUiBlock previusPage = EasyMock.createMock(ConsoleUiBlock.class);

        List<String> messages = new ArrayList<>();
        Consumer<String> consumer = messages::add;
        Supplier<String> supplier = ()->"2";
        MainMenu mainMenu = new MainMenu(consumer, supplier, EasyMock.createMock(QuestionAnswerService.class));
        Optional<ConsoleUiBlock> next = mainMenu.next(previusPage);
        assertThat(next.get().getClass().getName(), is( AddTheQuestionUI.class.getName()));
    }

    @Test
    public void whenSelect3EmptyOptionalIsReturned() {

        ConsoleUiBlock previusPage = EasyMock.createMock(ConsoleUiBlock.class);

        List<String> messages = new ArrayList<>();
        Consumer<String> consumer = messages::add;
        Supplier<String> supplier = ()->"3";
        MainMenu mainMenu = new MainMenu(consumer, supplier, EasyMock.createMock(QuestionAnswerService.class));
        Optional<ConsoleUiBlock> next = mainMenu.next(previusPage);
        assertFalse(next.isPresent());
    }
}