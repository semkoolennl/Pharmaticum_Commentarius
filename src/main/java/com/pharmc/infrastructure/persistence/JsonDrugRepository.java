package com.pharmc.infrastructure.persistence;

import com.pharmc.infrastructure.persistence.interfaces.DrugRepositoryInterface;
import com.pharmc.domain.entity.DrugEntity;

public class JsonDrugRepository extends AbstractJsonRepository<DrugEntity> implements DrugRepositoryInterface {
    public JsonDrugRepository(JsonDB jsonDB) {
        super(jsonDB, DrugEntity.class);
    }
}
