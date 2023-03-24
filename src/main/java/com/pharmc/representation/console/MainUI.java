package com.pharmc.representation.console;

import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.interfaces.MainUiInterface;

public class MainUI extends AbstractUI implements MainUiInterface {
    private DrugUI drugUI;
    public enum MenuOptions { DRUGS, EXIT }

    public MainUI(Container container) {
        super(container);
        this.drugUI = new DrugUI(container);
    }

    public void start() {
        textio.getTextTerminal().setBookmark("start");
        welcome();
        System.exit(0);
    }

    private void welcome() {
        textio.getTextTerminal().resetToBookmark("start");

        textio.getTextTerminal().println("Welcome to your personal Drug Management System\n");

        MenuOptions selected = textio.newEnumInputReader(MenuOptions.class).read("Select an option:");

        switch (selected) {
            case DRUGS:
                textio.getTextTerminal().resetToBookmark("start");
                drugUI.display();
                break;
            case EXIT:
                exit();
                return;
        }

        welcome();
    }

    private void exit() {
        boolean exit = textio.newBooleanInputReader().read("Are you sure you want to exit?");
        if (exit) {
            textio.getTextTerminal().println("Thank you for using our system");
            textio.getTextTerminal().println("Exiting...");
        } else {
            welcome();
        }
    }
}
