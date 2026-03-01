package com.arad.care4pets;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "care_instructions")
public class CareInstruction {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String instruction;

    public CareInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstruction() {
        return instruction;
    }
}
