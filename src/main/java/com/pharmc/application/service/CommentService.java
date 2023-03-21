package com.pharmc.application.service;

import com.pharmc.application.service.interfaces.CommentServiceInterface;
import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.infrastructure.persistence.JsonCommentRepository;
import com.pharmc.infrastructure.persistence.interfaces.CommentRepositoryInterface;

import java.util.ArrayList;

public class CommentService implements CommentServiceInterface {
    private CommentRepositoryInterface repository;
    public CommentService(CommentRepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public CommentEntity createComment(String drugId, String text) {
        CommentEntity comment = new CommentEntity(drugId, text);
        repository.save(comment);

        return comment;
    }

    @Override
    public CommentEntity updateComment(String id, String text) {
        CommentEntity comment = repository.findById(id);

        if (comment == null) {
            return null;
        }

        comment.setText(text);
        repository.save(comment);

        return comment;
    }

    @Override
    public void deleteComment(String id) {
        repository.delete(id);
    }

    @Override
    public ArrayList<CommentEntity> readComments() {
        return repository.findAll();
    }

    @Override
    public CommentEntity readCommentById(String id) {
        return repository.findById(id);
    }

    public ArrayList<CommentEntity> readCommentsByDrugId(String id) {
        return repository.findByDrugId(id);
    }
}
