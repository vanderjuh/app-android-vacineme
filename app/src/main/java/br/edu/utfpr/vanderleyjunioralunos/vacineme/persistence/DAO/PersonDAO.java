package br.edu.utfpr.vanderleyjunioralunos.vacineme.persistence.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Person;

@Dao
public interface PersonDAO {

    @Insert
    long insert(Person person);

    @Delete
    void delete(Person person);

    @Update
    void update(Person person);

    @Query("SELECT * FROM people WHERE id = :id")
    Person queryForId(long id);

    @Query("SELECT * FROM people ORDER BY name ASC")
    List<Person> queryAll();

}
