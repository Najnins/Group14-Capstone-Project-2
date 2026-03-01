package com.arad.care4pets;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class AppRepository {

    private PetDao petDao;
    private ReminderDao reminderDao;
    private HealthRecordDao healthRecordDao;
    private CareInstructionsDao careInstructionsDao;
    private LiveData<List<Pet>> allPets;
    private LiveData<List<Reminder>> allReminders;
    private LiveData<List<HealthRecord>> allHealthRecords;
    private LiveData<List<CareInstruction>> allCareInstructions;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        petDao = db.petDao();
        reminderDao = db.reminderDao();
        healthRecordDao = db.healthRecordDao();
        careInstructionsDao = db.careInstructionsDao();
        allPets = petDao.getAllPets();
        allReminders = reminderDao.getAllReminders();
        allHealthRecords = healthRecordDao.getAllHealthRecords();
        allCareInstructions = careInstructionsDao.getAllInstructions();
    }

    public void populateInitialData() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // For simplicity, we're not checking if the DB is empty.
            // In a real app, you'd want to do this only once.

            petDao.insert(new Pet("Luna", "Dog", 3, "Allergic to chicken", 25, 98));
            petDao.insert(new Pet("Milo", "Cat", 2, "Needs eye drops", 12, 95));

            reminderDao.insert(new Reminder("Luna – Vet visit", "2024-12-15", null, "Vet", false, "Annual checkup"));
            reminderDao.insert(new Reminder("Milo – Vaccination", "2025-01-05", null, "Vaccine", false, "Rabies booster"));

            healthRecordDao.insert(new HealthRecord("Rabies Vaccine", "Given: Nov 10, 2025\nNext: Nov 10, 2026", "Vaccinations"));
            healthRecordDao.insert(new HealthRecord("DHPP Vaccine", "Given: Oct 15, 2025\nNext: Oct 15, 2026", "Vaccinations"));
            healthRecordDao.insert(new HealthRecord("Heartgard Plus", "1 tablet • Monthly", "Medications"));
            healthRecordDao.insert(new HealthRecord("Bravecto", "500mg • Every 12 weeks", "Medications"));
            healthRecordDao.insert(new HealthRecord("Annual Checkup", "Overall health is excellent. Weight: 45 lbs", "Health Notes"));
            healthRecordDao.insert(new HealthRecord("Skin Allergy", "Prescribed medication for skin allergies.", "Health Notes"));

            careInstructionsDao.insert(new CareInstruction("• Feed twice daily"));
            careInstructionsDao.insert(new CareInstruction("• Walk for 30 minutes"));
        });
    }


    // Pet methods
    public LiveData<List<Pet>> getAllPets() { return allPets; }
    public void insert(Pet pet) {
        AppDatabase.databaseWriteExecutor.execute(() -> petDao.insert(pet));
    }

    // Reminder methods
    public LiveData<List<Reminder>> getAllReminders() { return allReminders; }
    public void insert(Reminder reminder) {
        AppDatabase.databaseWriteExecutor.execute(() -> reminderDao.insert(reminder));
    }
    public void update(Reminder reminder) {
        AppDatabase.databaseWriteExecutor.execute(() -> reminderDao.update(reminder));
    }

    // HealthRecord methods
    public LiveData<List<HealthRecord>> getAllHealthRecords() { return allHealthRecords; }
    public void insert(HealthRecord healthRecord) {
        AppDatabase.databaseWriteExecutor.execute(() -> healthRecordDao.insert(healthRecord));
    }

    // CareInstruction methods
    public LiveData<List<CareInstruction>> getAllCareInstructions() { return allCareInstructions; }
    public void insert(CareInstruction instruction) {
        AppDatabase.databaseWriteExecutor.execute(() -> careInstructionsDao.insert(instruction));
    }
}
