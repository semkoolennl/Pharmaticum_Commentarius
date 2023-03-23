package com.pharmc.application.service.interfaces;

import com.pharmc.domain.entity.CommentEntity;

import java.util.ArrayList;

public interface CommentServiceInterface extends AbstractEntityServiceInterface<CommentEntity>{
    ArrayList<CommentEntity> readByDrugId(int id);
}
