package com.pharmc.representation.consoleV2.utils.inputreader;

import org.beryx.textio.TextIO;

public class Confirmer extends AbstractInputReader<Boolean, Confirmer> {

    public Confirmer(TextIO textio) {
        super(textio);
    }

    @Override
    protected Confirmer getInstance() {
        return this;
    }

    @Override
    protected Boolean formatInput(String input) {
        if (input.equalsIgnoreCase("y")) {
            return true;
        } else if (input.equalsIgnoreCase("n")) {
            return false;
        }
        return null;
    }

    @Override
    protected boolean isValidInput(Object value) {
        return value != null;
    }
}
