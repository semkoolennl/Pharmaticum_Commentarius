package com.pharmc.representation.consoleV2;

import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.utils.ViewsScanner;
import com.pharmc.representation.consoleV2.views.AbstractView;
import com.pharmc.representation.consoleV2.views.interfaces.ViewManagerInterface;
import com.pharmc.representation.interfaces.MainUiInterface;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.util.ArrayList;

public class ConsoleUI implements MainUiInterface {
    private final ViewManager viewManager;

    public ConsoleUI(Container container) {
        Router router = new Router();
        viewManager   = new ViewManager(router);

        container.register(ViewManagerInterface.class, viewManager);
        container.register(TextIO.class, TextIoFactory.getTextIO());
        registerRoutes(router, container);
    }

    public void start() {
        viewManager.start();
    }

    private static void registerRoutes(Router router, Container container) {
        ArrayList<AbstractView> views = ViewsScanner.retrieveViews(container);
        for (AbstractView view : views) {
            router.register(view);
        }
    }
}
