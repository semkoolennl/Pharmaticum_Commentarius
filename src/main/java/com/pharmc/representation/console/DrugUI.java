package com.pharmc.representation.console;

import com.pharmc.application.service.interfaces.DrugServiceInterface;
import com.pharmc.domain.entity.DrugEntity;
import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.console.model.Table;

import java.util.ArrayList;

public class DrugUI extends AbstractUI
{
    private DrugServiceInterface service;

    private ArrayList<DrugEntity> drugs = new ArrayList<>();
    public enum DrugOverviewOptions { VIEW, ADD, EDIT, DELETE, BACK }
    public enum DrugEditOptions { NAME, DESCRIPTION, COMMENTS, TIMELINE, BACK }
    public enum CommentOptions { ADD, EDIT, DELETE, BACK }
    public enum TimeblockOptions { ADD, EDIT, DELETE, BACK }
    public enum DrugViewOptions { EDIT, BACK }
    private String message = "";

    public DrugUI(Container container)
    {
        super(container);
        this.service = container.resolve(DrugServiceInterface.class);
    }

    public void display()
    {
        drugs = service.readAll();

        textio.getTextTerminal().resetToBookmark("start");
        textio.getTextTerminal().printf("Drugs overview %s\n", !message.equals("") ? "- " + message : "");

        Table table = new Table()
                .addHeader("ID", 3)
                .addHeader("Name", 20)
                .addHeader("Description", 60);

        for (int i = 0; i < drugs.size(); i++) {
            DrugEntity drug = drugs.get(i);
            table.addRow(String.valueOf(i), drug.getName(), drug.getDescription());
        }

        textio.getTextTerminal().println(table.toString());

        DrugOverviewOptions selected = textio.newEnumInputReader(DrugOverviewOptions.class).read("\nSelect an option:");
        switch (selected) {
            case ADD:
                addDrug();
                break;
            case DELETE:
                deleteDrug(textio.newIntInputReader().withMinVal(0).withMaxVal(drugs.size() - 1).read("[DELETE] ID:"));
                break;
            case VIEW:
                viewDrug(textio.newIntInputReader().withMinVal(0).withMaxVal(drugs.size() - 1).read("[VIEW] ID:"));
                break;
            case EDIT:
                editDrug(textio.newIntInputReader().withMinVal(0).withMaxVal(drugs.size() - 1).read("[EDIT] ID:"));
                break;
            case BACK:
                return;
        }

        display();
    }

    private void viewDrug(int id)
    {
        showDrug(id, "View drug");

        DrugViewOptions selected = textio.newEnumInputReader(DrugViewOptions.class).read("\nSelect an option:");
        switch (selected) {
            case EDIT:
                editDrug(id);
                break;
            case BACK:
                return;
        }
        viewDrug(id);
    }

    private void addDrug()
    {
        textio.getTextTerminal().resetToBookmark("start");
        textio.getTextTerminal().println("Add drug");
        textio.getTextTerminal().println("============================");
        DrugEntity drug = new DrugEntity(
                textio.newStringInputReader().read("Name:"),
                textio.newStringInputReader().read("Description:")
        );
        service.save(drug);

        message = "Drug has been added";
    }

    private void editDrug(int id)
    {
        showDrug(id, "Edit drug");
        DrugEntity drug = service.readById(drugs.get(id).getId());

        DrugEditOptions selected = textio.newEnumInputReader(DrugEditOptions.class).read("\nSelect an option:");
        switch (selected) {
            case NAME:
                drug.setName(textio.newStringInputReader().read("Name:"));
            case DESCRIPTION:
                drug.setDescription(textio.newStringInputReader().read("Description:"));
            case COMMENTS:
                container.resolve(CommentUI.class).editCommentsForDrug(drug.getId());
                break;
            case TIMELINE:
                container.resolve(TimelineUI.class).editTimeblocksForDrug(drug.getId());
                break;
            case BACK:
                return;
        }

        service.save(drug);
        message = "Drug has been updated";
        editDrug(id);
    }

    private void deleteDrug(int id)
    {
        service.delete(drugs.get(id));
        message = "Drug has been deleted";
    }

    private void showDrug(int id, String message) {
        textio.getTextTerminal().resetToBookmark("start");
        textio.getTextTerminal().println(message);
        textio.getTextTerminal().println("==============================================");

        DrugEntity drug = service.readById(drugs.get(id).getId());

        if (drug == null) {
            this.message = "Drug not found";
            display();
            return;
        }

        textio.getTextTerminal().printf("%-12s: %s\n", "ID", id);
        textio.getTextTerminal().printf("%-12s: %s\n", "Name", drug.getName());
        textio.getTextTerminal().printf("%-12s: %s\n", "Description", drug.getDescription());

        textio.getTextTerminal().printf("%-12s: \n", "Comments");
        container.resolve(CommentUI.class).showCommentsByDrugId(drug.getId());
        textio.getTextTerminal().printf("%-12s: \n", "Timeblocks");
        container.resolve(TimelineUI.class).showTimeblocksByDrugId(drug.getId());
    }
}
