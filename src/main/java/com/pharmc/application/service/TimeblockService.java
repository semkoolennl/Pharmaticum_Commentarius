package com.pharmc.application.service;

import com.pharmc.application.service.interfaces.TimeblockServiceInterface;
import com.pharmc.domain.entity.TimeblockEntity;
import com.pharmc.infrastructure.persistence.interfaces.TimeblockRepositoryInterface;

import java.util.ArrayList;

public class TimeblockService implements TimeblockServiceInterface {
    private TimeblockRepositoryInterface repository;

    public TimeblockService(TimeblockRepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public TimeblockEntity createTimeblock(String drugId, String description, int duration) {
        TimeblockEntity timeblock = new TimeblockEntity(drugId, description, duration);
        repository.save(timeblock);

        return timeblock;
    }

    @Override
    public TimeblockEntity createTimeblock(TimeblockEntity timeblock) {
        repository.save(timeblock);

        return timeblock;
    }

    @Override
    public TimeblockEntity updateTimeblock(String id, String description, int duration) {
        TimeblockEntity timeblock = repository.findById(id);

        if (timeblock == null) {
            return null;
        }

        timeblock.setDescription(description);
        timeblock.setDuration(duration);

        return updateTimeblock(timeblock);
    }

    public TimeblockEntity updateTimeblock(TimeblockEntity timeblock) {
        repository.save(timeblock);

        return timeblock;
    }

    @Override
    public void deleteTimeblock(String id) {
        repository.delete(id);
    }

    @Override
    public void deleteTimeblock(TimeblockEntity timeblock) {
        repository.delete(timeblock.getId());
    }

    @Override
    public ArrayList<TimeblockEntity> readTimeblocks() {
        return repository.findAll();
    }

    @Override
    public TimeblockEntity readTimeblockById(String id) {
        return repository.findById(id);
    }

    public ArrayList<TimeblockEntity> readTimeblocksByDrugId(String id) {
        return repository.findByDrugId(id);
    }
}
