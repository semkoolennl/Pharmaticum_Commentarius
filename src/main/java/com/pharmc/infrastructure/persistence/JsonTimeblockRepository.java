package com.pharmc.infrastructure.persistence;

import com.pharmc.domain.entity.TimeblockEntity;
import com.pharmc.infrastructure.persistence.interfaces.JsonRepositoryInterface;
import com.pharmc.infrastructure.persistence.interfaces.TimeblockRepositoryInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonTimeblockRepository implements TimeblockRepositoryInterface {
    private JsonRepositoryInterface jsonRepository;
    private HashMap<String, TimeblockEntity> timeblocks = new HashMap<>();

    public JsonTimeblockRepository(String filepath) {
        this.jsonRepository = new JsonRepository(filepath, TimeblockEntity.class);

        ArrayList<TimeblockEntity> data = jsonRepository.load();
        for (TimeblockEntity timeblock : data) {
            timeblocks.put(timeblock.getId(), timeblock);
        }
    }

    public JsonTimeblockRepository() {
        this(System.getProperty("user.dir") + "/src/main/resources/timeblocks.json");
    }

    @Override
    public ArrayList<TimeblockEntity> findAll() {
        return new ArrayList<>(timeblocks.values());
    }

    public TimeblockEntity findById(String id) {
        return timeblocks.get(id);
    }

    @Override
    public ArrayList<TimeblockEntity> findByDrugId(String drugId) {
        ArrayList<TimeblockEntity> result = new ArrayList<>();

        if (timeblocks.isEmpty()) {
            return result;
        }
        for (TimeblockEntity timeblock : timeblocks.values()) {
            if (timeblock.getDrugId().equals(drugId)) {
                result.add(timeblock);
            }
        }
        return result;
    }

    @Override
    public void save(TimeblockEntity timeblock) {
        timeblocks.put(timeblock.getId(), timeblock);
        jsonRepository.save(new ArrayList<>(timeblocks.values()));
    }

    @Override
    public void delete(String id) {
        timeblocks.remove(id);
        jsonRepository.save(new ArrayList<>(timeblocks.values()));
    }

    @Override
    public void deleteAll() {
        timeblocks.clear();
        jsonRepository.save(new ArrayList<>(timeblocks.values()));
    }
}
