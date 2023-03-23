package com.pharmc.representation.console;

import com.pharmc.application.Container;
import com.pharmc.application.service.interfaces.TimeblockServiceInterface;
import com.pharmc.domain.entity.TimeblockEntity;

import java.util.ArrayList;

public class TimelineUI extends AbstractUI {
    private TimeblockServiceInterface service;
    public enum EditForDrugMenu {
        ADD,
        EDIT,
        DELETE,
        BACK
    }
    public enum EditTimeblockMenu {
        EDIT_DESCRIPTION,
        EDIT_DURATION,
        BACK
    }
    public TimelineUI(Container container) {
        super(container);
        this.service = container.resolve(TimeblockServiceInterface.class);
    }

    public void editTimeblocksForDrug(int drugId) {
        textio.getTextTerminal().resetToBookmark("start");
        textio.getTextTerminal().printf("Edit Timeline");
        textio.getTextTerminal().println("==================================");
        ArrayList<TimeblockEntity> timeblocks = service.readByDrugId(drugId);
        showTimeblocks(timeblocks);

        EditForDrugMenu menu = textio.newEnumInputReader(EditForDrugMenu.class)
                .read("\nSelect an action");
        switch (menu) {
            case ADD:
                textio.getTextTerminal().resetToBookmark("start");
                textio.getTextTerminal().println("Create timeblock");
                textio.getTextTerminal().println("==================================\n");
                service.save(new TimeblockEntity(
                        drugId,
                        textio.newStringInputReader().withMinLength(1).read("Enter timeblock description"),
                        textio.newIntInputReader().withMinVal(1).read("Enter timeblock duration")
                    )
                );
                break;
            case EDIT:
                int timeblockId = textio.newIntInputReader().withMinVal(0).withMaxVal(timeblocks.size()-1).read("Enter timeblock ID");
                editTimeblock(timeblocks.get(timeblockId));
                break;
            case DELETE:
                timeblockId = textio.newIntInputReader().withMinVal(0).withMaxVal(timeblocks.size()-1).read("Enter timeblock ID");
                service.delete(timeblocks.get(timeblockId));
                break;
            case BACK:
                return;
        }

        editTimeblocksForDrug(drugId);
    }

    private void editTimeblock(TimeblockEntity timeblock) {
        textio.getTextTerminal().resetToBookmark("start");
        textio.getTextTerminal().println("Edit timeblock");
        textio.getTextTerminal().println("==================================");
        showTimeblock(timeblock);

        EditTimeblockMenu menu = textio.newEnumInputReader(EditTimeblockMenu.class)
                .read("\nSelect an action");
        switch (menu) {
            case EDIT_DESCRIPTION:
                timeblock.setDescription(textio.newStringInputReader().withMinLength(1).read("Enter timeblock description"));
                break;
            case EDIT_DURATION:
                timeblock.setDuration(textio.newIntInputReader().withMinVal(1).read("Enter timeblock duration"));
                break;
            case BACK:
                return;
        }

        service.save(timeblock);
        editTimeblock(timeblock);
    }

    private void showTimeblock(TimeblockEntity timeblock) {
        textio.getTextTerminal().println("Timeblock:");
        textio.getTextTerminal().printf("  - %-12s: %s\n", "ID", timeblock.getId());
        textio.getTextTerminal().printf("  - %-12s: %s\n", "Description", timeblock.getDescription());
        textio.getTextTerminal().printf("  - %-12s: %s\n", "Duration", timeblock.getDuration());
    }

    public void showTimeblocksByDrugId(int drugId) {
        ArrayList<TimeblockEntity> timeblocks = service.readByDrugId(drugId);
        showTimeblocks(timeblocks);
    }

    private void showTimeblocks(ArrayList<TimeblockEntity> timeblocks) {
        if (timeblocks.size() > 0) {
            for (int i = 0; i < timeblocks.size(); i++) {
                textio.getTextTerminal().printf("  - %-12s: %s\n", "ID", i);
                textio.getTextTerminal().printf("  - %-12s: %s\n", "Description", timeblocks.get(i).getDescription());
                textio.getTextTerminal().printf("  - %-12s: %s\n", "Duration", timeblocks.get(i).getDuration());
                textio.getTextTerminal().println();
            }
        }
    }
}
