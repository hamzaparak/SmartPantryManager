package com.example.smartpantrymanager.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.adapters.PantryAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.PantryItem;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView pantryRecyclerView;

    private TextView emptyTitle;
    private TextView emptyMessage;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        SharedPreferences preferences =
                getSharedPreferences(
                        "app_settings",
                        MODE_PRIVATE
                );

        boolean darkMode =
                preferences.getBoolean(
                        "dark_mode",
                        false
                );

        if (darkMode) {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
            );

        } else {

            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
            );
        }

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_main
        );

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (view, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat
                                            .Type
                                            .systemBars()
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

        pantryRecyclerView =
                findViewById(
                        R.id.pantryRecyclerView
                );

        emptyTitle =
                findViewById(
                        R.id.emptyTitle
                );

        emptyMessage =
                findViewById(
                        R.id.emptyMessage
                );

        drawerLayout =
                findViewById(
                        R.id.drawerLayout
                );

        navigationView =
                findViewById(
                        R.id.navigationView
                );

        Button menuButton =
                findViewById(
                        R.id.menuButton
                );

        Button addIngredientButton =
                findViewById(
                        R.id.addIngredientButton
                );

        databaseHelper =
                new DatabaseHelper(this);

        pantryRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        menuButton.setOnClickListener(v ->
                drawerLayout.openDrawer(
                        GravityCompat.START
                )
        );

        addIngredientButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            AddEditIngredientActivity.class
                    );

            startActivity(intent);
        });

        navigationView.setNavigationItemSelectedListener(
                item -> {

                    int itemId =
                            item.getItemId();

                    if (itemId ==
                            R.id.navPantry) {

                        drawerLayout.closeDrawer(
                                GravityCompat.START
                        );

                        return true;
                    }

                    if (itemId ==
                            R.id.navRecipes) {

                        Intent intent =
                                new Intent(
                                        MainActivity.this,
                                        SuggestedRecipesActivity.class
                                );

                        startActivity(intent);

                        drawerLayout.closeDrawer(
                                GravityCompat.START
                        );

                        return true;
                    }

                    if (itemId ==
                            R.id.navSettings) {

                        Intent intent =
                                new Intent(
                                        MainActivity.this,
                                        SettingsActivity.class
                                );

                        startActivity(intent);

                        drawerLayout.closeDrawer(
                                GravityCompat.START
                        );

                        return true;
                    }

                    return false;
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPantryItems();
    }

    private void loadPantryItems() {

        List<PantryItem> pantryItems =
                databaseHelper
                        .getAllPantryItems();

        PantryAdapter adapter =
                new PantryAdapter(
                        this,
                        pantryItems
                );

        pantryRecyclerView.setAdapter(
                adapter
        );

        if (pantryItems.isEmpty()) {

            pantryRecyclerView.setVisibility(
                    View.GONE
            );

            emptyTitle.setVisibility(
                    View.VISIBLE
            );

            emptyMessage.setVisibility(
                    View.VISIBLE
            );

        } else {

            pantryRecyclerView.setVisibility(
                    View.VISIBLE
            );

            emptyTitle.setVisibility(
                    View.GONE
            );

            emptyMessage.setVisibility(
                    View.GONE
            );
        }
    }
}