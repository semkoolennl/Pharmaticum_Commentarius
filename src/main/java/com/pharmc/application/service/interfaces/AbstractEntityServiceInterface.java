package com.pharmc.application.service.interfaces;

import java.util.ArrayList;

public interface AbstractEntityServiceInterface<Entity> {
    Entity save(Entity comment);
    boolean delete(Entity comment);
    boolean delete(int id);
    ArrayList<Entity> readAll();
    Entity readById(int id);
}
