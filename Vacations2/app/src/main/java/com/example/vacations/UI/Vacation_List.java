package com.example.vacations.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.List;

public class Vacation_List extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Vacation_List.this, VacationInformation.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        repository = new Repository(getApplication());
        List<Vacation> vacations = repository.getAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(vacations);
        //System.out.println(getIntent().getStringExtra("vacation"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Vacation> allvacations = repository.getAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allvacations);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       if(item.getItemId()==R.id.add_vacation){
           Vacation vacation1 = new Vacation(0, "Paris", "Le Meridien Etoile", "06/15/2024", "06/22/2024");
           repository.insertVacation(vacation1);

           // Excursions for Paris
           Excursion excursion1 = new Excursion(0, "Eiffel Tower", 1, "06/16/2024");
           repository.insertExcursion(excursion1);
           Excursion excursion2 = new Excursion(0, "Louvre Museum", 1, "06/17/2024");
           repository.insertExcursion(excursion2);

           Vacation vacation2 = new Vacation(0, "New York", "The Plaza Hotel", "09/01/2024", "09/07/2024");
           repository.insertVacation(vacation2);

           // Excursions for New York
           Excursion excursion3 = new Excursion(0, "Statue of Liberty", 2, "09/02/2024");
           repository.insertExcursion(excursion3);
           Excursion excursion4 = new Excursion(0, "Central Park", 2, "09/03/2024");
           repository.insertExcursion(excursion4);

           Vacation vacation3 = new Vacation(0, "Tokyo", "Shinjuku Granbell Hotel", "10/10/2024", "10/17/2024");
           repository.insertVacation(vacation3);

           // Excursions for Tokyo
           Excursion excursion5 = new Excursion(0, "Tokyo Tower", 3, "10/11/2024");
           repository.insertExcursion(excursion5);
           Excursion excursion6 = new Excursion(0, "Shibuya Crossing", 3, "10/12/2024");
           repository.insertExcursion(excursion6);

           Vacation vacation4 = new Vacation(0, "London", "The Ritz London", "01/05/2025", "01/12/2025");
           repository.insertVacation(vacation4);

           // Excursions for London
           Excursion excursion7 = new Excursion(0, "Big Ben", 4, "01/06/2025");
           repository.insertExcursion(excursion7);
           Excursion excursion8 = new Excursion(0, "London Eye", 4, "01/07/2025");
           repository.insertExcursion(excursion8);

           Vacation vacation5 = new Vacation(0, "Sydney", "The Langham", "03/20/2025", "03/27/2025");
           repository.insertVacation(vacation5);

           // Excursions for Sydney
           Excursion excursion9 = new Excursion(0, "Sydney Opera House", 5, "03/21/2025");
           repository.insertExcursion(excursion9);
           Excursion excursion10 = new Excursion(0, "Bondi Beach", 5, "03/22/2025");
           repository.insertExcursion(excursion10);

           return true;


}


        return super.onOptionsItemSelected(item);
    }
}