package com.arad.care4pets;

import java.util.ArrayList;
import java.util.List;

public class FakeRepository {

    public static final List<Pet> pets = new ArrayList<>();
    public static final List<Reminder> reminders = new ArrayList<>();
    public static final List<HealthRecord> records = new ArrayList<>();
    public static final List<String> careInstructions = new ArrayList<>();

    static {
        pets.add(new Pet("Luna", "Dog", 3, "Allergic to chicken", 25, 98));
        pets.add(new Pet("Milo", "Cat", 2, "Needs eye drops", 12, 95));

        reminders.add(new Reminder("Luna – Vet visit", "2024-12-15", null, "Vet", false, "Annual checkup"));
        reminders.add(new Reminder("Milo – Vaccination", "2025-01-05", null, "Vaccine", false, "Rabies booster"));

        careInstructions.add("• Feed twice daily");
        careInstructions.add("• Walk for 30 minutes");

        records.add(new HealthRecord("Rabies Vaccine", "Given: Nov 10, 2025\nNext: Nov 10, 2026", "Vaccinations"));
        records.add(new HealthRecord("DHPP Vaccine", "Given: Oct 15, 2025\nNext: Oct 15, 2026", "Vaccinations"));
        records.add(new HealthRecord("Heartgard Plus", "1 tablet • Monthly", "Medications"));
        records.add(new HealthRecord("Bravecto", "500mg • Every 12 weeks", "Medications"));
        records.add(new HealthRecord("Annual Checkup", "Overall health is excellent. Weight: 45 lbs", "Health Notes"));
        records.add(new HealthRecord("Skin Allergy", "Prescribed medication for skin allergies.", "Health Notes"));
    }

    private FakeRepository() {
        // prevent instantiation
    }
}
