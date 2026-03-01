package com.arad.care4pets;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface HealthRecordDao {
    @Insert
    void insert(HealthRecord healthRecord);

    @Update
    void update(HealthRecord healthRecord);

    @Delete
    void delete(HealthRecord healthRecord);

    @Query("SELECT * FROM health_records")
    LiveData<List<HealthRecord>> getAllHealthRecords();
}
