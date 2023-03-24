package com.pharmc.representation.consoleV2.views.drugs;

import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.views.AbstractView;

public class DrugsIndex extends AbstractView {
    public DrugsIndex(Container container) {
        super(container, "/drugs/index");
    }

    @Override
    public void render() {
        terminal.resetToBookmark("");

        terminal.println("Drugs index\n");

        boolean back = textio.newBooleanInputReader()
                .withDefaultValue(false)
                .read("Do you want to go back?");
        if (back) {
            navigation.back();
        }
    }
}
