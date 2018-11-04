package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.persistence.VacinemeDatabase;

public class AddNewVaccineActivity extends AppCompatActivity {

    private TextView vaccineDescription;
    private TextView vaccineLaboratory;
    private TextView vaccineLotNumber;
    private Button button;
    private int VACCINE_ID = -1;
    private Vaccine vaccine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vaccine);

        vaccineDescription = findViewById(R.id.editTextVaccineDescription);
        vaccineLaboratory = findViewById(R.id.editTextLaboratoryName);
        vaccineLotNumber = findViewById(R.id.editTextLotNumber);
        button = findViewById(R.id.buttonSaveVaccine);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(!verifyIntentMode(getIntent())){
            setTitle(getString(R.string.inserir_uma_nova_pessoa));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!verifyIntentMode(getIntent())){
            menu.getItem(0).setVisible(false);
            menu.getItem(0).setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private boolean verifyIntentMode(Intent intent){
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            setTitle(getString(R.string.edit_vaccine));
            button.setText(R.string.save_changes);
            VACCINE_ID = bundle.getInt("VACCINE_ID");
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    vaccine = VacinemeDatabase.getDatabase(AddNewVaccineActivity.this).vaccineDAO().queryForId(VACCINE_ID);
                    AddNewVaccineActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setValuesForm(vaccine);
                        }
                    });
                }
            });
            return true;
        }
        return false;
    }

    private void deleteVaccine(final Vaccine v){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                VacinemeDatabase.getDatabase(AddNewVaccineActivity.this).vaccineDAO().delete(v);
            }
        });
    }

    private void setValuesForm(Vaccine v){
        if(v!=null){
            vaccineDescription.setText(v.getDescription());
            vaccineLotNumber.setText(v.getLotNumber());
            vaccineLaboratory.setText(v.getLaboratorio());
        } else {
            Toast.makeText(this, R.string.no_vaccines_found, Toast.LENGTH_SHORT).show();
        }
    }

    public void saveNewVaccine(View view) {
        if (verifyForm()) {
            final Vaccine v = new Vaccine(
                    vaccineDescription.getText().toString(),
                    vaccineLaboratory.getText().toString(),
                    vaccineLotNumber.getText().toString()
            );
            if(!button.getText().toString().equalsIgnoreCase(getString(R.string.save_changes))){
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        VacinemeDatabase.getDatabase(AddNewVaccineActivity.this).vaccineDAO().insert(v);
                    }
                });
            } else {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        v.setId(vaccine.getId());
                        VacinemeDatabase.getDatabase(AddNewVaccineActivity.this).vaccineDAO().update(v);
                    }
                });
            }
            setResult(RESULT_OK);
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
            case R.id.menuItemDelete:
                deleteVaccine(vaccine);
                setResult(RESULT_OK);
                this.finish();
        }
        return true;
    }

}
