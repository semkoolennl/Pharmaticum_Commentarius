package com.pharmc.infrastructure.persistence;

import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.infrastructure.persistence.interfaces.CommentRepositoryInterface;

import java.util.ArrayList;

public class JsonCommentRepository extends AbstractJsonRepository<CommentEntity> implements CommentRepositoryInterface {
    public JsonCommentRepository(JsonDB jsonDB) {
        super(jsonDB, CommentEntity.class);
    }

    @Override
    public ArrayList<CommentEntity> findByDrugId(int drugId) {
        return this.searchByField("drugId", drugId);
    }
}
