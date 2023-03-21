package com.pharmc.infrastructure.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pharmc.infrastructure.persistence.interfaces.JsonRepositoryInterface;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonRepository implements JsonRepositoryInterface {
    private final String filepath;
    private final Gson gson;
    private Class clazz;

    public <Entity> JsonRepository(String filepath, Class<Entity> clazz) {
        this.filepath = filepath;
        this.clazz    = clazz;
        this.gson     = new Gson();
    }

    public <Entity> ArrayList<Entity> load() {
        try (Reader reader = new FileReader(filepath)) {
            Type typeOfT = TypeToken.getParameterized(ArrayList.class, clazz).getType();
            return gson.fromJson(reader, typeOfT);
        } catch (IOException e) {
            return new ArrayList<Entity>();
        }
    }

    public <Entity> void save(ArrayList<Entity> data) {
        try (Writer writer = new FileWriter(filepath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.out.println("Hello from exception");
            System.out.println(e.getMessage());
        }
    }

}
