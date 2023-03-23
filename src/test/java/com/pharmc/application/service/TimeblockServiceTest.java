package com.pharmc.application.service;

import com.pharmc.domain.entity.TimeblockEntity;
import com.pharmc.infrastructure.persistence.JsonDB;
import com.pharmc.infrastructure.persistence.JsonTimeblockRepository;
import junit.framework.TestCase;

public class TimeblockServiceTest extends TestCase {
    private TimeblockService service;
    private JsonTimeblockRepository repository;

    public void setUp() throws Exception {
        super.setUp();
        repository = new JsonTimeblockRepository(new JsonDB(System.getProperty("user.dir") + "/src/test/resources/db.json"));
        service    = new TimeblockService(repository);

        repository.deleteAll();
    }

    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    public void testCreateTimeblock() {
        TimeblockEntity block = service.save(new TimeblockEntity(1, "timeblock 1", 10));
        assertNotNull(block);

        TimeblockEntity saved = repository.findById(block.getId());
        assertEquals(block.getId(), saved.getId());
        assertEquals(block.getDrugId(), saved.getDrugId());
        assertEquals(block.getDescription(), saved.getDescription());

        repository.deleteAll();
    }

    public void testUpdateTimeblock() {
        TimeblockEntity block = service.save(new TimeblockEntity(1, "timeblock 1", 10));
        block.setDescription("timeblock 1 updated");
        block.setDuration(20);
        service.save(block);

        TimeblockEntity saved = service.readById(block.getId());
        assertEquals(block.getId(), saved.getId());
        assertEquals(block.getDrugId(), saved.getDrugId());
        assertEquals("timeblock 1 updated", saved.getDescription());
        assertEquals(20,  saved.getDuration());

        repository.deleteAll();
    }

    public void testDeleteTimeblock() {
        TimeblockEntity block = service.save(new TimeblockEntity(1, "timeblock 1", 10));
        assertNotNull(service.readById(block.getId()));

        service.delete(block.getId());
        assertNull(service.readById(block.getId()));

        repository.deleteAll();
    }

    public void testDeleteTimeblockNotFound() {
        service.save(new TimeblockEntity(1, "timeblock 1", 10));

        assertEquals(1, service.readAll().size());

        service.delete(666);
        assertEquals(1, service.readAll().size());

        repository.deleteAll();
    }

    public void testReadTimeblocks() {
        service.save(new TimeblockEntity(1, "timeblock 1", 10));
        service.save(new TimeblockEntity(2, "timeblock 2", 10));
        assertEquals(2, service.readAll().size());

        service.save(new TimeblockEntity(1, "timeblock 3", 10));
        assertEquals(3, service.readAll().size());

        repository.deleteAll();
    }

    public void testReadTimeblocksNotFound() {
        assertEquals(0, service.readAll().size());
    }

    public void testReadTimeblockById() {
        TimeblockEntity block = service.save(new TimeblockEntity(1, "timeblock 1", 10));
        assertNotNull(service.readById(block.getId()));
    }

    public void testReadTimeblockByIdNotFound() {
        assertNull(service.readById(666));
    }

    public void testReadTimeblocksByDrugId() {
        service.save(new TimeblockEntity(1, "timeblock 1", 10));
        service.save(new TimeblockEntity(1, "timeblock 2", 10));
        service.save(new TimeblockEntity(2, "timeblock 3", 10));
        assertEquals(2, service.readByDrugId(1).size());
        assertEquals(1, service.readByDrugId(2).size());

        repository.deleteAll();
    }

    public void testReadTimeblocksByDrugIdNotFound() {
        assertEquals(0, service.readByDrugId(1).size());
    }
}