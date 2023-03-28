package com.pharmc.representation.consoleV2.utils;

import com.pharmc.representation.consoleV2.utils.inputreader.Confirmer;
import com.pharmc.representation.consoleV2.utils.inputreader.IntReader;
import com.pharmc.representation.consoleV2.utils.inputreader.SelectMenu;
import com.pharmc.representation.consoleV2.utils.inputreader.StringReader;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import java.util.Map;

public class TextIOUtils {
    private TextIO textio;
    private TextTerminal<?> terminal;

    public TextIOUtils(TextIO textio) {
        this.textio       = textio;
        this.terminal     = textio.getTextTerminal();
    }

    public SelectMenu selectMenu(Map<String, Runnable> options) {
        return new SelectMenu(textio).withOptions(options);
    }

    public SelectMenu selectMenu() {
        return new SelectMenu(textio);
    }

    public StringReader stringReader() {
        return new StringReader(textio);
    }

    public IntReader intReader() {
        return new IntReader(textio);
    }

    public Confirmer confirm() {
        return new Confirmer(textio);
    }
}
