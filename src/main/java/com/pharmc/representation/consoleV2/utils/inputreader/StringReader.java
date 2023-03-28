package com.pharmc.representation.consoleV2.utils.inputreader;

import org.beryx.textio.TextIO;

public class StringReader extends AbstractInputReader<String, StringReader> {
    private String[] validValues;
    private int minLength = 0;
    private int maxLength = Integer.MAX_VALUE;

    public StringReader(TextIO textio) {
        super(textio);
    }

    @Override
    protected StringReader getInstance() {
        return this;
    }

    @Override
    protected Object formatInput(String input) {
        return input;
    }

    @Override
    protected boolean isValidInput(Object value) {
        String input = (String) value;
        if (input.length() > maxLength || input.length() < minLength) {
            validatorMessage = "Input must be between " + minLength + " and " + maxLength + " characters long.\n";
            return false;
        }
        if (validValues != null) {
            for (String validValue : validValues) {
                if (input.equalsIgnoreCase(validValue)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public StringReader withValidValues(String[] validValues) {
        this.validValues = validValues;
        return this;
    }

    public StringReader withMinLength(int minLength) {
        this.minLength = minLength;
        return this;
    }

    public StringReader withMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }
}

