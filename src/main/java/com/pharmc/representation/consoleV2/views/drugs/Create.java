package com.pharmc.representation.consoleV2.views.drugs;

import com.pharmc.application.service.interfaces.DrugServiceInterface;
import com.pharmc.domain.entity.DrugEntity;
import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.views.AbstractView;

public class Create extends AbstractView {
    private DrugServiceInterface drugService;

    public Create(Container container) {
        super(container, "/drugs/create");
        drugService = container.resolve(DrugServiceInterface.class);
    }

    @Override
    public void render(Object... args) {
        terminal.resetToBookmark("");
        terminal.println("Create new drug\n");
        terminal.setBookmark("drugcreate");

        String name = textio.newStringInputReader().read("Name");
        String description = textio.newStringInputReader().read("Description");

        boolean confirm = textio.newBooleanInputReader()
                .withDefaultValue(false)
                .read("Do you want to create this drug?");
        if (confirm) {
            DrugEntity drug = new DrugEntity(name, description);
            drugService.save(drug);
        }
        navigation.back();
    }
}
