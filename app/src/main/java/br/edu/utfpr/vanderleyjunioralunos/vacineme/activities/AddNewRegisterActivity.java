package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.PeopleSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.VaccinesSpinnerAdapter;
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
