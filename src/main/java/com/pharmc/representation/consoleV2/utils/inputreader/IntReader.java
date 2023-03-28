package com.pharmc.representation.consoleV2.utils.inputreader;

import org.beryx.textio.TextIO;

public class IntReader extends AbstractInputReader<Integer, IntReader> {
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;
    private int[] validValues;

    public IntReader(TextIO textio) {
        super(textio);
    }

    @Override
    protected IntReader getInstance() {
        return this;
    }

    @Override
    protected Object formatInput(String input) throws NumberFormatException {
        return Integer.parseInt(input);
    }

    @Override
    protected boolean isValidInput(Object value) {
        int input = (int) value;
        if (input < min || input > max) {
            return false;
        }
        if (validValues != null) {
            for (int validValue : validValues) {
                if (input == validValue) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public IntReader withMin(int min) {
        this.min = min;
        return this;
    }

    public IntReader withMax(int max) {
        this.max = max;
        return this;
    }

    public IntReader withRange(int min, int max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public IntReader withValidValues(int[] validValues) {
        this.validValues = validValues;
        return this;
    }
}
