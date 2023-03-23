package com.pharmc.infrastructure.persistence;

import com.pharmc.domain.entity.CommentEntity;
import junit.framework.TestCase;

import java.util.ArrayList;

public class JsonCommentRepositoryTest extends TestCase {
    private JsonCommentRepository repository;

    public void setUp() throws Exception {
        super.setUp();
        repository = new JsonCommentRepository(new JsonDB(System.getProperty("user.dir") + "/src/test/resources/db.json"));
        repository.deleteAll();
        repository.save(new CommentEntity(1, "comment 1"));
        repository.save(new CommentEntity(2, "comment 2"));
    }

    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    public void testFindAll() {
        ArrayList<CommentEntity> comments = repository.findAll();
        assertEquals(2, comments.size());
    }

    public void testFindById() {
        ArrayList<CommentEntity> comments = repository.findAll();
        for (CommentEntity comment : comments) {
            CommentEntity foundComment = repository.findById(comment.getId());
            assertNotNull(foundComment);
        }
    }

    public void testFindByIdNotFound() {
        CommentEntity comment = repository.findById(666);
        assertNull(comment);
    }

    public void testFindByDrugId() {
        ArrayList<CommentEntity> comments = repository.findAll();
        for (CommentEntity comment : comments) {
            ArrayList<CommentEntity> foundComments = repository.findByDrugId(comment.getDrugId());
            assertNotNull(foundComments);
        }
    }

    public void testFindByDrugIdNotFound() {
        ArrayList<CommentEntity> comments = repository.findByDrugId(666);
        assertEquals(0, comments.size());
    }

    public void testSave() {
        CommentEntity comment = new CommentEntity(3, "comment 3");
        repository.save(comment);

        repository.findById(comment.getId());
        assertNotNull(comment);
        assertEquals("comment 3", comment.getText());
    }

    public void testSaveNoDuplicate() {
        CommentEntity comment = new CommentEntity(3, "comment 3");
        repository.save(comment);
        repository.save(comment);

        ArrayList<CommentEntity> comments = repository.findAll();
        assertEquals(3, comments.size());
    }

    public void testDelete() {
        ArrayList<CommentEntity> comments = repository.findAll();
        CommentEntity comment = comments.get(comments.size()-1);

        repository.delete(comment.getId());
        CommentEntity foundComment = repository.findById(comment.getId());
        assertNull(foundComment);
    }

    public void testDeleteAll() {
        repository.deleteAll();
        ArrayList<CommentEntity> comments = repository.findAll();
        assertEquals(0, comments.size());
    }
}