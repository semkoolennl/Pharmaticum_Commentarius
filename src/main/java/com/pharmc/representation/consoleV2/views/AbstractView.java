package com.pharmc.representation.consoleV2.views;

import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.views.interfaces.ViewInterface;
import com.pharmc.representation.consoleV2.views.interfaces.ViewManagerInterface;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

public abstract class AbstractView implements ViewInterface {
    protected Container container;
    protected TextIO textio;
    protected TextTerminal<?> terminal;
    protected ViewManagerInterface navigation;
    private final String route;

    public AbstractView(Container container, String route) {
        this.container  = container;
        this.textio     = container.resolve(TextIO.class);
        this.terminal   = textio.getTextTerminal();
        this.navigation = container.resolve(ViewManagerInterface.class);
        this.route      = route;

        terminal.setBookmark("");
    }

    public String getRoute() {
        return route;
    }
}
