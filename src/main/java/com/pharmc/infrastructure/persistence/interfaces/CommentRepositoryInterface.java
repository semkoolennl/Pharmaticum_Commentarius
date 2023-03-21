package com.pharmc.infrastructure.persistence.interfaces;

import com.pharmc.domain.entity.CommentEntity;

import java.util.ArrayList;

public interface CommentRepositoryInterface {
    ArrayList<CommentEntity> findAll();
    CommentEntity findById(String id);
    ArrayList<CommentEntity> findByDrugId(String drugId);
    void save(CommentEntity comment);
    void delete(String id);
    void deleteAll();
}
