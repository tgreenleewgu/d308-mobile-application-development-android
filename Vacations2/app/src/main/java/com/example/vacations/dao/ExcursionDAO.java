package com.example.vacations.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vacations.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM excursions ORDER BY excursionId ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM excursions WHERE vacationId = :vacationId ORDER BY excursionId ASC")
    List<Excursion> getExcursionsByVacationId(int vacationId);
}
