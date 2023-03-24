package com.pharmc.infrastructure.setup;

import com.pharmc.application.service.CommentService;
import com.pharmc.application.service.DrugService;
import com.pharmc.application.service.TimeblockService;
import com.pharmc.application.service.interfaces.CommentServiceInterface;
import com.pharmc.application.service.interfaces.TimeblockServiceInterface;
import com.pharmc.infrastructure.persistence.JsonCommentRepository;
import com.pharmc.infrastructure.persistence.JsonDB;
import com.pharmc.infrastructure.persistence.JsonDrugRepository;
import com.pharmc.infrastructure.persistence.JsonTimeblockRepository;
import com.pharmc.infrastructure.persistence.interfaces.CommentRepositoryInterface;
import com.pharmc.infrastructure.persistence.interfaces.DrugRepositoryInterface;
import com.pharmc.infrastructure.persistence.interfaces.TimeblockRepositoryInterface;
import com.pharmc.representation.consoleV2.ConsoleUI;
import com.pharmc.representation.interfaces.MainUiInterface;

public class Bootstrap {
    public static Container test() {
        Container container = new Container();
        registerRepositories(container, true);
        registerServices(container);

        container.register(MainUiInterface.class, new ConsoleUI(container));

        return container;
    }

    public static Container prod() {
        Container container = new Container();

        registerRepositories(container, false);
        registerServices(container);

        container.register(MainUiInterface.class, new ConsoleUI(container));

        return container;
    }

    private static void registerServices(Container container) {
        container.register(CommentServiceInterface.class, new DrugService(container.resolve(DrugRepositoryInterface.class)));
        container.register(CommentServiceInterface.class, new CommentService(container.resolve(CommentRepositoryInterface.class)));
        container.register(TimeblockServiceInterface.class, new TimeblockService(container.resolve(TimeblockRepositoryInterface.class)));
    }

    private static void registerRepositories(Container container, boolean isTest) {
        String projectDir = System.getProperty("user.dir");
        String filepath   = projectDir + (isTest ? "\\src\\test\\resources\\db.json" : "\\src\\main\\resources\\db.json");

        JsonDB jsonDB = new JsonDB(filepath);
        container.register(DrugRepositoryInterface.class, new JsonDrugRepository(jsonDB));
        container.register(CommentRepositoryInterface.class, new JsonCommentRepository(jsonDB));
        container.register(TimeblockRepositoryInterface.class, new JsonTimeblockRepository(jsonDB));
    }
}
