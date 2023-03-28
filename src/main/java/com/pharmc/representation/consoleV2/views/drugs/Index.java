package com.pharmc.representation.consoleV2.views.drugs;

import com.pharmc.application.service.interfaces.DrugServiceInterface;
import com.pharmc.domain.entity.DrugEntity;
import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.utils.textmodels.TableTextModel;
import com.pharmc.representation.consoleV2.views.AbstractView;

import java.awt.*;
import java.util.ArrayList;

public class Index extends AbstractView {
    private DrugServiceInterface drugService;

    public Index(Container container) {
        super(container, "/drugs/index");

        drugService = container.resolve(DrugServiceInterface.class);
    }

    @Override
    public void render(Object... args) {
        terminal.resetToBookmark("");
        terminal.println("Drugs index");

        ArrayList<DrugEntity> drugs = drugService.readAll();
        showDrugs(drugs);

        showMenu(drugs);
    }

    private void showMenu(ArrayList<DrugEntity> drugs) {
        int[] drugIds = drugs.stream().mapToInt(DrugEntity::getId).toArray();

        printer.setDelay(1);
        Runnable selected = utils.selectMenu().withBookmark("drugsdisplay").withPrinter(printer)
                .addOption("View",   () -> view(drugIds))
                .addOption("Create", () -> navigation.redirect("/drugs/create"))
                .addOption("Edit",   () -> edit(drugIds))
                .addOption("Delete", () -> delete(drugIds))
                .addOption("Back",   () -> navigation.back())
                .read();

        printer.setDelay(0);
        terminal.setBookmark("menu");
        selected.run();
    }

    private void view(int[] drugIds) {
        int drugId = getDrugId(drugIds, "Enter the ID of the drug to view:");
        navigation.redirect("/drugs/view", drugId);
    }

    private void edit(int[] drugIds) {
        int drugId = getDrugId(drugIds, "Enter the ID of the drug to edit:");
        navigation.redirect("/drugs/edit", drugId);
    }

    private void delete(int[] drugIds) {
        int drugId = getDrugId(drugIds, "Enter the ID of the drug to delete:");

        terminal.setBookmark("confirm");
        if (utils.confirm().withBookmark("confirm").read("Are you sure you want to delete this drug? (y/n):")) {
            drugService.delete(drugId);
        }
    }

    private void showDrugs(ArrayList<DrugEntity> drugs) {
        TableTextModel table = new TableTextModel();

        table.setHeaders("ID", "Name", "Description");
        table.setHeaderLengths(5, 20, 50);

        for (DrugEntity drug : drugs) {
            table.addRow(drug.getId(), drug.getName(), drug.getDescription());
        }

        printer.setDefaultColor(Color.WHITE);
        printer.setBorderColor(Color.BLUE, "-+|");
        printer.print(table);

        terminal.println();
        terminal.setBookmark("drugsdisplay");
    }

    private int getDrugId(int[] drugIds, String prompt) {
        return utils.intReader().withBookmark("menu").withValidValues(drugIds).read(prompt);
    }
}
