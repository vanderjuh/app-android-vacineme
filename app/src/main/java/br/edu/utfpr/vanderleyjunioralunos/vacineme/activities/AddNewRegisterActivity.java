package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.PeopleSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.VaccinesSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Register;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.utils.DateUtil;

public class AddNewRegisterActivity extends AppCompatActivity {

    private Spinner people;
    private Spinner vaccines;
    private EditText dateOfApplication;
    private EditText dateOfNextApplication;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_register);
        setTitle(getString(R.string.add_new_register));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        people = findViewById(R.id.spinnerPeople);
        people.setAdapter(new PeopleSpinnerAdapter(this, MainActivity.getPeople()));
        vaccines = findViewById(R.id.spinnerVaccines);
        vaccines.setAdapter(new VaccinesSpinnerAdapter(this, MainActivity.getVaccines()));
        dateOfApplication = findViewById(R.id.editTextDateOfApplication);
        datePickerEvent(R.id.editTextDateOfApplication);
        dateOfNextApplication = findViewById(R.id.editTextDateOfNextApplication);
        datePickerEvent(R.id.editTextDateOfNextApplication);

        getSeledtedPerson(getIntent());
    }

    private void getSeledtedPerson(Intent intent){
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            people.setSelection(bundle.getInt(MainActivity.PERSON_POSITION));
        }
    }

    private void datePickerEvent(int id) {
        final EditText editText = findViewById(id);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(AddNewRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int dayOfMonth) {
                        editText.setText(DateUtil.dateFormatter(dayOfMonth, mMonth, mYear, getString(R.string.formato_data)));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public void saveNewRegister(View view){
        if(verifyForm()){
            try {
                Register register = new Register(
                        (Vaccine)vaccines.getSelectedItem(),
                        (Person)people.getSelectedItem(),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse(dateOfApplication.getText().toString()),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse(dateOfNextApplication.getText().toString()),
                        R.drawable.ic_vacina
                );
                Toast.makeText(this, R.string.register_successfully_registered, Toast.LENGTH_SHORT).show();
                MainActivity.addNewRegister(register);
                this.finish();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean verifyForm(){
        if(people.isSelected()){
            Toast.makeText(this, R.string.you_need_to_select_a_person, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(vaccines.isSelected()){
            Toast.makeText(this, R.string.you_need_to_select_a_vaccine, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dateOfApplication.getText().toString().isEmpty()){
            Toast.makeText(this, R.string.inform_the_date_of_application, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dateOfNextApplication.getText().toString().isEmpty()){
            Toast.makeText(this, R.string.inform_the_date_of_next_application, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
