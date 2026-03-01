package com.arad.care4pets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class PetProfileActivity extends AppCompatActivity {

    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        // Get the Pet object from the intent
        pet = getIntent().getParcelableExtra("pet");
        if (pet == null) {
            // If no pet is passed, finish the activity
            Toast.makeText(this, "Error: Pet not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize ViewModels
        HealthRecordViewModel healthRecordViewModel = new ViewModelProvider(this).get(HealthRecordViewModel.class);
        ReminderViewModel reminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);

        // Setup UI Elements
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnOptions = findViewById(R.id.btnOptions);
        ImageButton fabFavorite = findViewById(R.id.fabFavorite);

        ImageView ivPetImage = findViewById(R.id.ivPetImage);
        TextView tvPetName = findViewById(R.id.tvPetName);
        TextView tvPetSpecies = findViewById(R.id.tvPetSpecies);

        View ageCard = findViewById(R.id.age_card);
        View weightCard = findViewById(R.id.weight_card);
        View recordsCard = findViewById(R.id.records_card);
        View upcomingCard = findViewById(R.id.upcoming_card);
        View healthCard = findViewById(R.id.health_card);

        Button btnViewHealthRecords = findViewById(R.id.btnViewHealthRecords);
        Button btnAddHealthRecord = findViewById(R.id.btnAddHealthRecord);
        Button btnCareInstructions = findViewById(R.id.btnCareInstructions);

        // --- Populate Static Pet Info ---
        ivPetImage.setImageResource(R.drawable.dog_placeholder); // Placeholder
        tvPetName.setText(pet.getName());
        tvPetSpecies.setText(pet.getSpecies());

        // Populate Info Cards
        ((ImageView) ageCard.findViewById(R.id.ivIcon)).setImageResource(R.drawable.ic_calendar);
        ((TextView) ageCard.findViewById(R.id.tvTitle)).setText(R.string.age);
        ((TextView) ageCard.findViewById(R.id.tvValue)).setText(getString(R.string.years_format, pet.getAge()));

        ((ImageView) weightCard.findViewById(R.id.ivIcon)).setImageResource(R.drawable.ic_weight);
        ((TextView) weightCard.findViewById(R.id.tvTitle)).setText(R.string.weight);
        ((TextView) weightCard.findViewById(R.id.tvValue)).setText(getString(R.string.kg_format, pet.getWeight()));

        // Populate Health Card
        ((ImageView) healthCard.findViewById(R.id.ivIcon)).setImageResource(R.drawable.ic_heart);
        ((TextView) healthCard.findViewById(R.id.tvValue)).setText(getString(R.string.percent_format, pet.getHealth()));
        ((TextView) healthCard.findViewById(R.id.tvTitle)).setText(R.string.health);

        // --- Observe LiveData for Dynamic Stats ---
        healthRecordViewModel.getAllHealthRecords().observe(this, records -> {
            ((ImageView) recordsCard.findViewById(R.id.ivIcon)).setImageResource(R.drawable.ic_records);
            ((TextView) recordsCard.findViewById(R.id.tvValue)).setText(String.valueOf(records.size()));
            ((TextView) recordsCard.findViewById(R.id.tvTitle)).setText(R.string.records);
        });

        reminderViewModel.getAllReminders().observe(this, reminders -> {
            ((ImageView) upcomingCard.findViewById(R.id.ivIcon)).setImageResource(R.drawable.ic_calendar);
            ((TextView) upcomingCard.findViewById(R.id.tvValue)).setText(String.valueOf(reminders.size()));
            ((TextView) upcomingCard.findViewById(R.id.tvTitle)).setText(R.string.upcoming);
        });

        // --- Set Click Listeners ---
        btnBack.setOnClickListener(v -> finish());

        btnViewHealthRecords.setOnClickListener(v -> startActivity(new Intent(this, HealthRecordsActivity.class)));
        btnAddHealthRecord.setOnClickListener(v -> startActivity(new Intent(this, AddHealthRecordActivity.class)));
        btnCareInstructions.setOnClickListener(v -> startActivity(new Intent(this, CareInstructionsActivity.class)));

        // Placeholder listeners for other buttons
        btnOptions.setOnClickListener(v -> Toast.makeText(this, "Options clicked", Toast.LENGTH_SHORT).show());
        fabFavorite.setOnClickListener(v -> Toast.makeText(this, "Favorite clicked", Toast.LENGTH_SHORT).show());
    }
}
