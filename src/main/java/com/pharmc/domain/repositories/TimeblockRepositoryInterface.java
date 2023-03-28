package com.pharmc.domain.repositories;

import com.pharmc.domain.entity.TimeblockEntity;

import java.util.ArrayList;

public interface TimeblockRepositoryInterface extends AbstractRepositoryInterface<TimeblockEntity> {
    ArrayList<TimeblockEntity> findByDrugId(int drugId);
}
