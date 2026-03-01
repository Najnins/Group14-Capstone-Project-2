package com.arad.care4pets;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class HealthRecordViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<HealthRecord>> allHealthRecords;

    public HealthRecordViewModel(Application application) {
        super(application);
        repository = new AppRepository(application);
        allHealthRecords = repository.getAllHealthRecords();
    }

    public LiveData<List<HealthRecord>> getAllHealthRecords() {
        return allHealthRecords;
    }

    public void insert(HealthRecord healthRecord) {
        repository.insert(healthRecord);
    }
}
