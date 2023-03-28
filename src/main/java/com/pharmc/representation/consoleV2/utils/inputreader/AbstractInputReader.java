package com.pharmc.representation.consoleV2.utils.inputreader;

import com.pharmc.representation.consoleV2.utils.ColorPrinter;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;


public abstract class AbstractInputReader<ReturnType, ChildType> {
    protected TextIO textio;
    protected TextTerminal<?> terminal;
    protected ColorPrinter colorPrinter;
    protected String prompt;
    protected String errorMessage;
    protected String bookmark;
    protected String validatorMessage;

    public AbstractInputReader(TextIO textio) {
        this.textio       = textio;
        this.terminal     = textio.getTextTerminal();
        this.errorMessage = "Invalid input, please try again.\n";
    }

    protected abstract ChildType getInstance();
    protected abstract Object formatInput(String input);
    protected abstract boolean isValidInput(Object value);
    public ReturnType read() {
        return read(prompt);
    }

    public ReturnType read(String prompt) {
        this.prompt  = prompt;
        while (true) {
            try {
                Object input = formatInput(readString(prompt));
                if (isValidInput(input)) {
                    return (ReturnType) input;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bookmark != null) {
                terminal.resetToBookmark(bookmark);
            }
            printErrorMessage();
            validatorMessage = null;
        }
    }

    private String readString(String prompt) {
        this.prompt = prompt;
        print(prompt);
        return textio.newStringInputReader()
                .withInputTrimming(true)
                .read();
    }

    public ChildType withBookmark(String bookmark) {
        this.bookmark = bookmark;
        return getInstance();
    }

    public ChildType withErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return getInstance();
    }

    public ChildType withPrinter(ColorPrinter printer) {
        this.colorPrinter = printer;
        return getInstance();
    }

    protected void printErrorMessage() {
        if (bookmark != null) {
            terminal.resetToBookmark(bookmark);
        }
        try {
            Thread.sleep(50);
            if (validatorMessage != null) {
                print(validatorMessage);
            } else {
                print(errorMessage);
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void print(String string) {
        if (colorPrinter != null) {
            colorPrinter.print(string);
        } else {
            terminal.print(string);
        }
    }
}
