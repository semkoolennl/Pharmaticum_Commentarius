package com.pharmc.infrastructure.persistence;

import com.pharmc.domain.entity.DrugEntity;
import junit.framework.TestCase;

import java.util.ArrayList;

public class JsonDrugRepositoryTest extends TestCase {
    private final JsonDrugRepository repository = new JsonDrugRepository(new JsonDB(System.getProperty("user.dir") + "/src/test/resources/db.json"));

    public void testFindAll() {
        repository.deleteAll();
        ArrayList<DrugEntity> drugs = repository.findAll();
        assertEquals(0, drugs.size());

        DrugEntity drug = new DrugEntity("testdrug1", "testdescription1");
        repository.save(drug);
        drugs = repository.findAll();
        assertEquals(1, drugs.size());

        drug = new DrugEntity("testdrug2", "testdescription2");
        repository.save(drug);
        drugs = repository.findAll();
        assertEquals(2, drugs.size());
    }

    public void testFindById() {
        repository.deleteAll();
        repository.save(new DrugEntity("testdrug1", "testdescription1"));
        repository.save(new DrugEntity("testdrug2", "testdescription3"));

        DrugEntity drug = new DrugEntity("testdrug3", "testdescription3");
        repository.save(drug);
        drug = repository.findById(drug.getId());

        assertEquals("testdrug3", drug.getName());
        assertEquals("testdescription3", drug.getDescription());
    }

    public void testFindByIdNotFound() {
        repository.deleteAll();
        DrugEntity drug = repository.findById(666);
        assertNull(drug);
    }

    public void testSave() {
        repository.deleteAll();
        DrugEntity drug = new DrugEntity("testdrug1", "testdescription1");
        repository.save(drug);

        ArrayList<DrugEntity> drugs = repository.findAll();
        assertEquals(1, drugs.size());
        assertEquals("testdrug1", drugs.get(0).getName());
        assertEquals("testdescription1", drugs.get(0).getDescription());
    }

    public void testSaveNoDuplicate() {
        repository.deleteAll();
        DrugEntity drug = new DrugEntity("testdrug1", "testdescription1");
        drug.setId(123);
        repository.save(drug);
        repository.save(drug);

        ArrayList<DrugEntity> drugs = repository.findAll();
        assertEquals(1, drugs.size());
    }
}