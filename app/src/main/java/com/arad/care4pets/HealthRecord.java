package com.arad.care4pets;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "health_records")
public class HealthRecord {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String category;

    public HealthRecord() {}

    @Ignore
    public HealthRecord(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
}
