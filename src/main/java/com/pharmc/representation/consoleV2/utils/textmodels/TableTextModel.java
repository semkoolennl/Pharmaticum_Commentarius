package com.pharmc.representation.consoleV2.utils.textmodels;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TableTextModel {
    private ArrayList<String> headers = new ArrayList<>();
    private ArrayList<Integer> headerLengths = new ArrayList<>();
    private ArrayList<ArrayList<String>> rows = new ArrayList<>();

    public TableTextModel setHeaders(String... headers) {
        this.headers.clear();
        this.headerLengths.clear();

        for (String header : headers) {
            this.headers.add(header);
            this.headerLengths.add(header.length());
        }

        return this;
    }

    public void setHeaderLengths(int... lengths) {
        if (headers.size() != 0 && lengths.length != headers.size()) {
            throw new IllegalArgumentException("Lengths must match header size");
        }

        headerLengths.clear();
        for (int i = 0; i < lengths.length; i++) {
            headerLengths.add(i, lengths[i]);
        }
    }

    public TableTextModel addHeader(String header, int length) {
        headers.add(header);
        headerLengths.add(length);
        return this;
    }

    public void addRow(ArrayList<Object> row) throws IllegalArgumentException {
        if (row.size() != headerLengths.size()) {
            throw new IllegalArgumentException("Row size must match header size");
        }
        ArrayList<String> rowList = new ArrayList<>();
        for (Object column : row) {
            String value = "";
            if (column instanceof String) {
                value = ((String) column);
            } else if (column != null) {
                value = column.toString();
            }
            rowList.add(value);
        }
        System.out.println("rowList = " + rowList);
        rows.add(rowList);
    }

    public void addRow(Object... row) throws IllegalArgumentException {
        addRow(new ArrayList<>(Arrays.asList(row)));
    }

    public void setRows(ArrayList<ArrayList<Object>> rows) {
        this.rows.clear();
        for (ArrayList<Object> row : rows) {
            addRow(row);
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (headers.size() != 0) {
            builder.append(getSeparatorString('+'));
            builder.append(getHeaderString());
            builder.append(getSeparatorString('|'));
        } else {
            builder.append(getSeparatorString('+'));
        }
        boolean first = true;
        for (ArrayList<String> row : rows) {
            if (!first) {
                builder.append(getSeparatorString('|'));
            } else {
                first = false;
            }
            builder.append(getRowString(row));
        }
        builder.append(getSeparatorString('+'));
        return builder.toString();
    }


    private String getSeparatorString(Character separator) {
        StringBuilder builder = new StringBuilder();
        builder.append(separator);
        for (int width : headerLengths) {
            builder.append(String.format(" %-" + width + "s ", "").replace(' ', '-'));
            builder.append(separator);
        }
        builder.append("\n");
        return builder.toString();
    }

    private String getRowString(ArrayList<String> row) {
        int lineCount = calculateLineCount(row);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lineCount; i++) {
            builder.append("|");
            for (int j = 0; j < headerLengths.size(); j++) {
                String[] lines = row.get(j).split("(?<=\\G.{" + headerLengths.get(j) + "})");
                String    line = i < lines.length ? lines[i] : "";
                builder.append(String.format(" %-" + headerLengths.get(j) + "s", line));
                builder.append(" |");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    private String getHeaderString() {
        StringBuilder builder = new StringBuilder();
        builder.append("|");
        for (int i = 0; i < headers.size(); i++) {
            builder.append(String.format(" %-" + headerLengths.get(i) + "s", headers.get(i)));
            builder.append(" |");
        }
        builder.append("\n");
        return builder.toString();
    }

    private int calculateLineCount(ArrayList<String> row) {
        int lineCount = 1;

        for (int i = 0; i < headerLengths.size(); i++) {
            String[] lines = row.get(i).split("(?<=\\G.{" + headerLengths.get(i) + "})");
            if (lines.length > lineCount) {
                lineCount = lines.length;
            }
        }
        return lineCount;
    }
}
