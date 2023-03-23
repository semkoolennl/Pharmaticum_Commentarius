package com.pharmc.application.service.interfaces;

import com.pharmc.domain.entity.TimeblockEntity;

import java.util.ArrayList;

public interface TimeblockServiceInterface extends AbstractEntityServiceInterface<TimeblockEntity>{
    ArrayList<TimeblockEntity> readByDrugId(int id);
}
