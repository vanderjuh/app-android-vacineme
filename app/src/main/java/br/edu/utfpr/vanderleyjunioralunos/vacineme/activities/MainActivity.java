package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.PeopleSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Relationship;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Register;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;

public class MainActivity extends AppCompatActivity {

    private static List<Register> registers;
    private static List<Person> people;
    private static List<Vaccine> vaccines;
    private ListView listViewRegisters;
    private Spinner spinnerPeople;
    private static PeopleSpinnerAdapter spinnerAdapterPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.welcome_to_vacineme));

        spinnerPeople = findViewById(R.id.spinnerPeople);
        listViewRegisters = findViewById(R.id.listViewRegisters);

        people = new ArrayList<>();
        registers = new ArrayList<>();
        vaccines = new ArrayList<>();

        insertDataSpinnerPeople();
        insertDataListViewRegisters();
    }

    private void insertDataSpinnerPeople(){
        spinnerAdapterPeople = new PeopleSpinnerAdapter(this, people);
        spinnerPeople.setAdapter(spinnerAdapterPeople);
    }

    private void insertDataListViewRegisters() {
        try {
            for(int i=0;i<4;i++){
                registers.add(new Register(
                        new Vaccine("Gripe", "Lote", "Laboratorio"),
                        new Person(
                                "Vanderley",
                                new Date(System.currentTimeMillis()),
                                "Masculino",
                                new Relationship("Pai")
                        ),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse("21/09/2018"),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse("21/09/2018"),
                        R.drawable.ic_vacina
                ));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RegistersAdapter registersAdapter = new RegistersAdapter(this, registers);
        listViewRegisters.setAdapter(registersAdapter);
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
                if(verifyBefore()) startActivity(intentMenu);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean verifyBefore(){
        if(people.size() == 0 && vaccines.size() == 0){
            Toast.makeText(this, R.string.you_need_to_add_a_person_and_a_vaccine_before, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public class RegistersAdapter extends ArrayAdapter<Register>{

        private Context context;
        private List<Register> registers;

        RegistersAdapter(Context c, List<Register> registers){
            super(c, R.layout.item_main_listview, R.id.textViewVaccineName, registers);
            this.context = c;
            this.registers = registers;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.item_main_listview, parent, false);
            ImageView image = row.findViewById(R.id.imageViewVaccine);
            TextView title = row.findViewById(R.id.textViewVaccineName);
            TextView dateOfApplication = row.findViewById(R.id.textViewDateOfApplication);
            TextView dateNextVaccine = row.findViewById(R.id.textViewNextDateVaccine);

            image.setImageResource(this.registers.get(position).getIconVaccine());
            title.setText(this.registers.get(position).getVaccine().getDescription().toUpperCase());
            dateOfApplication.setText(dateOfApplication.getText().toString()+" "+new SimpleDateFormat(getString(R.string.formato_data)).format(this.registers.get(position).getVaccineDate()).toString());
            dateNextVaccine.setText(dateNextVaccine.getText().toString()+" "+new SimpleDateFormat(getString(R.string.formato_data)).format(this.registers.get(position).getNextDateVaccine()).toString());
            return row;
        }
    }

    public static void addNewPerson(Person p){
        people.add(p);
        spinnerAdapterPeople.notifyDataSetChanged();
    }

    public static void addNewVaccine(Vaccine v){
        vaccines.add(v);
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
