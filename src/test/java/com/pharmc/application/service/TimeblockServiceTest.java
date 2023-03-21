package com.pharmc.application.service;

import com.pharmc.domain.entity.TimeblockEntity;
import com.pharmc.infrastructure.persistence.JsonTimeblockRepository;
import junit.framework.TestCase;

public class TimeblockServiceTest extends TestCase {
    private TimeblockService service;
    private JsonTimeblockRepository repository;

    public void setUp() throws Exception {
        super.setUp();
        repository = new JsonTimeblockRepository(System.getProperty("user.dir") + "/src/test/resources/timeblocks.json");
        service    = new TimeblockService(repository);

        repository.deleteAll();
    }

    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    public void testCreateTimeblock() {
        TimeblockEntity block = service.createTimeblock("1", "timeblock 1", 10);
        assertNotNull(block);

        TimeblockEntity saved = repository.findById(block.getId());
        assertEquals(block.getId(), saved.getId());
        assertEquals(block.getDrugId(), saved.getDrugId());
        assertEquals(block.getDescription(), saved.getDescription());

        repository.deleteAll();
    }

    public void testUpdateTimeblock() {
        TimeblockEntity block = service.createTimeblock("1", "timeblock 1", 10);
        service.updateTimeblock(block.getId(), "timeblock 1 updated", 20);

        TimeblockEntity saved = service.readTimeblockById(block.getId());
        assertEquals(block.getId(), saved.getId());
        assertEquals(block.getDrugId(), saved.getDrugId());
        assertEquals("timeblock 1 updated", saved.getDescription());
        assertEquals(20,  saved.getDuration());

        repository.deleteAll();
    }

    public void testUpdateTimeblockNotFound() {
        TimeblockEntity block = service.updateTimeblock("testid", "timeblock 1 updated", 20);
        assertNull(block);
    }

    public void testDeleteTimeblock() {
        TimeblockEntity block = service.createTimeblock("1", "timeblock 1", 10);
        assertNotNull(service.readTimeblockById(block.getId()));

        service.deleteTimeblock(block.getId());
        assertNull(service.readTimeblockById(block.getId()));

        repository.deleteAll();
    }

    public void testDeleteTimeblockNotFound() {
        service.createTimeblock("1", "timeblock 1", 10);
        assertEquals(1, service.readTimeblocks().size());

        service.deleteTimeblock("testid");
        assertEquals(1, service.readTimeblocks().size());

        repository.deleteAll();
    }

    public void testReadTimeblocks() {
        service.createTimeblock("1", "timeblock 1", 10);
        service.createTimeblock("2", "timeblock 2", 10);
        assertEquals(2, service.readTimeblocks().size());

        service.createTimeblock("3", "timeblock 3", 10);
        assertEquals(3, service.readTimeblocks().size());

        repository.deleteAll();
    }

    public void testReadTimeblocksNotFound() {
        assertEquals(0, service.readTimeblocks().size());
    }

    public void testReadTimeblockById() {
        TimeblockEntity block = service.createTimeblock("1", "timeblock 1", 10);
        assertNotNull(service.readTimeblockById(block.getId()));
    }

    public void testReadTimeblockByIdNotFound() {
        assertNull(service.readTimeblockById("testid"));
    }

    public void testReadTimeblocksByDrugId() {
        service.createTimeblock("1", "timeblock 1", 10);
        service.createTimeblock("2", "timeblock 2", 10);
        service.createTimeblock("1", "timeblock 3", 10);
        assertEquals(2, service.readTimeblocksByDrugId("1").size());
        assertEquals(1, service.readTimeblocksByDrugId("2").size());

        repository.deleteAll();
    }

    public void testReadTimeblocksByDrugIdNotFound() {
        assertEquals(0, service.readTimeblocksByDrugId("1").size());
    }
}