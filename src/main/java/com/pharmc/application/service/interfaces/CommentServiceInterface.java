package com.pharmc.application.service.interfaces;

import com.pharmc.domain.entity.CommentEntity;

import java.util.ArrayList;

public interface CommentServiceInterface {
    CommentEntity createComment(String drugId, String text);
    CommentEntity updateComment(String id, String text);
    void deleteComment(String id);
    ArrayList<CommentEntity> readComments();
    CommentEntity readCommentById(String id);
    ArrayList<CommentEntity> readCommentsByDrugId(String id);
}
