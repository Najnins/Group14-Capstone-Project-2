package com.arad.care4pets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RemindersActivity extends AppCompatActivity implements ReminderAdapter.OnReminderListener {

    private ReminderAdapter adapter;
    private TextView tvRemindersSubtitle;
    private MaterialButtonToggleGroup toggleButtonFilter;
    private ReminderViewModel reminderViewModel;

    private List<Reminder> allReminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        RecyclerView rvReminders = findViewById(R.id.rvReminders);
        tvRemindersSubtitle = findViewById(R.id.tvRemindersSubtitle);
        toggleButtonFilter = findViewById(R.id.toggleButtonFilter);
        ExtendedFloatingActionButton fabAddReminder = findViewById(R.id.fabAddReminder);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        fabAddReminder.setOnClickListener(v ->
                startActivity(new Intent(RemindersActivity.this, AddReminderActivity.class)));

        adapter = new ReminderAdapter(new ArrayList<>(), this);
        rvReminders.setLayoutManager(new LinearLayoutManager(this));
        rvReminders.setAdapter(adapter);

        reminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);
        reminderViewModel.getAllReminders().observe(this, reminders -> {
            allReminders = reminders;
            filterReminders(toggleButtonFilter.getCheckedButtonId());
            updateSubtitle();
        });

        toggleButtonFilter.check(R.id.btnAll);
        toggleButtonFilter.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                filterReminders(checkedId);
            }
        });

        updateSubtitle();
    }

    private void filterReminders(int checkedId) {
        List<Reminder> filteredReminders = new ArrayList<>();
        if (checkedId == R.id.btnAll) {
            filteredReminders.addAll(allReminders);
        } // Add filtering logic for other buttons here in the future
        adapter.setReminders(filteredReminders);
    }

    private void updateSubtitle() {
        int count = allReminders.size();
        tvRemindersSubtitle.setText(getResources().getQuantityString(R.plurals.active_reminders, count, count));
    }

    @Override
    public void onEditClick(Reminder reminder) {
        Intent intent = new Intent(this, EditReminderActivity.class);
        intent.putExtra("reminder_to_edit", reminder);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Reminder reminder) {
        // For Room, you should delete via the ViewModel
        // reminderViewModel.delete(reminder);
        Toast.makeText(this, reminder.getTitle() + " deleted", Toast.LENGTH_SHORT).show();
    }
}
