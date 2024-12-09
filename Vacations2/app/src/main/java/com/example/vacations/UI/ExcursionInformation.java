package com.example.vacations.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacations.R;
import com.example.vacations.database.Repository;

public class ExcursionInformation extends AppCompatActivity {

//    String excursionName; // Variable to store excursion name
//    String excursionDate; // Variable to store excursion date
//    int excursionId; // Variable to store excursion ID
//    int vacationId; // Variable to store vacation ID
//    EditText editExcursionName; // Variable to store excursion name
//    EditText editExcursionDate; // Variable to store excursion date
//    EditText editexcursionAlert;
//    Repository repository;
//    DatePickerDialog.OnDateSetListener startDate;


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
//        repository = new Repository(getApplication());
//        excursionName = getIntent().getStringExtra("excursionName");
//        editExcursionName.setText(getIntent().getStringExtra("excursionName"));
//        editExcursionName.setText(excursionName);


    }
}