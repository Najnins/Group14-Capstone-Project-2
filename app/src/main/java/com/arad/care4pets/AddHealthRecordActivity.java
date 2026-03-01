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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_record);

        healthRecordViewModel = new ViewModelProvider(this).get(HealthRecordViewModel.class);

        Button btnSave = findViewById(R.id.btn_save_health_record);
        EditText etRecordTitle = findViewById(R.id.etRecordTitle);
        EditText etRecordDescription = findViewById(R.id.etRecordDescription);

        btnSave.setOnClickListener(v -> {
            String recordTitle = etRecordTitle.getText() != null ? etRecordTitle.getText().toString().trim() : "";
            String description = etRecordDescription.getText() != null ? etRecordDescription.getText().toString().trim() : "";

            if (TextUtils.isEmpty(recordTitle) || TextUtils.isEmpty(description)) {
                Toast.makeText(this, R.string.fill_required_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new HealthRecord without an ID (Room will auto-generate it)
            // and assign a default category.
            HealthRecord healthRecord = new HealthRecord(recordTitle, description, "Health Notes");
            
            // Insert the new record into the database via the ViewModel
            healthRecordViewModel.insert(healthRecord);

            Toast.makeText(this, R.string.record_added, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
