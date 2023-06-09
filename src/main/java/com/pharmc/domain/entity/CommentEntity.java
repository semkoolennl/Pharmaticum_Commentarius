package com.pharmc.domain.entity;

import java.util.Date;

public class CommentEntity extends BaseEntity {
    private String text;
    private String date;
    private int drugId;



    public CommentEntity(int drugId, String text) {
        this.text = text;
        this.date = String.valueOf(new Date());
        this.drugId = drugId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.date = String.valueOf(new Date());
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

//    public String toString() {
//        return "Comment{" +
//                "id=" + id +
//                ", text='" + text + '\'' +
//                ", date='" + date + '\'' +
//                '}';
//    }
}
