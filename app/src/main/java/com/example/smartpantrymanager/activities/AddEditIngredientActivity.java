package com.example.smartpantrymanager.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.PantryItem;

import java.util.Calendar;
import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText quantityInput;
    private EditText expiryInput;

    private Spinner unitSpinner;

    private TextView screenTitle;

    private Button saveButton;
    private Button deleteButton;
    private Button backButton;

    private DatabaseHelper databaseHelper;

    private int itemId = -1;

    private PantryItem existingItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_add_edit_ingredient
        );

        nameInput = findViewById(R.id.nameInput);
        quantityInput = findViewById(R.id.quantityInput);
        expiryInput = findViewById(R.id.expiryInput);

        unitSpinner = findViewById(R.id.unitSpinner);

        screenTitle = findViewById(R.id.screenTitle);

        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        backButton = findViewById(R.id.backButton);

        databaseHelper = new DatabaseHelper(this);

        setupUnits();

        itemId = getIntent().getIntExtra(
                "ITEM_ID",
                -1
        );

        if (itemId != -1) {
            loadExistingItem();
        } else {
            deleteButton.setVisibility(View.GONE);
        }

        expiryInput.setOnClickListener(v ->
                showDatePicker()
        );

        saveButton.setOnClickListener(v ->
                saveIngredient()
        );

        deleteButton.setOnClickListener(v ->
                confirmDelete()
        );

        backButton.setOnClickListener(v ->
                finish()
        );
    }

    private void setupUnits() {

        String[] units = {
                "pcs",
                "g",
                "kg",
                "ml",
                "L",
                "slices"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        units
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        unitSpinner.setAdapter(adapter);
    }

    private void loadExistingItem() {

        existingItem =
                databaseHelper.getPantryItem(itemId);

        if (existingItem == null) {
            finish();
            return;
        }

        screenTitle.setText("Edit Ingredient");

        nameInput.setText(existingItem.getName());

        quantityInput.setText(
                String.valueOf(
                        existingItem.getQuantity()
                )
        );

        expiryInput.setText(
                existingItem.getExpiryDate()
        );

        for (int i = 0;
             i < unitSpinner.getCount();
             i++) {

            if (unitSpinner
                    .getItemAtPosition(i)
                    .toString()
                    .equals(existingItem.getUnit())) {

                unitSpinner.setSelection(i);
                break;
            }
        }

        deleteButton.setVisibility(View.VISIBLE);
    }

    private void showDatePicker() {

        Calendar calendar =
                Calendar.getInstance();

        DatePickerDialog dialog =
                new DatePickerDialog(
                        this,
                        (view, year, month, day) -> {

                            String date =
                                    String.format(
                                            Locale.getDefault(),
                                            "%02d/%02d/%04d",
                                            day,
                                            month + 1,
                                            year
                                    );

                            expiryInput.setText(date);
                        },
                        calendar.get(
                                Calendar.YEAR
                        ),
                        calendar.get(
                                Calendar.MONTH
                        ),
                        calendar.get(
                                Calendar.DAY_OF_MONTH
                        )
                );

        dialog.show();
    }

    private void saveIngredient() {

        String name =
                nameInput.getText()
                        .toString()
                        .trim();

        String quantityText =
                quantityInput.getText()
                        .toString()
                        .trim();

        String unit =
                unitSpinner
                        .getSelectedItem()
                        .toString();

        String expiryDate =
                expiryInput.getText()
                        .toString()
                        .trim();

        if (name.isEmpty()) {

            nameInput.setError(
                    "Ingredient name is required"
            );

            return;
        }

        if (quantityText.isEmpty()) {

            quantityInput.setError(
                    "Quantity is required"
            );

            return;
        }

        double quantity;

        try {

            quantity =
                    Double.parseDouble(
                            quantityText
                    );

        } catch (NumberFormatException e) {

            quantityInput.setError(
                    "Enter a valid quantity"
            );

            return;
        }

        if (quantity <= 0) {

            quantityInput.setError(
                    "Quantity must be greater than 0"
            );

            return;
        }

        if (itemId == -1) {

            PantryItem newItem =
                    new PantryItem(
                            name,
                            quantity,
                            unit,
                            expiryDate
                    );

            long result =
                    databaseHelper
                            .addPantryItem(newItem);

            if (result != -1) {

                Toast.makeText(
                        this,
                        "Ingredient added",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Could not add ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            existingItem.setName(name);
            existingItem.setQuantity(quantity);
            existingItem.setUnit(unit);
            existingItem.setExpiryDate(expiryDate);

            int result =
                    databaseHelper
                            .updatePantryItem(
                                    existingItem
                            );

            if (result > 0) {

                Toast.makeText(
                        this,
                        "Ingredient updated",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Could not update ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private void confirmDelete() {

        new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage(
                        "Are you sure you want to delete ingredient?"
                )
                .setPositiveButton(
                        "delete",
                        (dialog, which) ->
                                deleteIngredient()
                )
                .setNegativeButton(
                        "cancel",
                        null
                )
                .show();
    }

    private void deleteIngredient() {

        int result =
                databaseHelper
                        .deletePantryItem(itemId);

        if (result > 0) {

            Toast.makeText(
                    this,
                    "ingredient deleted",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "could not delete ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}