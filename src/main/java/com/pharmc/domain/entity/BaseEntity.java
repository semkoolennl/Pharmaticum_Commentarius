package com.pharmc.domain.entity;

import java.util.UUID;

public class BaseEntity {
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BaseEntity() {
        this.id = UUID.randomUUID().toString();
    }

    public String toString() {
        return "BaseEntity{" +
                "uuid=" + id +
                '}';
    }
}
