package com.pharmc.infrastructure.persistence;

import com.pharmc.domain.repositories.DrugRepositoryInterface;
import com.pharmc.domain.entity.DrugEntity;

public class JsonDrugRepository extends AbstractJsonRepository<DrugEntity> implements DrugRepositoryInterface {
    public JsonDrugRepository(JsonDB jsonDB) {
        super(jsonDB, DrugEntity.class);
    }
}
