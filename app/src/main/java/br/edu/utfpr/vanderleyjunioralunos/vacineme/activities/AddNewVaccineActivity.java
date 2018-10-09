package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;

public class AddNewVaccineActivity extends AppCompatActivity {

    private TextView vaccineDescription;
    private TextView vaccineLaboratory;
    private TextView vaccineLotNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vaccine);

        vaccineDescription = findViewById(R.id.editTextVaccineDescription);
        vaccineLaboratory = findViewById(R.id.editTextLaboratoryName);
        vaccineLotNumber = findViewById(R.id.editTextLotNumber);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void saveNewVaccine(View view) {
        if (verifyForm()) {
            Vaccine v = new Vaccine(
                    vaccineDescription.getText().toString(),
                    vaccineLaboratory.getText().toString(),
                    vaccineLotNumber.getText().toString()
            );
            MainActivity.addNewVaccine(v);
            Toast.makeText(this, R.string.vaccine_successfully_registered, Toast.LENGTH_SHORT).show();
            VaccinesActivity.updateListView();
            this.finish();
        }
    }

    private boolean verifyForm() {
        if (vaccineDescription.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.insert_the_vaccine_description, Toast.LENGTH_SHORT).show();
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
