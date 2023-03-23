package com.pharmc.infrastructure.persistence;

import com.pharmc.domain.entity.TimeblockEntity;
import com.pharmc.infrastructure.persistence.interfaces.TimeblockRepositoryInterface;

import java.util.ArrayList;


public class JsonTimeblockRepository extends AbstractJsonRepository<TimeblockEntity> implements TimeblockRepositoryInterface {
    public JsonTimeblockRepository(JsonDB jsonDB) {
        super(jsonDB, TimeblockEntity.class);
    }

    @Override
    public ArrayList<TimeblockEntity> findByDrugId(int drugId) {
        return searchByField("drugId", drugId);
    }
}
