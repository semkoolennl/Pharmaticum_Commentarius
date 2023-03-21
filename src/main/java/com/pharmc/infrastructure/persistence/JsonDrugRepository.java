package com.pharmc.infrastructure.persistence;

import com.pharmc.infrastructure.persistence.interfaces.DrugRepositoryInterface;
import com.pharmc.domain.entity.DrugEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonDrugRepository implements DrugRepositoryInterface {
    private JsonRepository jsonRepository;
    private HashMap<String, DrugEntity> drugs = new HashMap<>();

    public JsonDrugRepository(String filePath) {
        this.jsonRepository = new JsonRepository(filePath, DrugEntity.class);

        ArrayList<DrugEntity> data = jsonRepository.load();
        for (DrugEntity drug : data) {
            drugs.put(drug.getId(), drug);
        }
    }

    public JsonDrugRepository() {
        this(System.getProperty("user.dir") + "/src/main/resources/drugs.json");
    }

    @Override
    public DrugEntity findById(String id) {
        return drugs.get(id);
    }

    @Override
    public ArrayList<DrugEntity> findAll() {
        return new ArrayList<>(drugs.values());
    }

    @Override
    public void save(DrugEntity entity) {
        drugs.put(entity.getId(), entity);
        jsonRepository.save(new ArrayList<>(drugs.values()));
    }

    @Override
    public void delete(String id) {
        drugs.remove(id);
        jsonRepository.save(new ArrayList<>(drugs.values()));
    }

    @Override
    public void deleteAll() {
        drugs.clear();
        jsonRepository.save(new ArrayList<>(drugs.values()));
    }
}
