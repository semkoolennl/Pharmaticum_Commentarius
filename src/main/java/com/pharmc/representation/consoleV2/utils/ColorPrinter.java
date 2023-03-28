package com.pharmc.representation.consoleV2.utils;

import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ColorPrinter {
    private final TextTerminal<?> terminal;
    private HashMap<Character, Color> colormap = new HashMap<>();
    private HashMap<String, Color> stringmap = new HashMap<>();
    private Color currentColor;
    private Color defaultColor;
    private int delay = 0;
    private StringBuilder builder = new StringBuilder();
    private ArrayList<String> strings = new ArrayList<>();
    private ArrayList<Color>   colors  = new ArrayList<>();

    public ColorPrinter(TextTerminal<?> terminal, Color defaultColor) {
        this.terminal     = terminal;
        this.defaultColor = defaultColor;
        this.currentColor = defaultColor;
    }

    public ColorPrinter(TextTerminal<?> terminal) {
        this(terminal, Color.GREEN);
    }

    public void setDefaultColor(Color color) {
        defaultColor = color;
    }

    public void setBorderColor(Color color, String characters) {
        for (Character c: characters.toCharArray()) {
            colormap.put(c, color);
        }
    }

    public void setStringInColor(Color color, String string) {
        stringmap.put(string, color);
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void print(Object object) {
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Color>   colors = new ArrayList<>();
        String text = object.toString();
        builder = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            if (colormap.containsKey(c)) {
                if (shouldChangeColor(i, text)) {
                    if (builder.length() > 0) {
                        strings.add(builder.toString());
                        colors.add(currentColor);
                        builder = new StringBuilder();
                    }
                    currentColor = colormap.get(c);
                }
            } else if (currentColor != defaultColor) {
                if (builder.length() > 0) {
                    strings.add(builder.toString());
                    colors.add(currentColor);
                    builder = new StringBuilder();
                }
                currentColor = defaultColor;
            }
            builder.append(c);
        }
        if (builder.length() > 0) {
            strings.add(builder.toString());
            colors.add(currentColor);
        }
        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);
            if (stringmap.containsKey(string)) {
                terminal.getProperties().setPromptColor(stringmap.get(string));
            } else {
                terminal.getProperties().setPromptColor(colors.get(i));
            }
            if (delay > 0) {
                for (char c: string.toCharArray()) {
                    terminal.print(String.valueOf(c));
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                terminal.print(string);
            }
        }
        terminal.getProperties().setPromptColor(Color.GREEN);
        defaultColor = Color.GREEN;
    }

    private boolean shouldChangeColor(int index, String text) {
        char c = text.charAt(index);
        if (currentColor == colormap.get(c)) {
            return false;
        }

        if (index == 0) {
            return true;
        }

        while (index < text.length()-1) {
            char target = text.charAt(index+1);
            if (isWordBreak(target)) {
                return true;
            }
            if (!colormap.containsKey(target)) {
                return false;
            }

            index++;
        }
        return true;
    }

    private boolean isWordBreak(char target) {
        return target == ' ' || target == '\n';
    }


    private void printCurrent() {
        terminal.print(builder.toString());
        builder = new StringBuilder();
    }
}
