package com.pharmc.representation.consoleV2.views.drugs;

import com.pharmc.application.service.interfaces.CommentServiceInterface;
import com.pharmc.application.service.interfaces.DrugServiceInterface;
import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.domain.entity.DrugEntity;
import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.utils.textmodels.TableTextModel;
import com.pharmc.representation.consoleV2.views.AbstractView;

import javax.xml.stream.events.Comment;
import java.awt.*;
import java.util.ArrayList;

public class View extends AbstractView {
    private DrugServiceInterface drugService;
    private CommentServiceInterface commentService;

    public View(Container container) {
        super(container, "/drugs/view");
        drugService    = container.resolve(DrugServiceInterface.class);
        commentService = container.resolve(CommentServiceInterface.class);
    }

    @Override
    public void render(Object... args) {
        terminal.resetToBookmark("");
        terminal.println("View drug\n");

        DrugEntity drug = drugService.readById((int) args[0]);
        ArrayList<CommentEntity> comments = commentService.readByDrugId(drug.getId());
        showDrug(drug, comments);
        showMenu(drug, comments);
    }

    private void showDrug(DrugEntity drug, ArrayList<CommentEntity> comments) {
        TableTextModel table = new TableTextModel();
        table.setHeaderLengths(12, 40);
        table.addRow("ID", drug.getId());
        table.addRow("Name", drug.getName());
        table.addRow("Description", drug.getDescription());
        table.addRow("Comments:", "");
        for (CommentEntity comment : comments) {
            String id = String.format("%9s%3d", "#", comment.getId());
            table.addRow(id, comment.getText());
        }

        printer.setDefaultColor(Color.WHITE);
        printer.setBorderColor(Color.BLUE, "+-|");
        printer.print(table);

        terminal.setBookmark("drugdisplay");
    }

    private void showMenu(DrugEntity drug, ArrayList<CommentEntity> comments) {
        printer.setDelay(1);
        Runnable selected = utils.selectMenu().withBookmark("drugdisplay").withPrinter(printer)
                .addOption("EDIT", () -> navigation.redirect("/drugs/edit", drug.getId()))
                .addOption("ADD_COMMENT", () -> addComment(drug))
                .addOption("EDIT_COMMENT", () -> editComment(comments))
                .addOption("DELETE_COMMENT", () -> deleteComment(comments))
                .addOption("BACK", () -> navigation.back())
                .read();

        printer.setDelay(0);
        terminal.setBookmark("menu");
        selected.run();
    }

    private void addComment(DrugEntity drug) {
        String text = utils.stringReader().withBookmark("menu").read("Enter the comment:");
        terminal.setBookmark("addcomment");
        CommentEntity comment = new CommentEntity(drug.getId(), text);

        boolean confirm = utils.confirm().withBookmark("addcomment").read("Are you sure you want to add this comment? (y/n):");
        if (confirm) {
            commentService.save(comment);
        }
    }

    private void editComment(ArrayList<CommentEntity> comments) {
        int commentId = getCommentId(comments, "Enter the ID of the comment to edit:");
        navigation.redirect("/drugs/edit_comment", commentId);
    }

    private void deleteComment(ArrayList<CommentEntity> comments) {
        int commentId = getCommentId(comments, "Enter the ID of the comment to delete:");
        terminal.setBookmark("deletecomment");

        boolean confirm = utils.confirm().withBookmark("deletecomment").read("Are you sure you want to delete this comment? (y/n):");
        if (confirm) {
            commentService.delete(commentId);
        }
    }

    private int getCommentId(ArrayList<CommentEntity> comments, String prompt) {
        int[] ids = comments.stream().mapToInt(CommentEntity::getId).toArray();
        return utils.intReader().withBookmark("menu").withValidValues(ids).read(prompt);
    }
}
