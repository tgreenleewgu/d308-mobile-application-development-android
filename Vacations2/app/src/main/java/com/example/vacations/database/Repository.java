package com.example.vacations.database;

import android.app.Application;
import android.view.Menu;

import com.example.vacations.dao.ExcursionDAO;
import com.example.vacations.dao.VacationDAO;
import com.example.vacations.entities.Excursion;
import com.example.vacations.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private  ExcursionDAO mExcursionDAO;
    private  VacationDAO mVacationDAO;

    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mExcursionDAO = db.excursionDao();
        mVacationDAO = db.vacationDao();
    }

    // Get all vacations
    public List<Vacation> getAllVacations() {
        databaseExecutor.execute(() -> {
            mAllVacations = mVacationDAO.getAllVacations();
        });
        waitForResult();
        return mAllVacations;
    }

    // Insert a vacation
    public void insertVacation(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.insert(vacation);
        });
        waitForResult();
    }

    // Update a vacation
    public void updateVacation(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.update(vacation);
        });
        waitForResult();
    }

    // Delete a vacation with validation
    public void deleteVacation(Vacation vacation) {
        databaseExecutor.execute(() -> {
            if (!mExcursionDAO.getAssociatedExcursions(vacation.getVacationId()).isEmpty()) {
                throw new IllegalStateException("Cannot delete vacation with associated excursions.");
            } else {
                mVacationDAO.delete(vacation);
            }
        });
        waitForResult();
    }

    // Get all excursions
    public List<Excursion> getAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        waitForResult();
        return mAllExcursions;
    }

    // Get excursions associated with a vacation
    public List<Excursion> getAssociatedExcursions(int vacationId) {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAssociatedExcursions(vacationId);
        });
        waitForResult();
        return mAllExcursions;
    }

    // Insert an excursion
    public void insertExcursion(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.insert(excursion);
        });
        waitForResult();
    }

    // Update an excursion
    public void updateExcursion(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.update(excursion);
        });
        waitForResult();
    }

    // Delete an excursion
    public void deleteExcursion(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.delete(excursion);
        });
        waitForResult();
    }

    public List<Excursion> getExcursionsByVacationId(int vacationId) {
        return mExcursionDAO.getExcursionsByVacationId(vacationId);
    }
    // Utility method to wait for background tasks to finish
    private void waitForResult() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public Menu getAssociatedAlerts(int excursionId) {
        return null;
    }
}


