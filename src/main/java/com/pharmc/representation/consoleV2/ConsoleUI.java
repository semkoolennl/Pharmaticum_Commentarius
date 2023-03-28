package com.pharmc.representation.consoleV2;

import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.utils.ViewsScanner;
import com.pharmc.representation.consoleV2.views.AbstractView;
import com.pharmc.representation.consoleV2.views.interfaces.ViewManagerInterface;
import com.pharmc.representation.interfaces.MainUiInterface;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.swing.SwingTextTerminal;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConsoleUI implements MainUiInterface {
    private final ViewManager viewManager;

    public ConsoleUI(Container container) {
        Router router = new Router();
        viewManager   = new ViewManager(router);

        SwingTextTerminal terminal = new SwingTextTerminal();
        TextIO textIO = new TextIO(terminal);

        JFrame frame = terminal.getFrame();
        Dimension windowSize = new Dimension(800, 600);
        frame.setSize(windowSize);

        // Double-check that the size of the frame was set correctly
        if (!frame.getSize().equals(windowSize)) {
            System.err.println("Failed to set window size");
            System.exit(1);
        }

        frame.setVisible(true);


        container.register(ViewManagerInterface.class, viewManager);
        container.register(TextIO.class, textIO);
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
