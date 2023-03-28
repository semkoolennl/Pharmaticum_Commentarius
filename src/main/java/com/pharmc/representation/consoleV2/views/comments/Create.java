package com.pharmc.representation.consoleV2.views.comments;

import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.domain.repositories.CommentRepositoryInterface;
import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.views.AbstractView;

public class Create extends AbstractView {
    private CommentRepositoryInterface repository;

    public Create(Container container) {
        super(container, "/comments/create");
    }

    @Override
    public void render(Object... args) {
        terminal.resetToBookmark("");
        terminal.println("Create new comment\n");
        terminal.setBookmark("commentcreate");

        int    drugid = (int) args[0];
        String text   = utils.stringReader().withBookmark("commentcreate").withMaxLength(100).read("Text: ");
        CommentEntity comment = new CommentEntity(drugid, text);

        boolean confirm = utils.confirm().read("Do you want to create this comment?");
        if (confirm) {
            repository.save(comment);
        }
    }
}
