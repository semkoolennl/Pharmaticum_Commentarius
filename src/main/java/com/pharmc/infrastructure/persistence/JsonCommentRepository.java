package com.pharmc.infrastructure.persistence;

import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.infrastructure.persistence.interfaces.CommentRepositoryInterface;
import com.pharmc.infrastructure.persistence.interfaces.JsonRepositoryInterface;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonCommentRepository implements CommentRepositoryInterface {
    private JsonRepositoryInterface jsonRepository;
    private HashMap<String, CommentEntity> comments = new HashMap<>();

    public JsonCommentRepository(String filepath) {
        jsonRepository = new JsonRepository(filepath, CommentEntity.class);

        ArrayList<CommentEntity> data = jsonRepository.load();
        for (CommentEntity comment : data) {
            comments.put(comment.getId(), comment);
        }
    }

    public JsonCommentRepository() {
        this(System.getProperty("user.dir") + "/src/main/resources/comments.json");
    }

    public ArrayList<CommentEntity> findAll() {
        return new ArrayList<>(comments.values());
    }

    public CommentEntity findById(String id) {
        return comments.get(id);
    }

    @Override
    public ArrayList<CommentEntity> findByDrugId(String drugId) {
        ArrayList<CommentEntity> result = new ArrayList<>();

        if (comments.isEmpty()) {
            return result;
        }

        for (CommentEntity comment : comments.values()) {
            if (comment.getDrugId().equals(drugId)) {
                result.add(comment);
            }
        }
        return result;
    }

    @Override
    public void save(CommentEntity comment) {
        comments.put(comment.getId(), comment);
        jsonRepository.save(new ArrayList<>(comments.values()));
    }

    @Override
    public void delete(String id) {
        comments.remove(id);
        jsonRepository.save(new ArrayList<>(comments.values()));
    }

    @Override
    public void deleteAll() {
        comments.clear();
        jsonRepository.save(new ArrayList<>(comments.values()));
    }
}
