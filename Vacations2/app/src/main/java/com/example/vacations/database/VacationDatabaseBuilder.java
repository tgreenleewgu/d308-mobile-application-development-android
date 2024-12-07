package com.example.vacations.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vacations.dao.ExcursionDAO;
import com.example.vacations.dao.VacationDAO;
import com.example.vacations.entities.Excursion;
import com.example.vacations.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 6, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDao();
    public abstract ExcursionDAO excursionDao();
    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VacationDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VacationDatabaseBuilder.class, "vacation_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
