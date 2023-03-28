package com.pharmc.representation.consoleV2.views.drugs;

import com.pharmc.application.service.interfaces.DrugServiceInterface;
import com.pharmc.domain.entity.DrugEntity;
import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.utils.textmodels.TableTextModel;
import com.pharmc.representation.consoleV2.views.AbstractView;

import java.awt.*;

public class Edit extends AbstractView {
    private DrugServiceInterface drugService;

    public Edit(Container container) {
        super(container, "/drugs/edit");

        drugService = container.resolve(DrugServiceInterface.class);
    }

    @Override
    public void render(Object... args) {
        terminal.resetToBookmark("");
        terminal.println("Edit drug");
        terminal.setBookmark("drugheader");

        DrugEntity drug = drugService.readById((int) args[0]);
        showDrug(drug);
        showEditMenu(drug);
    }

    private void showDrug(DrugEntity drug) {
        terminal.resetToBookmark("drugheader");
        TableTextModel table = new TableTextModel();
        table.setHeaders("ID", "Name", "Description");
        table.setHeaderLengths(5, 20, 50);
        table.addRow(drug.getId(), drug.getName(), drug.getDescription());

        printer.setDefaultColor(Color.WHITE);
        printer.setBorderColor(Color.BLUE, "+-|");
        printer.print(table);
        terminal.setBookmark("drugdisplay");
    }

    private void showEditMenu(DrugEntity drug) {
        Runnable selected = utils.selectMenu().withBookmark("drugdisplay")
                .addOption("Name",        () -> editName(drug))
                .addOption("Description", () -> editDescription(drug))
                .addOption("Back",        () -> navigation.back())
                .read();

        terminal.println();
        terminal.setBookmark("menu");
        selected.run();
    }

    private void editName(DrugEntity drug) {
        drug.setName(utils.stringReader().withBookmark("menu").withMaxLength(60).read("Name: "));
        drugService.save(drug);
    }

    private void editDescription(DrugEntity drug) {
        drug.setDescription(utils.stringReader().withBookmark("menu").read("Description"));
        drugService.save(drug);
    }
}
