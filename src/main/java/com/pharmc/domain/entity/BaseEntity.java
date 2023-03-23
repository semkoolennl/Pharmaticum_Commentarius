package com.pharmc.domain.entity;

public class BaseEntity {
    protected int id = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String toString() {
//        return "BaseEntity{" +
//                "uuid=" + id +
//                '}';
//    }
}
