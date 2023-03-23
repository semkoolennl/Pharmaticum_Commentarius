package com.pharmc.application.service;

import com.pharmc.application.service.interfaces.TimeblockServiceInterface;
import com.pharmc.domain.entity.TimeblockEntity;
import com.pharmc.infrastructure.persistence.interfaces.TimeblockRepositoryInterface;

import java.util.ArrayList;

public class TimeblockService extends AbstractEntityService<TimeblockEntity> implements TimeblockServiceInterface {
    TimeblockRepositoryInterface repository;

    public TimeblockService(TimeblockRepositoryInterface repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public ArrayList<TimeblockEntity> readByDrugId(int id) {
        return repository.findByDrugId(id);
    }
}
