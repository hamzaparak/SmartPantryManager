package com.example.smartpantrymanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.activities.AddEditIngredientActivity;
import com.example.smartpantrymanager.models.PantryItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
                .inflate(
                        R.layout.item_pantry,
                        parent,
                        false
                );

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        PantryItem item =
                pantryItems.get(position);

        holder.nameText.setText(
                item.getName()
        );

        holder.quantityText.setText(
                item.getQuantity() +
                        " " +
                        item.getUnit()
        );

        updateExpiryStatus(
                holder.expiryText,
                item.getExpiryDate()
        );

        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
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

    private void updateExpiryStatus(
            TextView expiryText,
            String expiryDate) {

        if (expiryDate == null ||
                expiryDate.trim().isEmpty()) {

            expiryText.setText(
                    "Expiry: Not set"
            );

            expiryText.setTextColor(
                    ContextCompat.getColor(
                            context,
                            R.color.light_secondary_text
                    )
            );

            return;
        }

        SimpleDateFormat dateFormat =
                new SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                );

        dateFormat.setLenient(false);

        try {

            Date expiry =
                    dateFormat.parse(
                            expiryDate
                    );

            Date today =
                    new Date();

            if (expiry == null) {
                return;
            }

            long difference =
                    expiry.getTime() -
                            today.getTime();

            long daysRemaining =
                    TimeUnit.MILLISECONDS
                            .toDays(
                                    difference
                            );

            if (daysRemaining < 0) {

                expiryText.setText(
                        "Expired: " +
                                expiryDate
                );

                expiryText.setTextColor(
                        ContextCompat.getColor(
                                context,
                                R.color.expired_text
                        )
                );

            } else if (daysRemaining <= 7) {

                expiryText.setText(
                        "Expires soon: " +
                                expiryDate
                );

                expiryText.setTextColor(
                        ContextCompat.getColor(
                                context,
                                R.color.expiry_warning
                        )
                );

            } else {

                expiryText.setText(
                        "Expiry: " +
                                expiryDate
                );

                expiryText.setTextColor(
                        ContextCompat.getColor(
                                context,
                                R.color.light_secondary_text
                        )
                );
            }

        } catch (ParseException e) {

            expiryText.setText(
                    "Expiry: " +
                            expiryDate
            );

            expiryText.setTextColor(
                    ContextCompat.getColor(
                            context,
                            R.color.light_secondary_text
                    )
            );
        }
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

        public PantryViewHolder(
                @NonNull View itemView) {

            super(itemView);

            nameText =
                    itemView.findViewById(
                            R.id.itemName
                    );

            quantityText =
                    itemView.findViewById(
                            R.id.itemQuantity
                    );

            expiryText =
                    itemView.findViewById(
                            R.id.itemExpiry
                    );
        }
    }
}