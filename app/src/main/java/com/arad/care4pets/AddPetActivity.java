package com.arad.care4pets;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddPetActivity extends AppCompatActivity {

    private PetViewModel petViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        petViewModel = new ViewModelProvider(this).get(PetViewModel.class);

        EditText etName = findViewById(R.id.etPetName);
        EditText etSpecies = findViewById(R.id.etPetSpecies);
        EditText etAge = findViewById(R.id.etPetAge);
        EditText etNotes = findViewById(R.id.etPetNotes);
        Button btnSave = findViewById(R.id.btnSavePet);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String species = etSpecies.getText().toString().trim();
            String ageText = etAge.getText().toString().trim();
            String notes = etNotes.getText().toString().trim();

            if (name.isEmpty() || species.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Pet pet = new Pet(name, species, Integer.parseInt(ageText), notes, 0, 0);
            petViewModel.insert(pet);

            Toast.makeText(this, "Pet added", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
