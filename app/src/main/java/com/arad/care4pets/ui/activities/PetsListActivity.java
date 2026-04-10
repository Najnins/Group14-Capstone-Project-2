package com.arad.care4pets.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arad.care4pets.ui.adapters.PetAdapter;
import com.arad.care4pets.viewmodel.PetViewModel;
import com.arad.care4pets.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PetsListActivity extends AppCompatActivity {

    private PetAdapter adapter;
    private PetViewModel petViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_list);

        RecyclerView recycler = findViewById(R.id.rvPets);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddPet);
        ImageButton btnBack = findViewById(R.id.btnBackMenu);
        Button btnCareInstructions = findViewById(R.id.btnCareInstructions);

        adapter = new PetAdapter(
                new ArrayList<>(),
                pet -> {
                    Intent intent = new Intent(PetsListActivity.this, PetProfileActivity.class);
                    intent.putExtra("pet", pet);
                    startActivity(intent);
                }
        );

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        petViewModel = new ViewModelProvider(this).get(PetViewModel.class);
        petViewModel.getAllPets().observe(this, pets -> adapter.setPets(pets));

        fabAdd.setOnClickListener(v ->
                startActivity(new Intent(PetsListActivity.this, AddPetActivity.class))
        );

        btnBack.setOnClickListener(v -> finish());
        btnCareInstructions.setOnClickListener(v -> {
            Intent intent = new Intent(this, CareInstructionsActivity.class);
            startActivity(intent);
        });
    }
}
