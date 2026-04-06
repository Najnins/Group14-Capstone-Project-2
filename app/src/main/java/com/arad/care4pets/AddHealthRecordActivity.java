package com.arad.care4pets;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddHealthRecordActivity extends AppCompatActivity {

    private HealthRecordViewModel healthRecordViewModel;
    private int petId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_record);

        // Read petId — tells us which pet this record belongs to
        petId = getIntent().getIntExtra("pet_id", 0);

        healthRecordViewModel = new ViewModelProvider(this).get(HealthRecordViewModel.class);

        EditText etRecordTitle       = findViewById(R.id.etRecordTitle);
        EditText etRecordDescription = findViewById(R.id.etRecordDescription);
        Button btnSave               = findViewById(R.id.btn_save_health_record);

        btnSave.setOnClickListener(v -> {
            String title       = etRecordTitle.getText() != null
                    ? etRecordTitle.getText().toString().trim() : "";
            String description = etRecordDescription.getText() != null
                    ? etRecordDescription.getText().toString().trim() : "";

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
                Toast.makeText(this, R.string.fill_required_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            // petId links this record to the correct pet
            HealthRecord record = new HealthRecord(title, description, "Health Notes", petId, null);
            healthRecordViewModel.insert(record);

            Toast.makeText(this, R.string.record_added, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}