package com.cgm.answerbag;

import java.util.Optional;

public interface ConsoleUiBlock {

    Optional<ConsoleUiBlock> next(ConsoleUiBlock lastUiBlock);

}
