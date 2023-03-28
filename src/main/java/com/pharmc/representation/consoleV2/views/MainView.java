package com.pharmc.representation.consoleV2.views;

import com.pharmc.infrastructure.setup.Container;

public class MainView extends AbstractView {
    public MainView(Container container) {
        super(container, "/");
    }

    @Override
    public void render(Object... args) {
        terminal.resetToBookmark("");
        printer.setDelay(3);
        printer.print("Welcome to the Pharmacy Management System\n\n");

        terminal.setBookmark("welcome");

        printer.setDelay(1);
        Runnable selected = utils.selectMenu().withBookmark("welcome")
                .addOption("Drugs", () -> navigation.redirect("/drugs/index"))
                .addOption("Exit",  () -> exit())
                .read();

        printer.setDelay(0);
        terminal.setBookmark("mainmenu");
        selected.run();
    }

    private void exit() {
        boolean exit = utils.confirm().withBookmark("welcome").read("Do you want to exit? (y/n):");
        if (exit) {
            System.exit(0);
        }
    }
}
