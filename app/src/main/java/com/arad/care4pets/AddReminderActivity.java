package com.arad.care4pets;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddReminderActivity extends AppCompatActivity {

    private TextInputEditText etTitle;
    private TextInputEditText etDate;
    private TextInputEditText etTime;
    private TextInputEditText etNotes;
    private SwitchMaterial switchRepeat;
    private MaterialButtonToggleGroup toggleButtonPriority;

    private final Calendar calendar = Calendar.getInstance();
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        repository = new AppRepository(getApplication());

        etTitle = findViewById(R.id.etReminderTitle);
        etDate = findViewById(R.id.etReminderDate);
        etTime = findViewById(R.id.etReminderTime);
        etNotes = findViewById(R.id.etNotes);
        switchRepeat = findViewById(R.id.switchRepeat);
        toggleButtonPriority = findViewById(R.id.toggleButtonPriority);
        Button btnSave = findViewById(R.id.btnSaveReminder);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        etDate.setOnClickListener(v -> showDatePicker());
        etTime.setOnClickListener(v -> showTimePicker());

        btnSave.setOnClickListener(v -> saveReminder());

        // Set a default priority
        toggleButtonPriority.check(R.id.btnLow);
    }

    private void showDatePicker() {
        new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateInView();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void showTimePicker() {
        new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    updateTimeInView();
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
        ).show();
    }

    private void updateDateInView() {
        String myFormat = "MM/dd/yy"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etDate.setText(sdf.format(calendar.getTime()));
    }

    private void updateTimeInView() {
        String myFormat = "hh:mm a"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etTime.setText(sdf.format(calendar.getTime()));
    }

    private void saveReminder() {
        String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
        String date = etDate.getText() != null ? etDate.getText().toString().trim() : "";
        String time = etTime.getText() != null ? etTime.getText().toString().trim() : "";
        String notes = etNotes.getText() != null ? etNotes.getText().toString().trim() : "";

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int priorityButtonId = toggleButtonPriority.getCheckedButtonId();
        String priority = "Low"; // Default
        if (priorityButtonId == R.id.btnMedium) {
            priority = "Medium";
        } else if (priorityButtonId == R.id.btnHigh) {
            priority = "High";
        }

        boolean isRepeating = switchRepeat.isChecked();

        Reminder reminder = new Reminder(title, date, time, priority, isRepeating, notes);
        repository.insert(reminder);

        Toast.makeText(this, "Reminder created", Toast.LENGTH_SHORT).show();
        finish();
    }
}
