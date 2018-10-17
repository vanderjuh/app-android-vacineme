package br.edu.utfpr.vanderleyjunioralunos.vacineme.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.utfpr.vanderleyjunioralunos.vacineme.R;
import br.edu.utfpr.vanderleyjunioralunos.vacineme.entities.Vaccine;

public class AddNewVaccineActivity extends AppCompatActivity {

    private TextView vaccineDescription;
    private TextView vaccineLaboratory;
    private TextView vaccineLotNumber;
    private Button button;
    private int ITEM_POSITION = -1;

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
            ITEM_POSITION = bundle.getInt("ITEM_POSITION");
            setValuesForm(MainActivity.getVaccines().get(ITEM_POSITION));
            return true;
        }
        return false;
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
            Vaccine v = new Vaccine(
                    vaccineDescription.getText().toString(),
                    vaccineLaboratory.getText().toString(),
                    vaccineLotNumber.getText().toString()
            );
            if(!button.getText().toString().equalsIgnoreCase(getString(R.string.save_changes))){
                MainActivity.addNewVaccine(v);
            } else {
                MainActivity.getVaccines().get(ITEM_POSITION).setDescription(vaccineDescription.getText().toString());
                MainActivity.getVaccines().get(ITEM_POSITION).setLotNumber(vaccineLotNumber.getText().toString());
                MainActivity.getVaccines().get(ITEM_POSITION).setLaboratorio(vaccineLaboratory.getText().toString());
            }
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
            case R.id.menuItemDelete:
                MainActivity.getVaccines().remove(ITEM_POSITION);
                VaccinesActivity.updateListView();
                this.finish();
        }
        return true;
    }

}
