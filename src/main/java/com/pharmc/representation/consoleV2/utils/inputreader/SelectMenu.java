package com.pharmc.representation.consoleV2.utils.inputreader;

import org.beryx.textio.TextIO;

import java.util.LinkedHashMap;
import java.util.Map;

public class SelectMenu extends AbstractInputReader<Runnable, SelectMenu> {
    private Map<String, Runnable> options = new LinkedHashMap<>();
    public SelectMenu(TextIO textio) {
        super(textio);
    }

    public SelectMenu withOptions(Map<String, Runnable> options) {
        this.options = options;
        return this;
    }

    public SelectMenu addOption(String option, Runnable runnable) {
        options.put(option, runnable);
        return this;
    }

    @Override
    public Runnable read() {
        prompt = getOptionsString();
        return super.read();
    }

    @Override
    protected SelectMenu getInstance() {
        return this;
    }

    @Override
    protected Object formatInput(String input) {
        int index = Integer.parseInt(input);
        return options.values().toArray()[index - 1];
    }

    @Override
    protected boolean isValidInput(Object value) {
        return value != null;
    }

    private String getOptionsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Options:\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append("  ").append(i + 1).append(") ").append(options.keySet().toArray()[i]).append("\n");
        }
        sb.append("Please select an option:");
        return sb.toString();
    }
}
