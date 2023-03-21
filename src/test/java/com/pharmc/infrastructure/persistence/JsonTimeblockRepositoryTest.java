package com.pharmc.infrastructure.persistence;

import com.pharmc.domain.entity.TimeblockEntity;
import junit.framework.TestCase;

import java.util.ArrayList;

public class JsonTimeblockRepositoryTest extends TestCase {
    private JsonTimeblockRepository repository;

    public void setUp() throws Exception {
        super.setUp();
        repository = new JsonTimeblockRepository(System.getProperty("user.dir") + "/src/test/resources/timeblocks.json");
        repository.deleteAll();
        repository.save(new TimeblockEntity("1", "timeblock 1", 10));
        repository.save(new TimeblockEntity("2", "timeblock 2", 20));
    }

    public void tearDown() {
        repository.deleteAll();
    }

    public void testFindAll() {
        ArrayList<TimeblockEntity> timeblocks = repository.findAll();
        assertEquals(2, timeblocks.size());
    }

    public void testFindById() {
        ArrayList<TimeblockEntity> timeblocks = repository.findAll();
        for (TimeblockEntity timeblock : timeblocks) {
            TimeblockEntity foundTimeblock = repository.findById(timeblock.getId());
            assertNotNull(foundTimeblock);
        }
    }

    public void testFindByIdNotFound() {
        TimeblockEntity timeblock = repository.findById("testid");
        assertNull(timeblock);
    }

    public void testFindByDrugId() {
        ArrayList<TimeblockEntity> timeblocks = repository.findAll();
        for (TimeblockEntity timeblock : timeblocks) {
            ArrayList<TimeblockEntity> foundTimeblocks = repository.findByDrugId(timeblock.getDrugId());
            assertNotNull(foundTimeblocks);
        }
    }

    public void testFindByDrugIdNotFound() {
        ArrayList<TimeblockEntity> timeblocks = repository.findByDrugId("testid");
        assertEquals(0, timeblocks.size());
    }

    public void testSave() {
        TimeblockEntity timeblock = new TimeblockEntity("3", "timeblock 3", 30);
        repository.save(timeblock);

        repository.findById(timeblock.getId());
        assertNotNull(timeblock);
        assertEquals("timeblock 3", timeblock.getDescription());
    }

    public void testSaveNoDuplicate() {
        TimeblockEntity timeblock = new TimeblockEntity("3", "timeblock 3", 30);
        repository.save(timeblock);
        repository.save(timeblock);

        ArrayList<TimeblockEntity> timeblocks = repository.findAll();
        assertEquals(3, timeblocks.size());
    }

    public void testDelete() {
        TimeblockEntity timeblock = new TimeblockEntity("3", "timeblock 3", 30);
        repository.save(timeblock);

        repository.delete(timeblock.getId());
        TimeblockEntity foundTimeblock = repository.findById(timeblock.getId());
        assertNull(foundTimeblock);
    }

    public void testDeleteAll() {
        repository.deleteAll();
        ArrayList<TimeblockEntity> timeblocks = repository.findAll();
        assertEquals(0, timeblocks.size());
    }
}