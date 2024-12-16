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
    RecyclerView recyclerView;

    List<Excursion> sortedExcursions = new ArrayList<>();

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
            intent.putExtra("vacationId", vacationId); // Pass the vacation ID
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


        recyclerView = findViewById(R.id.vacationrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(repository.getAssociatedExcursions(vacationId));
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationformation, menu);
        return true;
    }


public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.vacationsave) {
        // Validate inputs
        if (editVacationName.getText().toString().isEmpty() ||
                editVacationHotel.getText().toString().isEmpty() ||
                startDate == null || endDate == null) {

            Toast.makeText(this, "Please fill in all fields before saving.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate date logic
        if (endDate.compareTo(startDate) < 0) {            Toast.makeText(this, "End date cannot be before start date.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Create or update Vacation object
        Vacation vacation = new Vacation(
                vacationId != -1 ? vacationId : generateVacationId(),
                editVacationName.getText().toString(),
                editVacationHotel.getText().toString(),
                startDate,
                endDate
        );

        // Insert or update Vacation in repository
        if (vacationId != -1) {
            repository.updateVacation(vacation);
        } else {
            repository.insertVacation(vacation);
        }

        // Provide user feedback
        Toast.makeText(this, "Vacation saved successfully!", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity
        return true;

    } else if (item.getItemId() == R.id.vacationdelete) {
        // Check if any excursions are associated with this vacation
        List<Excursion> associatedExcursions = repository.getAssociatedExcursions(vacationId);
        if (associatedExcursions != null && !associatedExcursions.isEmpty()) {
            Toast.makeText(this, "Cannot delete vacation with associated excursions.", Toast.LENGTH_SHORT).show();
            return false;
        }


        // Delete the vacation
        Vacation vacationToDelete = new Vacation(vacationId, null, null, null, null);
        repository.deleteVacation(vacationToDelete);
        Toast.makeText(this, "Vacation deleted successfully!", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity
        return true;
    }
    else if(item.getItemId() == R.id.vacationshare){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Vacation Name: " + vacationName + "\n" +
                "Hotel: " + vacationHotel + "\n" +
                "Start Date: " + startDate + "\n" +
                "End Date: " + endDate);
        intent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(intent, null);
        startActivity(shareIntent);
        return true;
    }

    return super.onOptionsItemSelected(item);
}

    // Helper method to generate a new vacation ID
    private int generateVacationId() {
        List<Vacation> allVacations = repository.getAllVacations();
        if (allVacations.isEmpty()) {
            return 1; // Start ID from 1 if no vacations exist
        }
        return allVacations.get(allVacations.size() - 1).getVacationId() + 1;
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

    @Override
    protected void onResume() {
        super.onResume();
        List<Excursion> allExcursions = repository.getAllExcursions();
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        excursionAdapter.setExcursions(allExcursions);
        excursionAdapter.setExcursions(repository.getAssociatedExcursions(vacationId));

//        List<Vacation> allVacations = repository.getAllVacations();
//        // Assuming there is a RecyclerView for vacations
//        RecyclerView vacationRecyclerView = findViewById(R.id.recyclerView);
//        final VacationAdapter vacationAdapter = new VacationAdapter(this);
//        vacationRecyclerView.setAdapter(vacationAdapter);
//        vacationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        vacationAdapter.setVacations(allVacations);
//    }

    }
}

