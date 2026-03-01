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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_records);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        ExtendedFloatingActionButton fabAddHealthRecord = findViewById(R.id.fabAddHealthRecord);
        fabAddHealthRecord.setOnClickListener(v -> {
            Intent intent = new Intent(HealthRecordsActivity.this, AddHealthRecordActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.rvHealthRecords);
        adapter = new HealthRecordAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        healthRecordViewModel = new ViewModelProvider(this).get(HealthRecordViewModel.class);
        healthRecordViewModel.getAllHealthRecords().observe(this, records -> adapter.submitList(records));
    }
}
