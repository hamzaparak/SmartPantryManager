package com.example.smartpantrymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.adapters.PantryAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.PantryItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView pantryRecyclerView;
    private TextView emptyTitle;
    private TextView emptyMessage;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (view, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    view.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        pantryRecyclerView = findViewById(R.id.pantryRecyclerView);
        emptyTitle = findViewById(R.id.emptyTitle);
        emptyMessage = findViewById(R.id.emptyMessage);

        Button addIngredientButton =
                findViewById(R.id.addIngredientButton);

        databaseHelper = new DatabaseHelper(this);

        pantryRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        addIngredientButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {

        List<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        PantryAdapter adapter =
                new PantryAdapter(
                        this,
                        pantryItems
                );

        pantryRecyclerView.setAdapter(adapter);

        if (pantryItems.isEmpty()) {

            pantryRecyclerView.setVisibility(View.GONE);
            emptyTitle.setVisibility(View.VISIBLE);
            emptyMessage.setVisibility(View.VISIBLE);

        } else {

            pantryRecyclerView.setVisibility(View.VISIBLE);
            emptyTitle.setVisibility(View.GONE);
            emptyMessage.setVisibility(View.GONE);
        }
    }
}