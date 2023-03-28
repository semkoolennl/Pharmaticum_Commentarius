package com.pharmc.application.service;

import com.pharmc.application.service.interfaces.DrugServiceInterface;
import com.pharmc.domain.entity.DrugEntity;
import com.pharmc.domain.repositories.DrugRepositoryInterface;

public class DrugService extends AbstractEntityService<DrugEntity> implements DrugServiceInterface {
    DrugRepositoryInterface repository;
    public DrugService(DrugRepositoryInterface repository) {
        super(repository);
        this.repository = repository;
    }
}
