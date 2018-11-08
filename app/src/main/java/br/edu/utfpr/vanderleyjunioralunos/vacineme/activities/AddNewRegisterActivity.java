package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.PeopleSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.VaccinesSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Register;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.models.Vaccine;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.persistence.VacinemeDatabase;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.utils.DateUtil;

public class AddNewRegisterActivity extends AppCompatActivity {

    private Spinner spinnerPeople;
    private Spinner spinnerVaccines;
    private EditText dateOfApplication;
    private EditText dateOfNextApplication;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private Button button;
    private int VACCINE_ID = -1;
    private int PERSON_ID = -1;
    private List<Vaccine> vaccineList;

    private Register register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_register);
        setTitle(getString(R.string.add_new_register));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        button = findViewById(R.id.buttonRegister);
        spinnerPeople = findViewById(R.id.spinnerPeople);
        spinnerVaccines = findViewById(R.id.spinnerVaccines);
        dateOfApplication = findViewById(R.id.editTextDateOfApplication);
        datePickerEvent(R.id.editTextDateOfApplication);
        dateOfNextApplication = findViewById(R.id.editTextDateOfNextApplication);
        datePickerEvent(R.id.editTextDateOfNextApplication);


        setSpinnerPeople();
        setSpinnerVaccines();

        verifyIntentMode(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    private boolean verifyIntentMode(final Intent intent){
        Bundle bundle = intent.getExtras();
        if(bundle.getInt(MainActivity.VACCINE_ID) != 0){
            setTitle(getString(R.string.edit_register));
            button.setText(R.string.save_changes);
            spinnerPeople.setEnabled(false);
            VACCINE_ID = bundle.getInt(MainActivity.VACCINE_ID);
            PERSON_ID = bundle.getInt(MainActivity.PERSON_ID);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    register = VacinemeDatabase.getDatabase(AddNewRegisterActivity.this).registerDAO().queryForId(VACCINE_ID, PERSON_ID);
                    register.setVaccine(VacinemeDatabase.getDatabase(AddNewRegisterActivity.this).vaccineDAO().queryForId(register.getVaccineId()));
                    setValuesForm(register, intent);
                }
            });
            return true;
        }
        return false;
    }

    private void setValuesForm(final Register register, final Intent intent){
        AddNewRegisterActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setSeledtedPerson(intent);
                setSeledtecVaccine();
                dateOfApplication.setText(new SimpleDateFormat(getString(R.string.formato_data)).format(register.getVaccineDate()));
                dateOfNextApplication.setText(new SimpleDateFormat(getString(R.string.formato_data)).format(register.getNextDateVaccine()));
            }
        });
    }

    private void setSpinnerPeople(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Person> personList = VacinemeDatabase.getDatabase(AddNewRegisterActivity.this).personDAO().queryAll();
                AddNewRegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinnerPeople.setAdapter(new PeopleSpinnerAdapter(AddNewRegisterActivity.this, personList));
                        setSeledtedPerson(getIntent());
                    }
                });
            }
        });
    }

    private void setSpinnerVaccines(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                vaccineList = VacinemeDatabase.getDatabase(AddNewRegisterActivity.this).vaccineDAO().queryAll();
                AddNewRegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinnerVaccines.setAdapter(new VaccinesSpinnerAdapter(AddNewRegisterActivity.this, vaccineList));
                    }
                });
            }
        });
    }

    private void setSeledtedPerson(Intent intent){
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            spinnerPeople.setSelection(bundle.getInt(MainActivity.PERSON_POSITION));
        }
    }

    private void setSeledtecVaccine(){
        for(int i=0;i<vaccineList.size();i++){
            Vaccine v = vaccineList.get(i);
            if(v.getId() == register.getVaccineId()){
                spinnerVaccines.setSelection(i);
            }
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
                final Register r = new Register(
                        ((Vaccine) spinnerVaccines.getSelectedItem()).getId(),
                        ((Person) spinnerPeople.getSelectedItem()).getId(),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse(dateOfApplication.getText().toString()),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse(dateOfNextApplication.getText().toString())
                );
                if(!button.getText().toString().equalsIgnoreCase(getString(R.string.save_changes))){
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            VacinemeDatabase.getDatabase(AddNewRegisterActivity.this).registerDAO().insert(r);
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                } else {
                    System.out.println(((Vaccine) spinnerVaccines.getSelectedItem()).getDescription());
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            r.setId(register.getId());
                            VacinemeDatabase.getDatabase(AddNewRegisterActivity.this).registerDAO().update(r);
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean verifyForm(){
        if(spinnerPeople.isSelected()){
            Toast.makeText(this, R.string.you_need_to_select_a_person, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(spinnerVaccines.isSelected()){
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
                break;
            case R.id.menuItemDelete:
                deleteRegister();
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!verifyIntentMode(getIntent())){
            menu.getItem(0).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void deleteRegister(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                VacinemeDatabase.getDatabase(AddNewRegisterActivity.this).registerDAO().delete(register);
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
