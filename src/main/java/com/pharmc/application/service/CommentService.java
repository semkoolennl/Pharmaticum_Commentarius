package com.pharmc.application.service;

import com.pharmc.application.service.interfaces.CommentServiceInterface;
import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.infrastructure.persistence.interfaces.CommentRepositoryInterface;

import java.util.ArrayList;

public class CommentService extends AbstractEntityService<CommentEntity> implements CommentServiceInterface {
    CommentRepositoryInterface repository;
    public CommentService(CommentRepositoryInterface repository) {
        super(repository);
        this.repository = repository;
    }

    public ArrayList<CommentEntity> readByDrugId(int id) {
        return repository.findByDrugId(id);
    }
}
