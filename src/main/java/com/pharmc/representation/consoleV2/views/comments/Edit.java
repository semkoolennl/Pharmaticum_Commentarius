package com.pharmc.representation.consoleV2.views.comments;

import com.pharmc.application.service.interfaces.CommentServiceInterface;
import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.utils.textmodels.TableTextModel;
import com.pharmc.representation.consoleV2.views.AbstractView;

import java.awt.*;

public class Edit extends AbstractView {
    private CommentServiceInterface service;

    public Edit(Container container) {
        super(container, "/comments/edit");
        service = container.resolve(CommentServiceInterface.class);
    }

    @Override
    public void render(Object... args) {
        terminal.resetToBookmark("");
        terminal.println("Edit comment\n");

        CommentEntity comment = service.readById((int) args[0]);
        showComment(comment);
        showMenu(comment);
    }

    private void showMenu(CommentEntity comment) {
        Runnable selected = utils.selectMenu().withBookmark("commentdisplay")
                .addOption("EDIT_TEXT", () -> editText(comment))
                .addOption("BACK", () -> navigation.back())
                .read();

        terminal.println();
        terminal.setBookmark("menu");
        selected.run();
    }

    private void showComment(CommentEntity comment) {
        TableTextModel table = new TableTextModel();
        table.setHeaders("ID", "Text", "Date");
        table.setHeaderLengths(5, 60, 10);
        table.addRow(comment.getId(), comment.getText(), comment.getDate());

        printer.setDefaultColor(Color.WHITE);
        printer.setBorderColor(Color.BLUE, "+-|");
        printer.print(table);

        terminal.setBookmark("commentdisplay");
    }

    private void editText(CommentEntity comment) {
        comment.setText(utils.stringReader().withBookmark("menu").withMaxLength(100).read("Text: "));
        service.save(comment);
    }
}
