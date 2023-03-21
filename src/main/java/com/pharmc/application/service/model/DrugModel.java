package com.pharmc.application.service.model;

import com.pharmc.domain.entity.CommentEntity;
import com.pharmc.domain.entity.DrugEntity;
import com.pharmc.domain.entity.TimeblockEntity;

import java.util.ArrayList;

public class DrugModel {
    private DrugEntity drug;
    private ArrayList<CommentEntity> comments;
    private ArrayList<TimeblockEntity> timeblocks;

    public DrugModel(DrugEntity drug, ArrayList<CommentEntity> comments, ArrayList<TimeblockEntity> timeblocks) {
        this.drug = drug;
        this.comments = comments;
        this.timeblocks = timeblocks;
    }

    public DrugModel(DrugEntity drug) {
        this.drug = drug;
        this.comments = new ArrayList<>();
        this.timeblocks = new ArrayList<>();
    }

    public String shortText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Drug: ").append(drug.getName()).append("\n");
        sb.append("Description: ").append(drug.getDescription());

        return sb.toString();
    }

    public String fullText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Drug: ").append(drug.getName()).append("\n");
        sb.append("Description: ").append(drug.getDescription()).append("\n");
        sb.append("Comments: \n");
        for (CommentEntity comment : comments) {
            sb.append("  ").append(comment.getText()).append(" - ").append(comment.getDate()).append("\n");
        }
        sb.append("Timeline: \n");
        for (TimeblockEntity timeblock : timeblocks) {
            sb.append("  ").append(timeblock.getDuration()).append(" - ").append(timeblock.getDescription());
        }

        return sb.toString();
    }

    public DrugEntity getDrug() {
        return drug;
    }

    public void setDrug(DrugEntity drug) {
        this.drug = drug;
    }

    public ArrayList<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentEntity> comments) {
        this.comments = comments;
    }

    public ArrayList<TimeblockEntity> getTimeblocks() {
        return timeblocks;
    }

    public void setTimeblocks(ArrayList<TimeblockEntity> timeblocks) {
        this.timeblocks = timeblocks;
    }
}
