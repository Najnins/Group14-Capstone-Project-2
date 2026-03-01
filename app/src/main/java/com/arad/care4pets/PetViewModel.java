package com.arad.care4pets;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class PetViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Pet>> allPets;

    public PetViewModel(Application application) {
        super(application);
        repository = new AppRepository(application);
        allPets = repository.getAllPets();
        
        // Populate the database with initial data
        repository.populateInitialData();
    }

    public LiveData<List<Pet>> getAllPets() {
        return allPets;
    }

    public void insert(Pet pet) {
        repository.insert(pet);
    }
}
