package com.pharmc.representation.console;

import com.pharmc.infrastructure.setup.Container;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

abstract class AbstractUI {
    protected TextIO textio;
    protected Container container;

    public AbstractUI(Container container) {
        this.container = container;
        this.textio    = TextIoFactory.getTextIO();
    }
}
