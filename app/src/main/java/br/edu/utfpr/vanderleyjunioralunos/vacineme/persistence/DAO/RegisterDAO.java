package br.edu.utfpr.vanderleyjunioralunos.vacineme.persistence.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Register;

@Dao
public interface RegisterDAO {

    @Insert
    long insert(Register register);

    @Delete
    void delete(Register register);

    @Update
    void update(Register register);

    @Query("SELECT * FROM registers WHERE vaccineId = :vaccineId AND personId = :personId")
    Register queryForId(long vaccineId, long personId);

    @Query("SELECT * FROM registers ORDER BY vaccineDate DESC")
    List<Register> queryAll();

    @Query("SELECT * FROM registers WHERE personId = :personId")
    List<Register> queryByPersonId(long personId);

}
