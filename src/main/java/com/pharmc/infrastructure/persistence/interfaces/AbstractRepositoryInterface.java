package com.pharmc.infrastructure.persistence.interfaces;

import java.util.ArrayList;

public interface AbstractRepositoryInterface<Entity> {
    void save(Entity entity);
    Entity findById(int id);
    ArrayList<Entity> findAll();
    ArrayList<Entity> searchByField(String field, Object value);
    boolean delete(Entity entity);
    boolean delete(int id);
    void deleteAll();
}
