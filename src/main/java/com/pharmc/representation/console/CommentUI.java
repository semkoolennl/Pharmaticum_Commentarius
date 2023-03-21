package com.pharmc.representation.console;

import com.pharmc.application.Container;
import com.pharmc.application.service.interfaces.CommentServiceInterface;
import com.pharmc.domain.entity.CommentEntity;

import java.util.ArrayList;

public class CommentUI extends AbstractUI {
    private CommentServiceInterface service;
    public enum EditForDrugMenu {
        ADD,
        EDIT,
        DELETE,
        BACK
    }
    public enum EditCommentMenu {
        EDIT_TEXT,
        BACK
    }

    public CommentUI(Container container) {
        super(container);
        this.service = container.resolve(CommentServiceInterface.class);
    }

    public void editCommentsForDrug(String drugId) {
        textio.getTextTerminal().resetToBookmark("start");
        textio.getTextTerminal().printf("Edit Comments");
        textio.getTextTerminal().println("==================================");
        ArrayList<CommentEntity> comments = service.readCommentsByDrugId(drugId);
        showComments(comments);

        EditForDrugMenu menu = textio.newEnumInputReader(EditForDrugMenu.class)
                .read("\nSelect an action");
        switch (menu) {
            case ADD:
                service.createComment(drugId, textio.newStringInputReader().withMinLength(1).read("Enter comment text"));
                break;
            case EDIT:
                int commentId = textio.newIntInputReader().withMinVal(0).withMaxVal(comments.size()-1).read("Enter comment ID");
                editComment(comments.get(commentId).getId());
                break;
            case DELETE:
                commentId = textio.newIntInputReader().withMinVal(0).withMaxVal(comments.size()-1).read("Enter comment ID");
                service.deleteComment(comments.get(commentId).getId());
                break;
            case BACK:
                return;
        }

        editCommentsForDrug(drugId);
    }

    public void editComment(String commentId) {
        textio.getTextTerminal().resetToBookmark("start");
        textio.getTextTerminal().println("Edit comment");
        textio.getTextTerminal().println("==================================");
        CommentEntity comment = service.readCommentById(commentId);

        textio.getTextTerminal().printf("  - %-12s: %s\n", "ID", comment.getId());
        textio.getTextTerminal().printf("  - %-12s: %s\n", "Text", comment.getText());
        textio.getTextTerminal().printf("  - %-12s: %s\n", "Date", comment.getDate());

        EditCommentMenu menu = textio.newEnumInputReader(EditCommentMenu.class)
                .read("\nSelect an action");
        switch (menu) {
            case EDIT_TEXT:
                service.updateComment(commentId, textio.newStringInputReader().withMinLength(1).read("Enter comment text"));
                break;
            case BACK:
                return;
        }

        editComment(commentId);
    }

    public void showCommentsByDrugId(String drugId) {
        textio.getTextTerminal().resetToBookmark("start");
        textio.getTextTerminal().println("Comments:");
        ArrayList<CommentEntity> comments = service.readCommentsByDrugId(drugId);
        showComments(comments);
    }

    private void showComments(ArrayList<CommentEntity> comments) {
        if (comments.size() > 0) {
            for (int i = 0; i < comments.size(); i++) {
                textio.getTextTerminal().printf("  - %-12s: %s\n", "ID", i);
                textio.getTextTerminal().printf("  - %-12s: %s\n", "Text", comments.get(i).getText());
                textio.getTextTerminal().printf("  - %-12s: %s\n", "Date", comments.get(i).getDate());
                textio.getTextTerminal().println();
            }
        }
    }
}
