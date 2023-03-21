package com.pharmc.application.service.interfaces;
import com.pharmc.domain.entity.DrugEntity;

import java.util.ArrayList;

public interface DrugServiceInterface {
    DrugEntity createDrug(String name, String description);
    DrugEntity updateDrug(String id, String name, String description);
    void deleteDrug(String id);
    ArrayList<DrugEntity> readDrugs();
    DrugEntity readDrugById(String id);
}
