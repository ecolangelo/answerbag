package com.cgm.answerbag;

import com.cgm.answerbag.entrypoint.MainMenu;
import com.cgm.answerbag.questionandanswers.InMemoryQuestionAndAnswers;
import com.cgm.answerbag.questionandanswers.QuestionAnswerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ApplicationBeanFactory {

    private static MainMenu mainMenu;

    private static Consumer<String> consumer = System.out::println;

    private static Supplier<String> inputProvider;

    public static MainMenu mainMenu(){
        if(mainMenu == null) {
            mainMenu = new MainMenu(messageConsumer(), inputProvider(), questionAnswerService());
        }
        return mainMenu;
    }


    public static Consumer<String> messageConsumer(){
        return consumer;
    }

    public static Supplier<String> inputProvider() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return ()->{
            try {
                return reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        };
    }


    public static QuestionAnswerService questionAnswerService() {
        return InMemoryQuestionAndAnswers.instance();
    }






}
