package com.example.smartpantrymanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.activities.AddEditIngredientActivity;
import com.example.smartpantrymanager.models.PantryItem;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private final Context context;
    private final List<PantryItem> pantryItems;

    public PantryAdapter(Context context, List<PantryItem> pantryItems) {
        this.context = context;
        this.pantryItems = pantryItems;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        PantryItem item = pantryItems.get(position);

        holder.nameText.setText(item.getName());

        holder.quantityText.setText(
                item.getQuantity() + " " + item.getUnit()
        );

        if (item.getExpiryDate() == null ||
                item.getExpiryDate().trim().isEmpty()) {

            holder.expiryText.setText("Expiry:not set");

        } else {

            holder.expiryText.setText(
                    "Expry: " + item.getExpiryDate()
            );
        }

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    AddEditIngredientActivity.class
            );

            intent.putExtra(
                    "ITEM_ID",
                    item.getId()
            );

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView nameText;
        TextView quantityText;
        TextView expiryText;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.itemName);
            quantityText = itemView.findViewById(R.id.itemQuantity);
            expiryText = itemView.findViewById(R.id.itemExpiry);
        }
    }
}