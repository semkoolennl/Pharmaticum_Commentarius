package com.pharmc.infrastructure.persistence.interfaces;

import com.pharmc.domain.entity.DrugEntity;

import java.util.ArrayList;

public interface DrugRepositoryInterface {
    DrugEntity findById(String id);
    ArrayList<DrugEntity> findAll();
    void save(DrugEntity drug);
    void delete(String id);
    void deleteAll();
}
