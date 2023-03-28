package com.pharmc.domain.repositories;

import com.pharmc.domain.entity.CommentEntity;

import java.util.ArrayList;

public interface CommentRepositoryInterface extends AbstractRepositoryInterface<CommentEntity> {
    ArrayList<CommentEntity> findByDrugId(int drugId);
}
