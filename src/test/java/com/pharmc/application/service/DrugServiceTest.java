package com.pharmc.application.service;

import com.pharmc.domain.entity.DrugEntity;
import com.pharmc.infrastructure.persistence.JsonDB;
import com.pharmc.infrastructure.persistence.JsonDrugRepository;
import junit.framework.TestCase;

public class DrugServiceTest extends TestCase {
    private DrugService service;
    private JsonDrugRepository repository;

    public void setUp() throws Exception {
        super.setUp();
        repository = new JsonDrugRepository(new JsonDB(System.getProperty("user.dir") + "/src/test/resources/db.json"));
        service = new DrugService(repository);

        repository.deleteAll();
    }

    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    public void testCreateDrug() {
        DrugEntity drug      = service.save(new DrugEntity("testdrug1", "testdescription1"));
        DrugEntity drugSaved = service.readById(drug.getId());

        assertEquals(drug.getId(), drugSaved.getId());
        assertEquals(drug.getName(), drugSaved.getName());
        assertEquals(drug.getDescription(), drugSaved.getDescription());

        repository.deleteAll();
    }

    public void testUpdateDrug() {
        DrugEntity drug = service.save(new DrugEntity("testdrug1", "testdescription1"));
        drug.setName("testdrug2");
        drug.setDescription("testdescription2");

        service.save(drug);
        DrugEntity newDrug = service.readById(drug.getId());

        assertEquals(drug.getId(), newDrug.getId());
        assertEquals(drug.getName(), newDrug.getName());
        assertEquals(drug.getDescription(), newDrug.getDescription());

        repository.deleteAll();
    }

    public void testDeleteDrug() {
        DrugEntity drug1 = service.save(new DrugEntity("testdrug1", "testdescription1"));
        DrugEntity drug2 = service.save(new DrugEntity("testdrug2", "testdescription2"));

        assertEquals(2, service.readAll().size());
        service.delete(drug1.getId());
        assertEquals(1, service.readAll().size());
        service.delete(drug2);
        assertEquals(0, service.readAll().size());

        repository.deleteAll();
    }

    public void testReadDrugs() {
        service.save(new DrugEntity("testdrug1", "testdescription1"));
        service.save(new DrugEntity("testdrug2", "testdescription2"));
        service.save(new DrugEntity("testdrug3", "testdescription3"));

        assertEquals(3, service.readAll().size());

        repository.deleteAll();
    }

    public void testReadDrugById() {
        DrugEntity drug = service.save(new DrugEntity("testdrug1", "testdescription1"));
        DrugEntity drugRead = service.readById(drug.getId());

        assertEquals(drug.getId(), drugRead.getId());
        assertEquals(drug.getName(), drugRead.getName());
        assertEquals(drug.getDescription(), drugRead.getDescription());

        repository.deleteAll();
    }

    public void testReadDrugByIdNotFound() {
        DrugEntity drug = service.readById(666);
        assertNull(drug);
    }
}