package com.pharmc.infrastructure.persistence;

import com.pharmc.domain.entity.BaseEntity;
import junit.framework.TestCase;

import java.util.ArrayList;

public class JsonRepositoryTest extends TestCase {
    private final String filepath = System.getProperty("user.dir") + "/src/test/resources/jsontest.json";

    public void testLoad() {
        JsonRepository repo = new JsonRepository(filepath, BaseEntity.class);
        ArrayList<BaseEntity> data = repo.load();
        assertEquals(2, data.size());
    }

    public void testSave() {
        JsonRepository repo = new JsonRepository(filepath, BaseEntity.class);
        ArrayList<BaseEntity> data = new ArrayList<>();
        data.add(new BaseEntity());
        repo.save(data);

        data = repo.load();
        assertEquals(1, data.size());

        data = new ArrayList<>();
        data.add(new BaseEntity());
        data.add(new BaseEntity());
        repo.save(data);
    }
}