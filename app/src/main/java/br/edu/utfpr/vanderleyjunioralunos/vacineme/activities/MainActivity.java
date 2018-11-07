package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.PeopleSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.RegisterAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Register;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Vaccine;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.persistence.VacinemeDatabase;

public class MainActivity extends AppCompatActivity {

    private List<Register> registers;
    private List<Person> people;
    private ListView listViewRegisters;
    private Spinner spinnerPeople;
    private PeopleSpinnerAdapter spinnerAdapterPeople;
    private RegisterAdapter registerAdapter;
    public static int countVaccines;
    private int INSERT_PERSON_MODE = 1;
    private int INSERT_REGISTER_MODE = 2;

    //SharedPreferences
    private final String SELECTED_PERSON = "SELECTED_PERSON";
    public static final String PERSON_POSITION = "PERSON_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.welcome_to_vacineme));

        listViewRegisters = findViewById(R.id.listViewRegisters);
        people = new ArrayList<>();
        registers = new ArrayList<>();

        spinnerPeople = findViewById(R.id.spinnerPeople);
        insertDataSpinnerPeople(true);
    }

    private void setEventSpinnerPeople(){
        spinnerPeople.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        insertDataListViewRegisters();
                        savePreferences();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                }
        );
    }

    private void savePreferences(){
        SharedPreferences preferences = getSharedPreferences(SELECTED_PERSON, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PERSON_POSITION, spinnerPeople.getSelectedItemPosition());
        editor.commit();
    }

    private void readPreferences(){
        SharedPreferences preferences = getSharedPreferences(SELECTED_PERSON, Context.MODE_PRIVATE);
        int spinnerPeoplePosition = preferences.getInt(PERSON_POSITION, -1);
        spinnerPeople.setSelection(spinnerPeoplePosition);
    }

    private void clearPreferences(){
        SharedPreferences preferences = getSharedPreferences(SELECTED_PERSON, Context.MODE_PRIVATE);
        preferences.edit().remove(PERSON_POSITION);
        preferences.edit().commit();
        if(!people.isEmpty()){
            spinnerPeople.setSelection(0);
        }
    }

    private void insertDataSpinnerPeople(final boolean flag){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                people = VacinemeDatabase.getDatabase(MainActivity.this).personDAO().queryAll();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinnerAdapterPeople = new PeopleSpinnerAdapter(MainActivity.this, people);
                        spinnerPeople.setAdapter(spinnerAdapterPeople);
                        setEventSpinnerPeople();
                        if(flag){
                            readPreferences();
                        }
                        insertDataListViewRegisters();
                    }
                });
            }
        });
    }

    private void insertDataListViewRegisters() {
        if(!people.isEmpty()){
            final int personId = ((Person)spinnerPeople.getSelectedItem()).getId();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    registers = VacinemeDatabase.getDatabase(MainActivity.this).registerDAO().queryByPersonId(personId);
                    for(Register r : registers){
                        Vaccine v = VacinemeDatabase.getDatabase(MainActivity.this).vaccineDAO().queryForId(r.getVaccineId());
                        r.setVaccine(v);
                    }
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            registerAdapter = new RegisterAdapter(MainActivity.this, registers);
                            listViewRegisters.setAdapter(registerAdapter);
                        }
                    });
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentMenu;
        switch (item.getItemId()) {
            case R.id.menuItemPeople:
                intentMenu = new Intent(this, PeopleActivity.class);
                startActivityForResult(intentMenu, INSERT_PERSON_MODE);
                return true;
            case R.id.menuItemVaccines:
                intentMenu = new Intent(this, VaccinesActivity.class);
                startActivity(intentMenu);
                return true;
            case R.id.menuItemAbout:
                intentMenu = new Intent(this, AboutActivity.class);
                startActivity(intentMenu);
                return true;
            case R.id.addNewRegister:
                intentMenu = new Intent(this, AddNewRegisterActivity.class);
                if(verifyBeforeAddNewRegister()){
                    if(spinnerPeople.getSelectedItem()!=null){
                        intentMenu.putExtra(PERSON_POSITION, spinnerPeople.getSelectedItemPosition());
                    }
                    startActivityForResult(intentMenu, INSERT_REGISTER_MODE);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == INSERT_PERSON_MODE){
            insertDataSpinnerPeople(false);
            clearPreferences();
        }
        if(resultCode == RESULT_OK && requestCode == INSERT_REGISTER_MODE){
            insertDataListViewRegisters();
        }
    }

    private int countVaccines(){
        try {
            Thread thr = new Thread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.countVaccines = VacinemeDatabase.getDatabase(MainActivity.this).vaccineDAO().queryCount();
                }
            });
            thr.start();
            thr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return MainActivity.countVaccines;
    }

    private boolean verifyBeforeAddNewRegister(){
        if(countVaccines() == 0 || people.size() == 0){
            Toast.makeText(this, R.string.you_need_to_add_a_person_and_a_vaccine_before, Toast.LENGTH_SHORT).show();
            MainActivity.countVaccines = 0;
            return false;
        }
        MainActivity.countVaccines = 0;
        return true;
    }

}
