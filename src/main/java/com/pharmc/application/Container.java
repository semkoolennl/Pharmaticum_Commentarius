package com.pharmc.application;

import com.pharmc.application.service.CommentService;
import com.pharmc.application.service.DrugService;
import com.pharmc.application.service.TimeblockService;
import com.pharmc.application.service.interfaces.CommentServiceInterface;
import com.pharmc.application.service.interfaces.DrugServiceInterface;
import com.pharmc.application.service.interfaces.TimeblockServiceInterface;
import com.pharmc.infrastructure.persistence.JsonCommentRepository;
import com.pharmc.infrastructure.persistence.JsonDB;
import com.pharmc.infrastructure.persistence.JsonDrugRepository;
import com.pharmc.infrastructure.persistence.JsonTimeblockRepository;
import com.pharmc.infrastructure.persistence.interfaces.CommentRepositoryInterface;
import com.pharmc.infrastructure.persistence.interfaces.DrugRepositoryInterface;
import com.pharmc.infrastructure.persistence.interfaces.TimeblockRepositoryInterface;
import com.pharmc.representation.console.CommentUI;
import com.pharmc.representation.console.MainUI;
import com.pharmc.representation.console.TimelineUI;
import com.pharmc.representation.interfaces.MainUiInterface;

import java.util.HashMap;

public class Container {
    private HashMap<Class, Object> services = new HashMap<>();

    public Container() {
        services.put(JsonDB.class, new JsonDB(System.getProperty("user.dir") + "\\src\\main\\resources\\db.json"));
        services.put(DrugRepositoryInterface.class, new JsonDrugRepository(resolve(JsonDB.class)));
        services.put(CommentRepositoryInterface.class, new JsonCommentRepository(resolve(JsonDB.class)));
        services.put(TimeblockRepositoryInterface.class, new JsonTimeblockRepository(resolve(JsonDB.class)));
        services.put(CommentServiceInterface.class, new CommentService(resolve(CommentRepositoryInterface.class)));
        services.put(TimeblockServiceInterface.class, new TimeblockService(resolve(TimeblockRepositoryInterface.class)));
        services.put(DrugServiceInterface.class, new DrugService(resolve(DrugRepositoryInterface.class)));
        services.put(MainUiInterface.class, new MainUI(this));
        services.put(TimelineUI.class, new TimelineUI(this));
        services.put(CommentUI.class, new CommentUI(this));
    }

    public <T> T resolve(Class<T> type) {
        return (T) services.get(type);
    }


}
