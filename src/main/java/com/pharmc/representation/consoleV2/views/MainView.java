package com.pharmc.representation.consoleV2.views;

import com.pharmc.infrastructure.setup.Container;

public class MainView extends AbstractView {
    public MainView(Container container) {
        super(container, "/");
    }

    @Override
    public void render() {
        terminal.resetToBookmark("");

        terminal.println("Welcome to the Pharmacy Management System\n");
        boolean gotodrugs = textio.newBooleanInputReader()
                .withDefaultValue(false)
                .read("Do you want to go to the drugs index?");
        if (gotodrugs) {
            navigation.redirect("/drugs/index");
            return;
        }

        boolean exit = textio.newBooleanInputReader()
                .withDefaultValue(false)
                .read("Do you want to exit?");
        if (exit) {
            System.exit(0);
        }
    }
}
