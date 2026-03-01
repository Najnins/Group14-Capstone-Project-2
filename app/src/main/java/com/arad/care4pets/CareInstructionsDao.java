package com.arad.care4pets;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CareInstructionsDao {
    @Insert
    void insert(CareInstruction instruction);

    @Query("SELECT * FROM care_instructions")
    LiveData<List<CareInstruction>> getAllInstructions();
}
