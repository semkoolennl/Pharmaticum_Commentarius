package com.pharmc.application.service;

import com.pharmc.domain.entity.DrugEntity;
import com.pharmc.infrastructure.persistence.JsonDrugRepository;
import junit.framework.TestCase;

public class DrugServiceTest extends TestCase {
    private DrugService service;
    private JsonDrugRepository repository;

    public void setUp() throws Exception {
        super.setUp();
        repository = new JsonDrugRepository(System.getProperty("user.dir") + "/src/test/resources/drugs.json");
        service = new DrugService(repository);

        repository.deleteAll();
    }

    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    public void testCreateDrug() {
        DrugEntity drug      = service.createDrug("testdrug1", "testdescription1");
        DrugEntity drugSaved = service.readDrugById(drug.getId());

        assertEquals(drug.getId(), drugSaved.getId());
        assertEquals(drug.getName(), drugSaved.getName());
        assertEquals(drug.getDescription(), drugSaved.getDescription());

        repository.deleteAll();
    }

    public void testUpdateDrug() {
        DrugEntity drug = service.createDrug("testdrug1", "testdescription1");
        drug.setName("testdrug2");
        drug.setDescription("testdescription2");

        service.updateDrug(drug.getId(), drug.getName(), drug.getDescription());
        DrugEntity newDrug = service.readDrugById(drug.getId());

        assertEquals(drug.getId(), newDrug.getId());
        assertEquals(drug.getName(), newDrug.getName());
        assertEquals(drug.getDescription(), newDrug.getDescription());

        repository.deleteAll();
    }

    public void testUpdateDrugNotFound() {
        DrugEntity drug = service.updateDrug("testid", "testdrug2", "testdescription2");
        assertNull(drug);
    }

    public void testDeleteDrug() {
        DrugEntity drug1 = service.createDrug("testdrug1", "testdescription1");
        DrugEntity drug2 = service.createDrug("testdrug2", "testdescription2");

        assertEquals(2, service.readDrugs().size());
        service.deleteDrug(drug1.getId());
        assertEquals(1, service.readDrugs().size());
        service.deleteDrug(drug2.getId());
        assertEquals(0, service.readDrugs().size());

        repository.deleteAll();
    }

    public void testReadDrugs() {
        service.createDrug("testdrug1", "testdescription1");
        service.createDrug("testdrug2", "testdescription2");
        service.createDrug("testdrug3", "testdescription3");

        assertEquals(3, service.readDrugs().size());

        repository.deleteAll();
    }

    public void testReadDrugById() {
        DrugEntity drug = service.createDrug("testdrug1", "testdescription1");
        DrugEntity drugRead = service.readDrugById(drug.getId());

        assertEquals(drug.getId(), drugRead.getId());
        assertEquals(drug.getName(), drugRead.getName());
        assertEquals(drug.getDescription(), drugRead.getDescription());

        repository.deleteAll();
    }

    public void testReadDrugByIdNotFound() {
        DrugEntity drug = service.readDrugById("testid");
        assertNull(drug);
    }
}