package com.cgm.answerbag;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class ApplicationTestIntegrationTest {


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


        Application.run(messageConsumer, interactionSimulator);




    }

}