package com.cgm.answerbag.questionandanswers;

import com.cgm.answerbag.ConsoleUiBlock;
import com.cgm.answerbag.entrypoint.MainMenu;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;


public class AskTheQuestionUITest {

    @Test
    public void whenAskingAnExistingQuestionYoullgetAnAnswer() {
        List<String> messages = new ArrayList<>();
        Consumer<String> messageConsumer = message->{
            messages.add(message);
        };
        String questionToBeasked = "How old are you?";
        Supplier<String> inputProvider = ()-> questionToBeasked;
        QuestionAnswerService service = mock(QuestionAnswerService.class);
        String anAnswer = "anAnswer";
        expect(service.ask("How old are you")).andReturn(Arrays.asList(anAnswer));
        replay(service);
        AskTheQuestionUI questionUI = new AskTheQuestionUI( messageConsumer,inputProvider,service);

        ConsoleUiBlock previousPage = createMock(ConsoleUiBlock.class);
        Optional<ConsoleUiBlock> next = questionUI.next(previousPage);
        verify(service);

        assertThat(messages.size(), is(2));
        assertThat(messages.get(0),is("please ask the question : "));
        assertThat(messages.get(1),is("\t\t* "+anAnswer));
        assertTrue(next.isPresent());

        assertThat(next.get().getClass().getName(), is(MainMenu.class.getName()));

    }

    @Test
    public void ifNoAnswerIsFoundTheAnswerToEverythingIs42() {
        List<String> messages = new ArrayList<>();
        Consumer<String> messageConsumer = message->{
            messages.add(message);
        };
        String questionToBeasked = "How many pillows are in my bedroom";
        Supplier<String> inputProvider = ()-> questionToBeasked;
        QuestionAnswerService service = mock(QuestionAnswerService.class);


        expect(service.ask(questionToBeasked)).andReturn(new ArrayList<>());

        replay(service);

        AskTheQuestionUI questionUI = new AskTheQuestionUI( messageConsumer,inputProvider,service);
        Optional<ConsoleUiBlock> next = questionUI.next(createMock(ConsoleUiBlock.class));

        assertThat(messages.size(), is(2));
        assertThat(messages.get(0),is("please ask the question : "));
        assertThat(messages.get(1),is("the answer to life, universe and everything is 42 according to \"The hitchhikers guide to the Galaxy\""));
        assertTrue(next.isPresent());

        assertThat(next.get().getClass().getName(), is(MainMenu.class.getName()));

    }

}