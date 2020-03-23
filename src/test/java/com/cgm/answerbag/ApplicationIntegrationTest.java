package com.cgm.answerbag;

import com.cgm.answerbag.entrypoint.MainMenu;
import com.cgm.answerbag.questionandanswers.InMemoryQuestionAndAnswers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ApplicationIntegrationTest {



    @Test
    public void testAddingAQuestionAndThenAskingIt() {

        Consumer<String> messageConsumer = System.out::println;

        int[] counter = new int[]{0};
        Map<Integer, String> interactionMap = new HashMap<Integer,String>(){
            {
                put(0,"2");
                put(1,"How old is the world? \"30\" \"40\" \"50\"");
                put(2,"1");
                put(3,"How old is the world?");
                put(4,"3");
            }
        };


        Supplier<String> interactionSimulator = ()->{

            String responseToConsole = interactionMap.get(counter[0]);
            System.out.println("responseToConsole = " + responseToConsole);
            counter[0]++;
            return responseToConsole;

        };


        Application.run(new MainMenu(messageConsumer, interactionSimulator, InMemoryQuestionAndAnswers.instance()));


    }


    @Test
    public void testAddingAQuestionButWithAnInvalidFormat() throws Exception {
        Consumer<String> messageConsumer = System.out::println;

        int[] counter = new int[]{0};
        Map<Integer, String> interactionMap = new HashMap<Integer,String>(){
            {
                put(0,"2");
                put(1,"How old is the world? ");
                put(2,"How old is the world? \"30\" \"40\" \"50\"");
                put(3,"1");
                put(4,"How old is the world?");
                put(5,"3");
            }
        };


        Supplier<String> interactionSimulator = ()->{

            String responseToConsole = interactionMap.get(counter[0]);
            System.out.println("responseToConsole = " + responseToConsole);
            counter[0]++;
            return responseToConsole;

        };

        Application.run(new MainMenu(messageConsumer, interactionSimulator, InMemoryQuestionAndAnswers.instance()));


    }

}