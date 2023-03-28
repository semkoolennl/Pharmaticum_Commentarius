package com.pharmc.representation.consoleV2.utils;

import org.beryx.textio.TextIO;

public class MenuBuilder {
    public static String selectionMenu(TextIO textio, String[] options, String bookmark) {
        SelectionMenu menu = new SelectionMenu(textio, options, bookmark);
        return menu.getInput();
    }

    public static String selectionMenu(TextIO textio, String[] options) {
        return selectionMenu(textio, options, null);
    }
}
