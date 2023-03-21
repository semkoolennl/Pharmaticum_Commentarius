package com.pharmc.infrastructure.persistence.interfaces;

import com.pharmc.domain.entity.TimeblockEntity;

import java.util.ArrayList;

public interface TimeblockRepositoryInterface {
    ArrayList<TimeblockEntity> findAll();
    TimeblockEntity findById(String id);
    ArrayList<TimeblockEntity> findByDrugId(String drugId);
    void save(TimeblockEntity comment);
    void delete(String id);
    void deleteAll();
}
