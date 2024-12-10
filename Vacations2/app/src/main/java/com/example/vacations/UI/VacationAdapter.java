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
import com.example.vacations.entities.Vacation;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;
        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Vacation current = mVacations.get(position);
                    Intent intent = new Intent(context, VacationInformation.class);
                    intent.putExtra("Id", current.getVacationId());
                    intent.putExtra("VacationName", current.getVacationName());
                    intent.putExtra("VacationHotel", current.getVacationHotel());
                    intent.putExtra("StartDate", current.getStartDate());
                    intent.putExtra("EndDate", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation_list_item, parent, false);
        return new VacationViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if(mVacations != null){
            Vacation current = mVacations.get(position);
            String VacationName = current.getVacationName();
            holder.vacationItemView.setText(current.getVacationName());
        } else {
            holder.vacationItemView.setText("No Vacation");
        }

    }

    @Override
    public int getItemCount() {
        if(mVacations != null){
            return mVacations.size();
        } else {
            return 0;
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    public void setVacations(List<Vacation> vacations) {
        mVacations = vacations;
        notifyDataSetChanged();
    }

}
