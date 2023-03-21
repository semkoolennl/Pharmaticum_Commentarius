package com.pharmc.representation.console.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Table {
    private ArrayList<Header> headers = new ArrayList<>();
    private ArrayList<ArrayList<String>> rows = new ArrayList<>();

    public Table addHeader(String header, int length) {
        headers.add(new Header(header, length));
        return this;
    }

    public void addRow(ArrayList<String> row) throws IllegalArgumentException {
        if (row.size() != headers.size()) {
            throw new IllegalArgumentException("Row size must match header size");
        }
        rows.add(row);
    }

    public void addRow(String... row) throws IllegalArgumentException {
        ArrayList<String> rowList = new ArrayList<>(Arrays.asList(row));
        addRow(rowList);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getBorderString());
        builder.append(getHeaderString());
        builder.append(getSeparatorString());
        boolean first = true;
        for (ArrayList<String> row : rows) {
            if (!first) {
                builder.append(getSeparatorString());
            } else {
                first = false;
            }
            builder.append(getRowString(row));
        }
        builder.append(getBorderString());
        return builder.toString();
    }

    private String getBorderString() {
        StringBuilder builder = new StringBuilder();
        builder.append("+");
        for (Header header : headers) {
            builder.append(String.format("%" + (header.width + 2) + "s", "").replace(' ', '-'));
            builder.append("+");
        }
        builder.append("\n");
        return builder.toString();
    }

    private String getSeparatorString() {
        StringBuilder builder = new StringBuilder();
        builder.append("|");
        for (Header header : headers) {
            builder.append(String.format(" %-" + header.width + "s", "").replace(' ', '-'));
            builder.append(" |");
        }
        builder.append("\n");
        return builder.toString();
    }

    private String getRowString(ArrayList<String> row) {
        int lineCount = 1;
        HashMap<Integer, Integer> lineLengths = new HashMap<>();
        for (int i = 0; i < row.size(); i++) {
            String[] lines = row.get(i).split("(?<=\\G.{" + headers.get(i).width + "})");
            lineLengths.put(i, lines.length);
            if (lines.length > lineCount) {
                lineCount = lines.length;
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lineCount; i++) {
            builder.append("|");
            for (int j = 0; j < row.size(); j++) {
                String[] lines = row.get(j).split("(?<=\\G.{" + headers.get(j).width + "})");
                if (i < lines.length) {
                    builder.append(String.format(" %-" + headers.get(j).width + "s", lines[i]));
                } else {
                    builder.append(String.format(" %-" + headers.get(j).width + "s", ""));
                }
                builder.append(" |");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    private String getHeaderString() {
        StringBuilder builder = new StringBuilder();
        builder.append("|");
        for (Header header : headers) {
            builder.append(String.format(" %-" + header.width + "s", header.value));
            builder.append(" |");
        }
        builder.append("\n");
        return builder.toString();
    }
}
