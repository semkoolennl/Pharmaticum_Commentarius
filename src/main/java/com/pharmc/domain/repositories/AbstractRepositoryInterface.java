package com.pharmc.domain.repositories;

import com.pharmc.domain.entity.BaseEntity;

import java.util.ArrayList;

public interface AbstractRepositoryInterface<Entity extends BaseEntity> {
    void save(Entity entity);
    Entity findById(int id);
    ArrayList<Entity> findAll();
    boolean delete(Entity entity);
    boolean delete(int id);
    void deleteAll();
}
