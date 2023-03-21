package com.pharmc.application.service;

import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.infrastructure.persistence.JsonCommentRepository;
import junit.framework.TestCase;

import java.util.ArrayList;

public class CommentServiceTest extends TestCase {
    private CommentService service;
    private JsonCommentRepository repository;

    public void setUp() throws Exception {
        super.setUp();
        repository = new JsonCommentRepository(System.getProperty("user.dir") + "/src/test/resources/comments.json");
        service    = new CommentService(repository);

        repository.deleteAll();
    }

    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    public void testCreateComment() {
        CommentEntity comment = service.createComment("testdrug1", "testdescription1");
        CommentEntity commentSaved = service.readCommentById(comment.getId());

        assertEquals(comment.getId(), commentSaved.getId());
        assertEquals(comment.getDrugId(), commentSaved.getDrugId());
        assertEquals(comment.getText(), commentSaved.getText());

        repository.deleteAll();
    }

    public void testUpdateComment() {
        CommentEntity comment = service.createComment("testdrug1", "testdescription1");
        CommentEntity changed = service.updateComment(comment.getId(), "testdescription2");

        assertEquals(comment.getId(), changed.getId());
        assertEquals(comment.getDrugId(), changed.getDrugId());
        assertEquals("testdescription2", changed.getText());

        repository.deleteAll();
    }

    public void testUpdateCommentNotFound() {
        CommentEntity comment = service.updateComment("testid", "testdescription2");
        assertNull(comment);
    }

    public void testDeleteComment() {
        CommentEntity comment1 = service.createComment("drugid1", "comment1");
        CommentEntity comment2 = service.createComment("drugid2", "comment2");

        assertEquals(2, service.readComments().size());
        service.deleteComment(comment1.getId());
        assertEquals(1, service.readComments().size());
        service.deleteComment(comment2.getId());
        assertEquals(0, service.readComments().size());

        repository.deleteAll();
    }

    public void testDeleteCommentNotFound() {
        service.createComment("drugid1", "comment1");
        assertEquals(1, service.readComments().size());

        service.deleteComment("testid");
        assertEquals(1, service.readComments().size());

        repository.deleteAll();
    }

    public void testReadComments() {
        CommentEntity comment1 = service.createComment("drugid1", "comment1");
        CommentEntity comment2 = service.createComment("drugid2", "comment2");

        assertEquals(2, service.readComments().size());

        repository.deleteAll();
    }

    public void testReadCommentsNotFound() {
        assertEquals(0, service.readComments().size());
    }

    public void testReadCommentById() {
        CommentEntity comment1 = service.createComment("drugid1", "comment1");
        CommentEntity comment2 = service.createComment("drugid2", "comment2");

        assertEquals(comment1.getId(), service.readCommentById(comment1.getId()).getId());
        assertEquals(comment2.getId(), service.readCommentById(comment2.getId()).getId());

        repository.deleteAll();
    }

    public void testReadCommentsByIdNotFound() {
        CommentEntity comment = service.readCommentById("testid");
        assertNull(comment);
    }

    public void testReadCommentsByDrugId() {
        CommentEntity comment1 = service.createComment("drugid1", "comment1");
        CommentEntity comment2 = service.createComment("drugid2", "comment2");
        CommentEntity comment3 = service.createComment("drugid1", "comment3");

        assertEquals(2, service.readCommentsByDrugId("drugid1").size());
        assertEquals(1, service.readCommentsByDrugId("drugid2").size());

        repository.deleteAll();
    }

    public void testReadCommentsByDrugIdNotFound() {
        ArrayList<CommentEntity> comment = service.readCommentsByDrugId("testid");
        assertEquals(0, comment.size());
    }
}