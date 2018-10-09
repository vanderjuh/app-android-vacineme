package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.activities.adapters.RelationshitSpinnerAdapter;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Relationship;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Person;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.utils.DateUtil;

public class AddNewPersonActivity extends AppCompatActivity {

    private TextView name;
    private RadioGroup genrer;
    private Spinner spinnerRelationship;
    private DatePickerDialog datePickerDialog;
    private TextView dateOfBorn;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_person);
        setTitle(getString(R.string.inserir_uma_nova_pessoa));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        name = findViewById(R.id.editTextName);
        dateOfBorn = findViewById(R.id.editTextDateOfBorn);
        genrer = findViewById(R.id.radioGroupGender);
        spinnerRelationship = findViewById(R.id.spinnerRelationship);

        insertDataSpinnerRelationship();
        datePickerEvent();
    }

    private void datePickerEvent() {
        dateOfBorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(AddNewPersonActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int dayOfMonth) {
                        dateOfBorn.setText(DateUtil.dateFormatter(dayOfMonth, mMonth, mYear, getString(R.string.formato_data)));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void insertDataSpinnerRelationship() {
        String[] descs = getResources().getStringArray(R.array.relationship);
        TypedArray icons = getResources().obtainTypedArray(R.array.icones_parentesco);

        ArrayList<Relationship> relationships = new ArrayList();

        for (int cont = 0; cont < descs.length; cont++) {
            relationships.add(new Relationship(descs[cont], icons.getDrawable(cont)));
        }

        RelationshitSpinnerAdapter paisAdapter = new RelationshitSpinnerAdapter(this, relationships);

        spinnerRelationship.setAdapter(paisAdapter);
    }

    private String getSelectedGender(int id) {
        switch (id) {
            case R.id.radioButtonFemale:
                return getString(R.string.feminino);
            case R.id.radioButtonMale:
                return getString(R.string.masculino);
        }
        return "";
    }

    public void saveNewPerson(View view) {
        if (verifyForm()) {
            try {
                Person p = new Person(
                        name.getText().toString(),
                        new SimpleDateFormat(getString(R.string.formato_data)).parse(dateOfBorn.getText().toString()),
                        getSelectedGender(genrer.getCheckedRadioButtonId()),
                        (Relationship) spinnerRelationship.getSelectedItem()
                );
                MainActivity.addNewPerson(p);
                Toast.makeText(this, R.string.pessoa_cadastrada_sucesso, Toast.LENGTH_SHORT).show();
                PeopleActivity.updateListView();
                this.finish();
            } catch (ParseException e) {
                Toast.makeText(this, R.string.verifique_a_data_de_nascimento, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean verifyForm() {
        if (name.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.informe_o_nome_da_pessoa, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dateOfBorn.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.informe_a_data_de_nascimento, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (genrer.getCheckedRadioButtonId()==-1) {
            Toast.makeText(this, R.string.informe_o_genero_da_pessoa, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spinnerRelationship.getSelectedItemPosition() == -1) {
            Toast.makeText(this, R.string.informe_o_parentesco, Toast.LENGTH_SHORT).show();
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
        }
        return true;
    }
}
