package br.edu.utfpr.vanderleyjunioralunos.vacineme.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.concurrent.Executors;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.persistence.DAO.PersonDAO;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.persistence.DAO.VaccineDAO;

@Database(entities = {Vaccine.class, Person.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class VacinemeDatabase extends RoomDatabase {

    public abstract VaccineDAO vaccineDAO();
    public abstract PersonDAO personDAO();

    private static VacinemeDatabase instance;

    public static VacinemeDatabase getDatabase(final Context context){
        if(instance == null){
            synchronized (VacinemeDatabase.class){
                if(instance == null){
                    Builder builder = Room.databaseBuilder(context, VacinemeDatabase.class, "vacineme.db");
                    builder.addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    //carregaVacinasIniciais(context);
                                }
                            });
                        }
                    });
                    instance = (VacinemeDatabase) builder.build();
                }
            }
        }
        return instance;
    }

    private static void carregaVacinasIniciais(final Context context){
        instance.vaccineDAO().insert(new Vaccine(
                "Flu",
                "Teste",
                "FD64d"
        ));
    }

}
