package com.pharmc.representation.consoleV2.views.comments;

import com.pharmc.application.service.interfaces.CommentServiceInterface;
import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.utils.textmodels.TableTextModel;
import com.pharmc.representation.consoleV2.views.AbstractView;

import java.awt.*;
import java.util.ArrayList;

public class Index extends AbstractView {
    private CommentServiceInterface service;

    public Index(Container container) {
        super(container, "/comments/index");
        service = container.resolve(CommentServiceInterface.class);
    }

    @Override
    public void render(Object... args) {
        terminal.resetToBookmark("");
        terminal.println("Show comment\n");

        ArrayList<CommentEntity> comments = service.readByDrugId((int) args[0]);
        showComments(comments);
        terminal.setBookmark("comments");

        showMenu(comments);
    }

    private void showMenu(ArrayList<CommentEntity> comments) {
        int[] commentIds = comments.stream().mapToInt(CommentEntity::getId).toArray();

        Runnable selected = utils.selectMenu().withBookmark("comments")
                .addOption("Create", () -> navigation.redirect("/comments/create", comments.get(0).getDrugId()))
                .addOption("Edit",   () -> edit(commentIds))
                .addOption("Delete", () -> delete(commentIds))
                .addOption("Back",   () -> navigation.back())
                .read();

        terminal.println();
        terminal.setBookmark("menu");
        selected.run();
    }

    private void edit(int[] commentIds) {
        int id = utils.intReader().withBookmark("menu").withValidValues(commentIds).read("Enter the ID of the comment to edit: ");
        navigation.redirect("/comments/edit", id);
    }

    private void delete(int[] commentIds) {
        int id = utils.intReader().withBookmark("menu").withValidValues(commentIds).read("Enter the ID of the comment to delete: ");

        terminal.setBookmark("confirm");
        if (utils.confirm().withBookmark("confirm").read("Are you sure you want to delete this comment?")) {
            service.delete(id);
        }
    }

    private void showComments(ArrayList<CommentEntity> comments) {
        TableTextModel table = new TableTextModel();
        table.setHeaders("ID", "Text", "Date");
        table.setHeaderLengths(5, 60, 10);

        for (CommentEntity comment : comments) {
            table.addRow(comment.getId(), comment.getText(), comment.getDate());
        }

        printer.setDefaultColor(Color.WHITE);
        printer.setBorderColor(Color.BLUE, "-+|");
        printer.print(table);
    }
}
