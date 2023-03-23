package com.pharmc.domain.entity;

public class TimeblockEntity extends BaseEntity {
    private String description;
    private int duration;
    private int drugId;

    public TimeblockEntity(int drugId, String description, int duration) {
        this.drugId = drugId;
        this.description = description;
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public String toString() {
        return "Timeblock{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
