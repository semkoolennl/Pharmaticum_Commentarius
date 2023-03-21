package com.pharmc.application.service.interfaces;

import com.pharmc.domain.entity.TimeblockEntity;

import java.util.ArrayList;

public interface TimeblockServiceInterface {
    TimeblockEntity createTimeblock(String drugId, String description, int duration);
    TimeblockEntity createTimeblock(TimeblockEntity timeblock);
    TimeblockEntity updateTimeblock(String id, String description, int duration);
    TimeblockEntity updateTimeblock(TimeblockEntity timeblock);
    void deleteTimeblock(String id);
    void deleteTimeblock(TimeblockEntity timeblock);
    ArrayList<TimeblockEntity> readTimeblocks();
    TimeblockEntity readTimeblockById(String id);
    ArrayList<TimeblockEntity> readTimeblocksByDrugId(String id);


}
