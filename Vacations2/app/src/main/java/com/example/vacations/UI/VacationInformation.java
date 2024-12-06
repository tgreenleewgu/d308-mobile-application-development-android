package com.example.vacations.UI;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacations.R;
import com.example.vacations.database.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class VacationInformation extends AppCompatActivity {

    private String startDate; // Variable to store start date
    private String endDate;   // Variable to store end date
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(VacationInformation.this, ExcursionInformation.class);
            startActivity(intent);
        });
        // Initialize buttons
        Button startDateButton = findViewById(R.id.startDate);
        Button endDateButton = findViewById(R.id.endDate);

        // Attach click listeners to buttons
        startDateButton.setOnClickListener(v -> showDatePicker(startDateButton));
        endDateButton.setOnClickListener(v -> showDatePicker(endDateButton));

//        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerView);
//        repository = new Repository(getApplication());
//        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
//        recyclerView.setAdapter(excursionAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        excursionAdapter.setExcursions(repository.getAllExcursions());
    }


    /**
     * Opens a DatePickerDialog, updates the button text, and saves the selected date.
     *
     * @param button The button that triggered the date picker.
     */
    private void showDatePicker(Button button) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format and display the selected date on the button
                    String selectedDate = formatDate(selectedYear, selectedMonth, selectedDay);
                    button.setText(selectedDate);

                    // Save the selected date based on the button ID
                    saveDate(button.getId(), selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    /**
     * Formats the date into MM/dd/yyyy format.
     *
     * @param year  The selected year.
     * @param month The selected month (0-based).
     * @param day   The selected day.
     * @return A formatted date string.
     */
    @SuppressLint("DefaultLocale")
    private String formatDate(int year, int month, int day) {
        return String.format("%02d/%02d/%04d", month + 1, day, year);
    }

    /**
     * Saves the selected date to the appropriate variable based on the button ID.
     *
     * @param buttonId The ID of the button.
     * @param date     The selected date string.
     */
    private void saveDate(int buttonId, String date) {
        if (buttonId == R.id.startDate) {
            startDate = date; // Save to start date
        } else if (buttonId == R.id.endDate) {
            endDate = date; // Save to end date
        }
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}