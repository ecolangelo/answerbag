package com.cgm.answerbag.questionandanswers;

import com.cgm.answerbag.ConsoleUiBlock;
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


public class AskTheQuestionUITest {

    @Test
    public void next() {
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

    }
}