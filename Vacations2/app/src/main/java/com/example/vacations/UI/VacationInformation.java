package com.example.vacations.UI;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacations.R;
import com.example.vacations.database.Repository;
import com.example.vacations.entities.Excursion;
import com.example.vacations.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VacationInformation extends AppCompatActivity {
    String vacationName; // Variable to store vacation name
    String vacationHotel; // Variable to store vacation hotel
    int vacationId; // Variable to store vacation ID
    EditText editVacationName; // Variable to store vacation name
    EditText editVacationHotel; // Variable to store vacation hotel



    String startDate; // Variable to store start date
    String endDate;   // Variable to store end date
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

        editVacationName = findViewById(R.id.vacationName);
        editVacationHotel = findViewById(R.id.hoteltext);
        vacationName = getIntent().getStringExtra("VacationName");
        vacationHotel = getIntent().getStringExtra("VacationHotel");
        editVacationName.setText(vacationName);
        editVacationHotel.setText(vacationHotel);
        vacationId = getIntent().getIntExtra("Id", -1);
        startDate = getIntent().getStringExtra("StartDate");
        endDate = getIntent().getStringExtra("EndDate");
        if (startDate != null) startDateButton.setText(startDate);
        if (endDate != null) endDateButton.setText(endDate);


        RecyclerView recyclerView = findViewById(R.id.vacationrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        excursionAdapter.setExcursions(repository.getAllExcursions());
//        excursionAdapter.setExcursions(repository.getExcursionsByVacationId(vacationId));
        excursionAdapter.setExcursions(repository.getAssociatedExcursions(vacationId));
    }

//        RecyclerView recyclerView = findViewById(R.id.vacationrecyclerview);
//        repository = new Repository(getApplication());
//        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
//        recyclerView.setAdapter(excursionAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//// Filter excursions by vacationId
//        List<Excursion> filteredExcursions = new ArrayList<>();
//        for (Excursion currentExcursion : repository.getAllExcursions()) {
//            if (currentExcursion.getVacationId() == vacationId) { // Compare currentExcursion's vacationId
//                filteredExcursions.add(currentExcursion); // Add matching excursions
//            }
//        }
//
//// Set the filtered excursions to the adapter
//        excursionAdapter.setExcursions(filteredExcursions);
//    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationformation, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.vacationsave) {
//            Vacation vacation;
//            if (vacationId == -1) {
//                if (repository.getAllVacations().size() == 0) vacationId = 1;
//                else
//                    vacationId = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationId() + 1;
//                vacation = new Vacation(vacationId, editVacationName.getText().toString(), editVacationHotel.getText().toString(), startDate, endDate);
//
//            }
//            else {
//                vacation = new Vacation(vacationId, editVacationName.getText().toString(), editVacationHotel.getText().toString(), startDate, endDate);
//                repository.updateVacation(vacation);
//            }
//        }
//        return true;
//    }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.vacationsave) {
        // Validate inputs
        if (editVacationName.getText().toString().isEmpty() ||
                editVacationHotel.getText().toString().isEmpty() ||
                startDate == null ||
                endDate == null) {

            Toast.makeText(this, "Please fill in all fields before saving.", Toast.LENGTH_SHORT).show();
            return false; // Do not proceed with saving
        }

        Vacation vacation;

        if (vacationId != -1) {
            // Update existing vacation
            vacation = new Vacation(
                    vacationId,
                    editVacationName.getText().toString(),
                    editVacationHotel.getText().toString(),
                    startDate,
                    endDate
            );
            repository.updateVacation(vacation); // Update the vacation in the repository
        } else {
            // Create new vacation
            if (repository.getAllVacations().size() == 0) {
                vacationId = 1;
            } else {
                vacationId = repository.getAllVacations()
                        .get(repository.getAllVacations().size() - 1)
                        .getVacationId() + 1;
            }
            vacation = new Vacation(
                    vacationId,
                    editVacationName.getText().toString(),
                    editVacationHotel.getText().toString(),
                    startDate,
                    endDate
            );
            repository.insertVacation(vacation); // Insert new vacation
        }

        // Provide user feedback and close activity
        Toast.makeText(this, "Vacation saved successfully!", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity
    }
    return super.onOptionsItemSelected(item);
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