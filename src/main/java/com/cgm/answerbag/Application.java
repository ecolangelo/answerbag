package com.cgm.answerbag;

import java.util.Optional;

public class Application {

    public static void main(String[] args)  {

        ConsoleUiBlock mainMenu = NaiveBeanFactory.mainMenu();

        run(mainMenu);
    }

    protected static void run(ConsoleUiBlock previousBlock) {
        Optional<ConsoleUiBlock> optionallyTheNext = previousBlock.next(null);

        while (optionallyTheNext.isPresent()) {
            ConsoleUiBlock nextBlock = optionallyTheNext.get();
            optionallyTheNext = nextBlock.next(previousBlock);
            previousBlock = nextBlock;
        }
    }


}
