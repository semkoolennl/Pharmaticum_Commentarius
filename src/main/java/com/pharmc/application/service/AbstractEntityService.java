package com.pharmc.application.service;

import com.pharmc.application.service.interfaces.AbstractEntityServiceInterface;
import com.pharmc.infrastructure.persistence.interfaces.AbstractRepositoryInterface;

import java.util.ArrayList;

public class AbstractEntityService<Entity> implements AbstractEntityServiceInterface<Entity> {
    protected AbstractRepositoryInterface<Entity> repository;

    public AbstractEntityService(AbstractRepositoryInterface<Entity> repository) {
        this.repository = repository;
    }

    @Override
    public Entity save(Entity entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public boolean delete(Entity entity) {
        return repository.delete(entity);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public ArrayList<Entity> readAll() {
        return repository.findAll();
    }

    @Override
    public Entity readById(int id) {
        return repository.findById(id);
    }
}
