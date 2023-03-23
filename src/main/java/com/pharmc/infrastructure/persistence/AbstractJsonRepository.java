package com.pharmc.infrastructure.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pharmc.domain.entity.BaseEntity;
import com.pharmc.infrastructure.persistence.interfaces.AbstractRepositoryInterface;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

abstract class AbstractJsonRepository<Entity extends BaseEntity> implements AbstractRepositoryInterface<Entity> {
    private JsonDB jsonDB;
    private Gson gson;
    private Class<Entity> clazz;
    protected HashMap<Integer, Entity> entities = new HashMap<>();

    public AbstractJsonRepository(JsonDB jsonDB, Class<Entity> clazz) {
        this.jsonDB = jsonDB;
        this.gson   = jsonDB.getGson();
        this.clazz  = clazz;

        loadEntitiesFromJson();
    }

    public void save(Entity entity) {
        if (entity.getId() == -1) {
            entity.setId(determineNextId());
        }
        entities.put(entity.getId(), entity);
        persist();
    }

    public Entity findById(int id) {
        return entities.get(id);
    }

    public ArrayList<Entity> findAll() {
        return new ArrayList<>(entities.values());
    }

    public boolean delete(Entity entity) {
        return delete(entity.getId());
    }

    public boolean delete(int id) {
        if (!entities.containsKey(id)) {
            return false;
        }
        entities.remove(id);
        persist();
        return true;
    }

    public void deleteAll() {
        entities.clear();
        persist();
    }

    public ArrayList<Entity> searchByField(String fieldName, Object value) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Entity entity : entities.values()) {
            Object fieldValue = getEntityFieldValue(fieldName, entity);
            if (fieldValue != null && fieldValue.equals(value)) {
                result.add(entity);
            }
        }
        return result;
    }

    private Object getEntityFieldValue(String fieldName, Entity entity) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int determineNextId() {
        int maxId = 0;
        for (Entity entity : entities.values()) {
            if (entity.getId() > maxId) {
                maxId = entity.getId();
            }
        }
        return maxId + 1;
    }

    private void persist() {
        jsonDB.saveJson(clazz.getName(), gson.toJson(entities.values()));
    }

    private void loadEntitiesFromJson() {
        String json = jsonDB.loadJson(clazz.getName());

        Type typeOfT = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        ArrayList<Entity> array = gson.fromJson(json, typeOfT);
        if (array == null) {
            return;
        }
        for (Entity entity : array) {
            entities.put(entity.getId(), entity);
        }
    }
}
