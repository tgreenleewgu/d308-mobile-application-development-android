package com.example.vacations.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacations.R;
import com.example.vacations.database.Repository;
import com.example.vacations.entities.Excursion;
import com.example.vacations.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ExcursionInformation extends AppCompatActivity {

    String excursionName;
    String excursionDate;
    int excursionId;
    int vacationId;
    EditText editExcursionName;
    TextView editExcursionDate;
    Repository repository;
    final Calendar myCalendar = Calendar.getInstance();
    Random rand = new Random();
    int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new Repository(getApplication());

        editExcursionName = findViewById(R.id.excursionName);
        editExcursionDate = findViewById(R.id.excursionDate);

        // Retrieve data from Intent
        excursionName = getIntent().getStringExtra("excursionName");
        excursionId = getIntent().getIntExtra("excursionId", -1);
        vacationId = getIntent().getIntExtra("vacationId", -1);
        excursionDate = getIntent().getStringExtra("excursionDate");

        // Populate fields
        if (excursionName != null) editExcursionName.setText(excursionName);
        if (excursionDate != null) {
            editExcursionDate.setText(excursionDate);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
                Date date = sdf.parse(excursionDate);
                myCalendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Set DatePicker dialog
        editExcursionDate.setOnClickListener(v -> new DatePickerDialog(
                ExcursionInformation.this,
                (view, year, month, dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                },
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show());
    }

    /**
     * Updates the excursion date field with the selected date.
     */
    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        editExcursionDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursioninformation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.excursionsave) {
            // Save Excursion logic
            String excursionTitle = editExcursionName.getText().toString();
            String excursionDateString = editExcursionDate.getText().toString();

            if (excursionTitle.isEmpty() || excursionDateString.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields before saving.", Toast.LENGTH_SHORT).show();
                return false;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
            try {
                Date date = sdf.parse(excursionDateString);

                // Ensure excursion date is within the vacation period
                Vacation vacation = null;
                for (Vacation vac : repository.getAllVacations()) {
                    if (vac.getVacationId() == vacationId) {
                        vacation = vac;
                        break;
                    }
                }

                if (vacation != null) {
                    Date startDate = sdf.parse(vacation.getStartDate());
                    Date endDate = sdf.parse(vacation.getEndDate());

                    if (date.before(startDate) || date.after(endDate)) {
                        Toast.makeText(this, "Excursion date must be within the vacation's start and end dates.", Toast.LENGTH_LONG).show();
                        return true;
                    }
                }

                Excursion excursion;
                if (excursionId == -1) {
                    // Insert new excursion
                    excursionId = rand.nextInt(99999);
                    excursion = new Excursion(excursionId, excursionTitle, vacationId, excursionDateString);
                    repository.insertExcursion(excursion);
                } else {
                    // Update existing excursion
                    excursion = new Excursion(excursionId, excursionTitle, vacationId, excursionDateString);
                    repository.updateExcursion(excursion);
                }

                Toast.makeText(this, "Excursion saved successfully!", Toast.LENGTH_SHORT).show();
                finish();

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        } else if (item.getItemId() == R.id.excursiondelete) {
            // Delete Excursion logic
            Excursion currentExcursion = null;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getExcursionId() == excursionId) { // Fixed method call and syntax
                    currentExcursion = excursion;
                    break;
                }
            }

            if (currentExcursion != null) {
                repository.deleteExcursion(currentExcursion);
                Toast.makeText(this, currentExcursion.getExcursionName() + " was deleted", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Excursion not found!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}