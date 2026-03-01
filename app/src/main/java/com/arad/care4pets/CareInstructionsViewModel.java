package com.arad.care4pets;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class CareInstructionsViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<CareInstruction>> allInstructions;

    public CareInstructionsViewModel(Application application) {
        super(application);
        repository = new AppRepository(application);
        allInstructions = repository.getAllCareInstructions();
    }

    public LiveData<List<CareInstruction>> getAllInstructions() {
        return allInstructions;
    }

    public void insert(CareInstruction instruction) {
        repository.insert(instruction);
    }
}
