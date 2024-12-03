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

import com.example.vacations.R;
import com.example.vacations.database.Repository;
import com.example.vacations.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        System.out.println(getIntent().getStringExtra("vacation"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       if(item.getItemId()==R.id.add_vacation){
           repository = new Repository(getApplication());
           Toast.makeText(Vacation_List.this, "Add Vacation", Toast.LENGTH_LONG).show();
           Vacation vacation1 = new Vacation(0, "Paris", "Le Meridien Etoile", "06/15/2024", "06/22/2024");
           repository.insertVacation(vacation1);

           Vacation vacation2 = new Vacation(0, "New York", "The Plaza Hotel", "09/01/2024", "09/07/2024");
           repository.insertVacation(vacation2);

           Vacation vacation3 = new Vacation(0, "Tokyo", "Shinjuku Granbell Hotel", "10/10/2024", "10/17/2024");
           repository.insertVacation(vacation3);

           Vacation vacation4 = new Vacation(0, "London", "The Ritz London", "01/05/2025", "01/12/2025");
           repository.insertVacation(vacation4);

           Vacation vacation5 = new Vacation(0, "Sydney", "The Langham", "03/20/2025", "03/27/2025");
           repository.insertVacation(vacation5);
           return true;


}


        return super.onOptionsItemSelected(item);
    }
}