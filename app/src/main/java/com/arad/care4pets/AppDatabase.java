package com.arad.care4pets;
// Import required Android and Room libraries
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
//Import concurrency utilities for background operations
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Define the Room database with entities (tables)
// version = 2 → indicates database version (used for migration)

@Database(entities = {Pet.class, Reminder.class, HealthRecord.class, CareInstruction.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    
    // Abstract DAO methods (used to access database tables)
    
    
    public abstract PetDao petDao();
    public abstract ReminderDao reminderDao();
    public abstract HealthRecordDao healthRecordDao();
    public abstract CareInstructionsDao careInstructionsDao();
        // Singleton instance to prevent multiple database instances
    private static volatile AppDatabase INSTANCE;
     // Thread pool for running database operations in background
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    // Method to get the database instance (Singleton pattern)
    
    public static AppDatabase getDatabase(final Context context) {
        
        // Check if instance already exists
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                // Double-check locking for thread safety
                if (INSTANCE == null) {
                     // Build the database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "care4pets_database")
                                // If migration not provided, recreate database (WARNING: data loss)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
