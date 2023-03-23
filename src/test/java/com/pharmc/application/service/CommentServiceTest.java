package com.pharmc.application.service;

import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.infrastructure.persistence.JsonCommentRepository;
import com.pharmc.infrastructure.persistence.JsonDB;
import junit.framework.TestCase;

import java.util.ArrayList;

public class CommentServiceTest extends TestCase {
    private CommentService service;
    private JsonCommentRepository repository;

    public void setUp() throws Exception {
        super.setUp();
        repository = new JsonCommentRepository(new JsonDB(System.getProperty("user.dir") + "/src/test/resources/db.json"));
        service    = new CommentService(repository);

        repository.deleteAll();
    }

    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    public void testSave() {
        CommentEntity comment = service.save(new CommentEntity(1, "comment1"));
        CommentEntity commentSaved = service.readById(comment.getId());

        assertEquals(comment.getId(), commentSaved.getId());
        assertEquals(comment.getDrugId(), commentSaved.getDrugId());
        assertEquals(comment.getText(), commentSaved.getText());

        repository.deleteAll();
    }

    public void testDeleteComment() {
        CommentEntity comment1 = service.save(new CommentEntity(1, "comment1"));
        CommentEntity comment2 = service.save(new CommentEntity(1, "comment2"));

        assertEquals(2, service.readAll().size());
        service.delete(comment1.getId());
        assertEquals(1, service.readAll().size());
        service.delete(comment2);
        assertEquals(0, service.readAll().size());

        repository.deleteAll();
    }

    public void testDeleteCommentNotFound() {
        CommentEntity comment = service.save(new CommentEntity(1, "comment1"));
        assertEquals(1, service.readAll().size());

        service.delete(666);
        service.delete(new CommentEntity(666, "comment666"));
        assertEquals(1, service.readAll().size());

        repository.deleteAll();
    }

    public void testReadComments() {
        service.save(new CommentEntity(1, "comment1"));
        service.save(new CommentEntity(2, "comment2"));

        assertEquals(2, service.readAll().size());

        repository.deleteAll();
    }

    public void testReadCommentsNotFound() {
        assertEquals(0, service.readAll().size());
    }

    public void testReadCommentById() {
        CommentEntity comment1 = service.save(new CommentEntity(1, "comment1"));
        CommentEntity comment2 = service.save(new CommentEntity(2, "comment2"));

        assertEquals(comment1.getId(), service.readById(comment1.getId()).getId());
        assertEquals(comment2.getId(), service.readById(comment2.getId()).getId());

        repository.deleteAll();
    }

    public void testReadCommentsByIdNotFound() {
        CommentEntity comment = service.readById(666);
        assertNull(comment);
    }

    public void testReadCommentsByDrugId() {
        service.save(new CommentEntity(1, "comment1"));
        service.save(new CommentEntity(1, "comment2"));
        service.save(new CommentEntity(2, "comment3"));

        assertEquals(2, service.readByDrugId(1).size());
        assertEquals(1, service.readByDrugId(2).size());

        repository.deleteAll();
    }

    public void testReadCommentsByDrugIdNotFound() {
        ArrayList<CommentEntity> comment = service.readByDrugId(666);
        assertEquals(0, comment.size());
    }
}