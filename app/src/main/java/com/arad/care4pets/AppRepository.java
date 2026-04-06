package com.arad.care4pets;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AppRepository {

    private final PetDao petDao;
    private final ReminderDao reminderDao;
    private final HealthRecordDao healthRecordDao;
    private final CareInstructionsDao careInstructionsDao;
    private final UserDao userDao;

    private final LiveData<List<Pet>> allPets;
    private final LiveData<List<Reminder>> allReminders;
    private final LiveData<List<HealthRecord>> allHealthRecords;
    private final LiveData<List<CareInstruction>> allCareInstructions;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        petDao = db.petDao();
        reminderDao = db.reminderDao();
        healthRecordDao = db.healthRecordDao();
        careInstructionsDao = db.careInstructionsDao();
        userDao = db.userDao();
        allPets = petDao.getAllPets();
        allReminders = reminderDao.getAllReminders();
        allHealthRecords = healthRecordDao.getAllHealthRecords();
        allCareInstructions = careInstructionsDao.getAllInstructions();
    }

    // User

    public boolean registerUser(String email, String plainPassword, String name) {
        String hash = PasswordUtils.hash(plainPassword);
        User user = new User(email, hash, name);
        long result = userDao.insert(user);
        return result != -1; // -1 means the email already existed (IGNORE strategy)
    }

    /**
     * Looks up a user by email and verifies the password.
     * Returns the User if credentials match, null otherwise.
     */
    public User login(String email, String plainPassword) {
        User user = userDao.getUserByEmail(email);
        if (user == null) return null; // email not found
        if (!PasswordUtils.verify(plainPassword, user.getPasswordHash())) return null; // wrong password
        return user;
    }

    // Pet
    public LiveData<List<Pet>> getAllPets() { return allPets; }
    public void insert(Pet pet) { AppDatabase.databaseWriteExecutor.execute(() -> petDao.insert(pet)); }
    public void update(Pet pet) { AppDatabase.databaseWriteExecutor.execute(() -> petDao.update(pet)); }
    public void delete(Pet pet) { AppDatabase.databaseWriteExecutor.execute(() -> petDao.delete(pet)); }

    // Reminder
    public LiveData<List<Reminder>> getAllReminders() { return allReminders; }

    public LiveData<List<Reminder>> getRemindersForPet(int petId) {
        return reminderDao.getRemindersForPet(petId);
    }

    public void insert(Reminder reminder) {
        AppDatabase.databaseWriteExecutor.execute(() -> reminderDao.insert(reminder));
    }

    // Inserts a reminder and returns its auto-generated ID.

    public long insertReminderAndGetId(Reminder reminder) {
        Future<Long> future = AppDatabase.databaseWriteExecutor.submit(
                () -> reminderDao.insert(reminder)
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return -1;
        }
    }

    public void update(Reminder reminder) { AppDatabase.databaseWriteExecutor.execute(() -> reminderDao.update(reminder)); }
    public void delete(Reminder reminder) { AppDatabase.databaseWriteExecutor.execute(() -> reminderDao.delete(reminder)); }

    // HealthRecord
    public LiveData<List<HealthRecord>> getAllHealthRecords() { return allHealthRecords; }

    public LiveData<List<HealthRecord>> getHealthRecordsForPet(int petId) {
        return healthRecordDao.getHealthRecordsForPet(petId);
    }

    public void insert(HealthRecord h) { AppDatabase.databaseWriteExecutor.execute(() -> healthRecordDao.insert(h)); }
    public void update(HealthRecord h) { AppDatabase.databaseWriteExecutor.execute(() -> healthRecordDao.update(h)); }
    public void delete(HealthRecord h) { AppDatabase.databaseWriteExecutor.execute(() -> healthRecordDao.delete(h)); }

    // CareInstruction
    public LiveData<List<CareInstruction>> getAllCareInstructions() { return allCareInstructions; }

    public LiveData<List<CareInstruction>> getInstructionsForPet(int petId) {
        return careInstructionsDao.getInstructionsForPet(petId);
    }

    public void insert(CareInstruction i) { AppDatabase.databaseWriteExecutor.execute(() -> careInstructionsDao.insert(i)); }
    public void delete(CareInstruction i) { AppDatabase.databaseWriteExecutor.execute(() -> careInstructionsDao.delete(i)); }
}