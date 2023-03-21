package com.pharmc.application.service;

import com.pharmc.application.service.interfaces.DrugServiceInterface;
import com.pharmc.domain.entity.DrugEntity;
import com.pharmc.infrastructure.persistence.interfaces.DrugRepositoryInterface;

import java.util.ArrayList;

public class DrugService implements DrugServiceInterface {
    private final DrugRepositoryInterface repository;

    public DrugService(DrugRepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public DrugEntity createDrug(String name, String description) {
        DrugEntity drug = new DrugEntity(name, description);
        repository.save(drug);
        return drug;
    }

    @Override
    public DrugEntity updateDrug(String id, String name, String description) {
        DrugEntity drug = repository.findById(id);

        if (drug == null) {
            return null;
        }

        drug.setName(name);
        drug.setDescription(description);
        repository.save(drug);
        return drug;
    }

    @Override
    public void deleteDrug(String id) {
        repository.delete(id);
    }

    @Override
    public ArrayList<DrugEntity> readDrugs() {
        return repository.findAll();
    }

    @Override
    public DrugEntity readDrugById(String id) {
        return repository.findById(id);
    }
}
