package com.pharmc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.pharmc.infrastructure.persistence.*;
import com.pharmc.application.service.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        JsonRepositoryTest.class,
        JsonDrugRepositoryTest.class,
        JsonCommentRepositoryTest.class,
        JsonTimeblockRepositoryTest.class,
        CommentServiceTest.class,
        DrugServiceTest.class,
        TimeblockServiceTest.class
})

public class JunitTestSuite {
}  