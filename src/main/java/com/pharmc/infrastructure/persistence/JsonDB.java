package com.pharmc.infrastructure.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public class JsonDB {
    private final String filepath;
    private final Gson gson;
    private HashMap<String, String> data = new HashMap<>();

    public JsonDB(String filepath) {
        this.filepath = filepath;
        this.gson     = new Gson();
    }

    public String loadJson(String key) {
        return data.get(key);
    }

    public void saveJson(String key, String value) {
        // save data to json file. Per key in the first level of the json file store the json string in to the data hashmap using the key as the key in the hashmap
        data.put(key, value);
        try (Writer writer = new FileWriter(filepath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.out.println("jsonDB save error: " + e.getMessage());
        }
    }

    public Gson getGson() {
        return gson;
    }

    private void initialize() {
        createFileIfItDoesNotExist();
        // load data from json file. Per key in the first level of the json file store the json string in to the data hashmap using the key as the key in the hashmap
        try (Reader reader = new FileReader(filepath)) {
            Type typeOfT = TypeToken.getParameterized(HashMap.class, String.class, String.class).getType();
            data = gson.fromJson(reader, typeOfT);
        } catch (IOException e) {
            System.out.println("jsonDB load error: " + e.getMessage());
        }
    }

    private void createFileIfItDoesNotExist() {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("jsonDB createFile error: " + e.getMessage());
            }
        }
    }

}
