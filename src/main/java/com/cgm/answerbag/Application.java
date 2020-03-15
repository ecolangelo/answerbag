package com.cgm.assessment.question;

import com.cgm.assessment.question.gui.QuizMainMenu;
import com.cgm.assessment.question.gui.GuiException;

public class Application {

    public static void main(String[] args) throws GuiException {
        QuizMainMenu quizMainMenu = new QuizMainMenu(System.out);
        quizMainMenu.start();
    }
}
