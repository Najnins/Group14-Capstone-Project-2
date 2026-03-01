package com.arad.care4pets;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Find all the CardViews in the menu
        CardView cardDashboard = findViewById(R.id.cardDashboard);
        CardView cardPets = findViewById(R.id.cardPets);
        CardView cardReminders = findViewById(R.id.cardReminders);
        CardView cardHealth = findViewById(R.id.cardHealth);
        CardView cardCare = findViewById(R.id.cardCare);

        // Set click listeners for each card
        cardDashboard.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, DashboardActivity.class));
        });

        cardPets.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, PetsListActivity.class));
        });

        cardReminders.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, RemindersActivity.class));
        });

        cardHealth.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, HealthRecordsActivity.class));
        });

        cardCare.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, CareInstructionsActivity.class));
        });
    }
}
