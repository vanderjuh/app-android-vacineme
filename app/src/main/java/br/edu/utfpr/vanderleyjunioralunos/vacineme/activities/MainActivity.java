package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Date;
import java.util.List;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.PeopleSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.RegisterAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Register;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Relationship;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;

public class MainActivity extends AppCompatActivity {

    private static List<Register> registers;
    private static List<Person> people;
    private static List<Vaccine> vaccines;
    private ListView listViewRegisters;
    private Spinner spinnerPeople;
    private static PeopleSpinnerAdapter spinnerAdapterPeople;
    private RegisterAdapter registerAdapter;
    private final String SELECTED_PERSON = "SELECTED_PERSON";
    public static final String PERSON_POSITION = "PERSON_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.welcome_to_vacineme));
        listViewRegisters = findViewById(R.id.listViewRegisters);
        people = new ArrayList<>();
        vaccines = new ArrayList<>();
        registers = new ArrayList<>();
        setSpinnerPeople();
        testRegisters();
        insertDataSpinnerPeople();
        insertDataListViewRegisters();
        readPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDataComponents();
    }

    private void updateDataComponents(){
        Person p = (Person) spinnerPeople.getSelectedItem();
        if(p!=null){
            registersFilter(p);
        }
    }

    private void setSpinnerPeople(){
        spinnerPeople = findViewById(R.id.spinnerPeople);
        spinnerPeople.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Person pSelected = (Person)parent.getItemAtPosition(position);
                        registersFilter(pSelected);
                        savePreferences();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
    }

    private void testRegisters(){
        people.add(
                new Person(
                        "Vanderley",
                        new Date(),
                        "Male",
                        new Relationship(
                                getString(R.string.pai),
                                getResources().getDrawable(R.drawable.ic_homem)
                        )
                )
        );
        people.add(
                new Person(
                        "Renan",
                        new Date(),
                        "Male",
                        new Relationship(
                                getString(R.string.filho),
                                getResources().getDrawable(R.drawable.ic_menino)
                        )
                )
        );
        vaccines.add(
                new Vaccine(
                        "Flu",
                        "Teste",
                        "FD64d"
                )
        );
        vaccines.add(
                new Vaccine(
                        "Tetano",
                        "Teste",
                        "SDD3A"
                )
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
        int spinnerPeoplePosition = preferences.getInt(PERSON_POSITION, 0);
        spinnerPeople.setSelection(spinnerPeoplePosition);
    }

    private void insertDataSpinnerPeople(){
        spinnerAdapterPeople = new PeopleSpinnerAdapter(this, people);
        spinnerPeople.setAdapter(spinnerAdapterPeople);
    }

    private void insertDataListViewRegisters() {
        registerAdapter = new RegisterAdapter(this, registers);
        listViewRegisters.setAdapter(registerAdapter);
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
                startActivity(intentMenu);
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
                    startActivity(intentMenu);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean verifyBeforeAddNewRegister(){
        if(people.size() == 0 && vaccines.size() == 0){
            Toast.makeText(this, R.string.you_need_to_add_a_person_and_a_vaccine_before, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registersFilter(Person person){
        List<Register> rFilter = new ArrayList<>();
        for(int i=0;i<registers.size();i++){
            if(registers.get(i).getPerson().getName().equalsIgnoreCase(person.getName())){
                rFilter.add(registers.get(i));
            }
        }
        registerAdapter = new RegisterAdapter(this, rFilter);
        listViewRegisters.setAdapter(registerAdapter);
    }

    public static void addNewPerson(Person p){
        people.add(p);
        spinnerAdapterPeople.notifyDataSetChanged();
    }

    public static void updateSpinnerPeople(){
        spinnerAdapterPeople.notifyDataSetChanged();
    }

    public static void removeAPerson(int position){
        people.remove(position);
        spinnerAdapterPeople.notifyDataSetChanged();
    }

    public static void addNewVaccine(Vaccine v){
        vaccines.add(v);
    }

    public static void addNewRegister(Register r){
        registers.add(r);
    }

    public static List<Register> getRegisters() {
        return registers;
    }

    public static List<Person> getPeople() {
        return people;
    }

    public static List<Vaccine> getVaccines() {
        return vaccines;
    }
}
