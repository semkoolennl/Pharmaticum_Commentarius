package com.pharmc.infrastructure.persistence.interfaces;

import java.util.ArrayList;

public interface JsonRepositoryInterface {
    <Entity> ArrayList<Entity> load();
    <Entity> void save(ArrayList<Entity> data);
}