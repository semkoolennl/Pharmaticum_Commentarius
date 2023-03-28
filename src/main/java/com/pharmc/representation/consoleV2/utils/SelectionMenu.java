package com.pharmc.representation.consoleV2.utils;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

public class SelectionMenu {
    private final TextIO textio;
    private final TextTerminal<?> terminal;
    private String[] options;
    private String bookmark;

    public SelectionMenu(TextIO textio, String[] options, String bookmark) {
        this.textio   = textio;
        this.terminal = textio.getTextTerminal();
        this.bookmark = bookmark;
        this.options  = options;
    }

    public String getInput() {
        while (true) {
            for (int i = 0; i < options.length; i++) {
                terminal.println(String.format("%d. %s", i + 1, options[i]));
            }
            String input = textio.newStringInputReader().read("Select an option");
            try {
                int index = Integer.parseInt(input);
                if (index > 0 && index <= options.length) {
                    terminal.resetToBookmark(bookmark);
                    return options[index - 1];
                }
            } catch (NumberFormatException e) {
                // Do nothing
            }
            if (bookmark != null) {
                terminal.resetToBookmark(bookmark);
            }
            terminal.println("Invalid input. Please try again.");
        }
    }
}
