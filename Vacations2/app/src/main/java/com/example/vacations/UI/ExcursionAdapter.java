package com.example.vacations.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacations.R;
import com.example.vacations.entities.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;  // List of excursions
    private final Context context;
    private final LayoutInflater mInflater;

    // ViewHolder class for handling currentExcursion items
    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        private final TextView excursionItemView2;

        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView3);
            excursionItemView2 = itemView.findViewById(R.id.textView4);

            // Set up the item click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Excursion current = mExcursions.get(position);  // Get the currentExcursion at that position
                    Intent intent = new Intent(context, ExcursionInformation.class);
                    intent.putExtra("excursionId", current.getExcursionId());
                    intent.putExtra("excursionName", current.getExcursionName());
                    intent.putExtra("excursionDate", current.getExcursionDate());
                    intent.putExtra("vacationId", current.getVacationId());
                    context.startActivity(intent);  // Start the new activity
                }
            });
        }

        public TextView getExcursionItemView() {
            return excursionItemView;
        }

        public TextView getExcursionItemView2() {
            return excursionItemView2;
        }
    }

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            // Get the excursion at the current position
            Excursion current = mExcursions.get(position);
            String excursionName = current.getExcursionName();
            String excursionDate = current.getExcursionDate();
            int vacationId = current.getVacationId();
            // Set the excursion name and date to the TextView
            holder.getExcursionItemView().setText(current.getExcursionName());
            holder.getExcursionItemView2().setText(current.getExcursionDate());
        } else {
            // If the data is not ready yet
            holder.getExcursionItemView().setText("No Excursion");
            holder.getExcursionItemView2().setText("No Date");
        }

    }

    @Override
    public int getItemCount() {
        if (mExcursions != null) {
            return mExcursions.size();  // Return the size of the excursions list
        } else {
            return 0;  // Return 0 if the list is empty
        }
    }

    // Method to set the list of excursions
    @SuppressLint("NotifyDataSetChanged")
    public void setExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }
}