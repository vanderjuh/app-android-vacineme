package br.edu.utfpr.vanderleyjunioralunos.vacineme.persistence.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;

@Dao
public interface VaccineDAO {

    @Insert
    long insert(Vaccine vaccine);

    @Delete
    void delete(Vaccine vaccine);

    @Update
    void update(Vaccine vaccine);

    @Query("SELECT * FROM vaccines WHERE id = :id")
    Vaccine queryForId(long id);

    @Query("SELECT * FROM vaccines ORDER BY description ASC")
    List<Vaccine> queryAll();

}
