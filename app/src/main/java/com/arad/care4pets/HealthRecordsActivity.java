package com.arad.care4pets;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class HealthRecordsActivity extends AppCompatActivity {

    private HealthRecordViewModel healthRecordViewModel;
    private HealthRecordAdapter adapter;
    private int petId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_records);

        // Read petId passed from PetProfileActivity (0 if opened from global menu)
        petId = getIntent().getIntExtra("pet_id", 0);
        String petName = getIntent().getStringExtra("pet_name");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show pet name in toolbar title if available
        if (petName != null && !petName.isEmpty() && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(petName + "'s Health Records");
        }

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Forward petId when adding a new record from this screen
        ExtendedFloatingActionButton fabAddHealthRecord = findViewById(R.id.fabAddHealthRecord);
        fabAddHealthRecord.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddHealthRecordActivity.class);
            intent.putExtra("pet_id", petId);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.rvHealthRecords);
        adapter = new HealthRecordAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        healthRecordViewModel = new ViewModelProvider(this).get(HealthRecordViewModel.class);

        // If petId > 0, show only that pet's records. Otherwise show all.
        if (petId > 0) {
            healthRecordViewModel.getHealthRecordsForPet(petId)
                    .observe(this, records -> adapter.submitList(records));
        } else {
            healthRecordViewModel.getAllHealthRecords()
                    .observe(this, records -> adapter.submitList(records));
        }
    }
}