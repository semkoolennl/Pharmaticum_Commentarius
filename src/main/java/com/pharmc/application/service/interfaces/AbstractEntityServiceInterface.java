package com.pharmc.application.service.interfaces;

import java.util.ArrayList;

public interface AbstractEntityServiceInterface<Entity> {
    Entity save(Entity entity);
    Entity readById(int id);
    ArrayList<Entity> readAll();
    boolean delete(Entity entity);
    boolean delete(int id);
}
