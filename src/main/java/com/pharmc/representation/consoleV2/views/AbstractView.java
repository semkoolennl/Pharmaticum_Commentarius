package com.pharmc.representation.consoleV2.views;

import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.utils.ColorPrinter;
import com.pharmc.representation.consoleV2.utils.TextIOUtils;
import com.pharmc.representation.consoleV2.views.interfaces.ViewInterface;
import com.pharmc.representation.consoleV2.views.interfaces.ViewManagerInterface;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import java.util.ArrayList;

public abstract class AbstractView implements ViewInterface {
    protected Container container;
    protected TextIO textio;
    protected TextTerminal<?> terminal;
    protected ViewManagerInterface navigation;
    private final String route;
    protected TextIOUtils utils;
    protected ColorPrinter printer;

    public AbstractView(Container container, String route) {
        this.container  = container;
        this.textio     = container.resolve(TextIO.class);
        this.terminal   = textio.getTextTerminal();
        this.navigation = container.resolve(ViewManagerInterface.class);
        this.route      = route;

        this.utils   = new TextIOUtils(textio);
        this.printer = new ColorPrinter(terminal);

        terminal.setBookmark("");
    }

    public String getRoute() {
        return route;
    }
}
