package com.example.smartpantrymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.model.PantryItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private final List<PantryItem> pantryItems;
    private final List<PantryItem> allPantryItems;

    public interface OnPantryItemActionListener {

        void onEdit(PantryItem item);

        void onDelete(PantryItem item);
    }

    private final OnPantryItemActionListener listener;

    public PantryAdapter(
            List<PantryItem> pantryItems,
            OnPantryItemActionListener listener) {

        this.pantryItems =
                new ArrayList<>(pantryItems);

        this.allPantryItems =
                new ArrayList<>(pantryItems);

        this.listener = listener;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
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

        holder.tvIngredientName.setText(
                item.getName()
        );

        String quantityText =
                "Quantity: "
                        + item.getQuantity()
                        + " "
                        + item.getUnit();

        holder.tvQuantity.setText(
                quantityText
        );

        String expiryDate =
                item.getExpiryDate();

        if (expiryDate == null
                || expiryDate.trim().isEmpty()) {

            holder.tvExpiry.setText(
                    "Expiry: Not specified"
            );

            holder.tvStatus.setText(
                    "Status: No expiry date"
            );

        } else {

            holder.tvExpiry.setText(
                    "Expiry: " + expiryDate
            );

            updateExpiryStatus(
                    holder.tvStatus,
                    expiryDate
            );
        }

        // Low-stock warning
        if (item.getQuantity() <= 1) {

            String currentStatus =
                    holder.tvStatus
                            .getText()
                            .toString();

            holder.tvStatus.setText(
                    currentStatus
                            + " • Low stock"
            );
        }

        // Edit button
        holder.btnEdit.setOnClickListener(
                v -> listener.onEdit(item)
        );

        // Delete button
        holder.btnDelete.setOnClickListener(
                v -> listener.onDelete(item)
        );
    }

    private void updateExpiryStatus(
            TextView statusView,
            String expiryDate) {

        try {

            LocalDate expiry =
                    LocalDate.parse(
                            expiryDate,
                            DateTimeFormatter.ISO_LOCAL_DATE
                    );

            LocalDate today =
                    LocalDate.now();

            if (expiry.isBefore(today)) {

                statusView.setText(
                        "Status: EXPIRED"
                );

            } else if (!expiry.isAfter(
                    today.plusDays(3))) {

                statusView.setText(
                        "Status: Expiring soon"
                );

            } else {

                statusView.setText(
                        "Status: Fresh"
                );
            }

        } catch (DateTimeParseException e) {

            statusView.setText(
                    "Status: Check expiry date"
            );
        }
    }

    @Override
    public int getItemCount() {

        return pantryItems.size();
    }

    // ---------------------------------------------------------
    // SEARCH / FILTER
    // ---------------------------------------------------------

    public void filter(String searchText) {

        String query =
                searchText
                        .trim()
                        .toLowerCase(Locale.ROOT);

        pantryItems.clear();

        if (query.isEmpty()) {

            pantryItems.addAll(
                    allPantryItems
            );

        } else {

            for (PantryItem item :
                    allPantryItems) {

                String ingredientName =
                        item.getName()
                                .toLowerCase(
                                        Locale.ROOT
                                );

                if (ingredientName.contains(query)) {

                    pantryItems.add(item);
                }
            }
        }

        notifyDataSetChanged();
    }

    // ---------------------------------------------------------
    // VIEW HOLDER
    // ---------------------------------------------------------

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvIngredientName;
        TextView tvQuantity;
        TextView tvExpiry;
        TextView tvStatus;

        Button btnEdit;
        Button btnDelete;

        public PantryViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvIngredientName =
                    itemView.findViewById(
                            R.id.tvIngredientName
                    );

            tvQuantity =
                    itemView.findViewById(
                            R.id.tvQuantity
                    );

            tvExpiry =
                    itemView.findViewById(
                            R.id.tvExpiry
                    );

            tvStatus =
                    itemView.findViewById(
                            R.id.tvStatus
                    );

            btnEdit =
                    itemView.findViewById(
                            R.id.btnEdit
                    );

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDelete
                    );
        }
    }
}