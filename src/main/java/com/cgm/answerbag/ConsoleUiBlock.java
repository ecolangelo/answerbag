package com.cgm.answerbag.ui;

import java.util.Optional;

public interface ConsoleUiBlock {

    Optional<ConsoleUiBlock> next(ConsoleUiBlock lastUiBlock);

}
